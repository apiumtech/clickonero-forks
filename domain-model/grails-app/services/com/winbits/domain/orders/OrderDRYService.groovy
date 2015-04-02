package com.winbits.domain.orders
 
import com.winbits.api.orders.services.BitsDomainService
import com.winbits.domain.catalog.SkuProfile
import com.winbits.orders.ActorStockService
import com.winbits.orders.exception.OrderNotCancellableException
 
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

class OrderDRYService {

  BitsDomainService bitsDomainService
  ActorStockService actorStockService
  def messagingService

  static final String errorBitsReembolsados = 'BITE050'

  Order expireOrder(Long orderId) {
    Order order = Order.get(orderId)
    if(order.status.enum != OrderStatusEnum.CHECKOUT)
      throw new OrderNotCancellableException(orderId)

    releaseStock(order.orderDetails)
    expireOrderDetails(order)
    order.status = OrderStatusEnum.ATTEMPTED.domain

    try { 
      order.save()
    } catch (OptimisticLockingFailureException olfe) {
      expireOrder(orderId)
    }
    order
  }

  OrderPayment cancelOrder(Long orderPaymentId) {
    OrderPayment orderPayment = OrderPayment.get(orderPaymentId)
    orderPayment.status = OrderPaymentStatusEnum.CANCELLED.domain
    orderPayment.save()
    Order order = orderPayment.order
    order.status = OrderStatusEnum.CANCELLED.domain

    cancelOrderDetails (order)
    releaseStock(order.orderDetails)

    //Se toma en cuenta que una orden solo podrá relacionarse a lo mas con dos orderPayments
    //uno del tipo del pago seleccionado y otro para la opcion de bits.
    attemptRefundBits(order)

    orderPayment
  }

  Order cancelOrderDetails(Order order) {
    order.orderDetails.each{ orderDetail ->
      orderDetail.status = OrderDetailStatusEnum.CANCELLED.domain
      orderDetail.save()      
    }
    order
  }
  
  Order expireOrderDetails(Order order) {
    order.orderDetails.each{ orderDetail ->
      orderDetail.status = OrderDetailStatusEnum.ATTEMPTED.domain
      orderDetail.save()      
    }
    order
  }

  def attemptRefundBits(Long orderId) {
    def order = Order.findById(orderId, [fetch: [orderPayments: 'join', status: 'join']])
    attemptRefundBits(order)
  }

  def attemptRefundBits(Order order) {
    def bitsOrderPayment = order.orderPayments.find {
      it.paymentMethod.identifier == 'user.bits' && it.status.enum == OrderPaymentStatusEnum.PAID
    }
    if (bitsOrderPayment) {
      refundBits(bitsOrderPayment)
    }
  }

  def refundBits(OrderPayment bitsOrderPayment) {
    messagingService.publishMessage("bitsRollback",
        [id: bitsOrderPayment.order.user.profile().bitsId,
         orderNumber: bitsOrderPayment.order.orderNumber,
         concept: 'Refund'])
    bitsOrderPayment.status = OrderPaymentStatusEnum.REFUNDED.domain
    bitsOrderPayment.save()
  }

  void releaseStock(def orderDetails){
    orderDetails.each { orderDetail ->
      SkuProfile skuProfile = orderDetail.skuProfile
      Long skuId = skuProfile.sku.id
      Long skuProfileId = skuProfile.id
      def message4sku =  [skuId: skuId, quantity: orderDetail.quantity]
      def message4skuProfile = [skuProfileId: skuProfileId, quantity: orderDetail.quantity]
      actorStockService.releaseStock4Sku.sendAndWait message4sku
      actorStockService.releaseStock4SkuProfile.sendAndWait message4skuProfile
    }
  }
}
