package com.winbits.domain.orders

import com.winbits.domain.affiliation.User
import org.hibernate.criterion.CriteriaSpecification
import com.winbits.domain.affiliation.Vertical
import org.joda.time.DateTime

class Order {

  BigDecimal itemsTotal
  BigDecimal shippingTotal
  BigDecimal bitsTotal
  BigDecimal total
  BigDecimal cashTotal
  BigDecimal cashbackTotal
  
  Long cashbackTransactionId

  String orderNumber // calculado, nullable: true, ID+dateCreated
  DateTime paidDate
  OrderStatus status
  Vertical vertical
  Long salesAgentId

  User user

  DateTime dateCreated
  DateTime lastUpdated

  static hasMany = [orderDetails: OrderDetail, orderPayments: OrderPayment]
  static hasOne = [shippingOrder:ShippingOrder]

  List paymentMethods
  List<CartDetail> failedCartDetails
  static transients = ['paymentMethods', 'failedCartDetails', 'cashback', 'estimatedDeliveryDate']

  static constraints = {
    itemsTotal min: 0.0, max: 10000000000.0, scale: 2
    shippingTotal min: 0.0, max: 10000000000.0, scale: 2
    bitsTotal min: 0.0
    total min: 0.0, max: 10000000000.0, scale: 2
    cashTotal min: 0.0, max: 10000000000.0, scale: 2
    orderNumber nullable: true, maxSize: 20
    paidDate nullable: true
    shippingOrder nullable: true
    salesAgentId nullable: true
    cashbackTotal nullable: true
    cashbackTransactionId  nullable: true
  }

  static mapping = {
    table 'orders'
    orderDetails sort: 'id', order: "desc", cascade: 'all-delete-orphan'
    orderPayments cascade: 'all-delete-orphan'
    shippingOrder fetch: 'join'
  }

  static namedQueries = {

    paidPayment { user ->
        eq 'status', OrderStatusEnum.PAID.domain
        eq 'user', user
    }

    cancelledPayment{user ->
        eq 'status', OrderStatusEnum.CANCELLED.domain
        eq 'user', user
    
    }
  }

  def beforeUpdate() {
    if (!paidDate && status.enum == OrderStatusEnum.PAID) {
      paidDate = DateTime.now()
    }
  }

  def afterInsert() {
     generateOrderNumber()
  }

  private void generateOrderNumber() {
    if (id && dateCreated) {
      orderNumber = dateCreated.toString('yyMMddHHmm--') + id.toString()
      this.save()
    }
  }

  BigDecimal getCashback() {
    orderDetails?.sum() {
      def quantity = it.quantity
      if(it.status.enum == OrderDetailStatusEnum.REFUNDED)
        quantity = quantity * -1
      (it.skuProfile?.itemGroupProfile?.cashback ?: 0) * quantity
    } ?: 0.0
  }

  String getEstimatedDeliveryDate() {
      def maxDeliveryDate = orderDetails*.estimatedDeliveryDate?.max()
      if (maxDeliveryDate) {
          maxDeliveryDate.toString('dd/MM/yyyy')
      }
  }

//  ShippingOrder getShippingOrder() {
//     ShippingOrder.findByOrder(this)
//  }
}
