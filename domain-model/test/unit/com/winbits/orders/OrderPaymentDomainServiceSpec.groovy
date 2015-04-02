package com.winbits.orders
 
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.Item
import com.winbits.domain.catalog.ItemGroup
import com.winbits.domain.catalog.PaymentMethod
import com.winbits.domain.catalog.Sku
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatus
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderPayment
import com.winbits.domain.orders.OrderPaymentStatus
import com.winbits.domain.orders.OrderPaymentStatusEnum
import com.winbits.domain.orders.OrderStatus
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.utils.DomainModelUtils
 
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
 
import org.joda.time.DateTime
 
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(OrderPaymentDomainService)
@Mock([Item, ItemGroup, Sku, SkuProfile, Order, OrderDetail, User, 
OrderPayment, OrderPaymentStatus, PaymentMethod, Profile, OrderDetailStatus, OrderStatus])
class OrderPaymentDomainServiceSpec extends Specification {

	def setup() {
            DomainModelUtils.createPersistentEnumModel(OrderPaymentStatusEnum)
            DomainModelUtils.createPersistentEnumModel(OrderDetailStatusEnum)
            DomainModelUtils.createPersistentEnumModel(OrderPaymentStatusEnum)
            DomainModelUtils.createPersistentEnumModel(OrderStatusEnum)
            Map.metaClass.encodeAsJSON = { -> '{ \"json\":\"lleison\"}' }
	}

	def cleanup() {
	}

	void "test change order payment status"() {
            given:
                Order order = initializeOrder()
                OrderPayment orderPayment = initializeOrderPayment(order, "cybersource.cc",
                    OrderPaymentStatusEnum.REVIEW.domain)

                OrderPaymentStatus newStatus = OrderPaymentStatusEnum.PAID.domain
            when:
                OrderPayment orderPaymentChanged  = service.changeOrderPaymentStatus(orderPayment, newStatus)
            then:
                assert orderPaymentChanged
                assert orderPaymentChanged.status == newStatus
	}
	
        void "test change order payment status and date and reference"() {
            given:
                Order order = initializeOrder()
                OrderPayment orderPayment = initializeOrderPayment(order, "reference.hsbc",
                    OrderPaymentStatusEnum.PENDING.domain)
                DateTime paidDate = DateTime.now()
                String lineCapture ="999931405651674320131217                      W00000004296734985PAGO                           9651780  00000000020.0023174" 
            when:
                OrderPayment orderPaymentChanged  = service.changeOrderPaymentStatusAndPaidDateAndReference(paidDate, 
                        lineCapture, orderPayment)
            then:
                assert orderPaymentChanged
                assert orderPaymentChanged.status == OrderPaymentStatusEnum.PAID.domain
                assert orderPaymentChanged.paidDate == paidDate
                assert orderPaymentChanged.paymentCapture
	}

        void "test payment capture"(){
            given:
            String paymentCapture = '{\"downloadUrl\":\"http://127.0.0.1:8003/orders-api/payments/13/hsbc-payment-ticket\"}'
            String lineCapture ="999931405651674320131217 W00000004296734985PAGO 9651780 00000000020.0023174" 
            when:
            String response = service.addLineCaptureToPaymentCapture(paymentCapture, lineCapture)
            then:
            assert response
        }
        
        void "test null payment capture"(){
            given:
            String paymentCapture = null
            String lineCapture ="999931405651674320131217 W00000004296734985PAGO 9651780 00000000020.0023174" 
            when:
            String response = service.addLineCaptureToPaymentCapture(paymentCapture, lineCapture)
            then:
            assert response
        }
  
  private Order initializeOrder() {
    User user = obtainUser()

    Order order = new Order()
    order.status = OrderStatusEnum.PENDING.domain
    order.user = user
    order.itemsTotal = 400
    order.bitsTotal = 10
    order.shippingTotal = 79
    order.total = 479
    order.cashTotal = 469

    ItemGroup itemGroup = new ItemGroup(requiresShipping: true).save(validate: false)
    Item item = new Item(itemGroup: itemGroup)
    item.save(validate: false)
    Sku sku = new Sku(item: item).save(validate: false)
    SkuProfile skuProfile1 = new SkuProfile(sku: sku, price: 100).save(validate: false)
    SkuProfile skuProfile2 = new SkuProfile(sku: sku, price: 50).save(validate: false)

    OrderDetail orderDetail = new OrderDetail()
    orderDetail.amount = skuProfile1.price * 3
    orderDetail.quantity = 3
    orderDetail.status = OrderDetailStatusEnum.CHECKOUT.domain
    orderDetail.skuProfile = skuProfile1
    order.addToOrderDetails(orderDetail)

    OrderDetail orderDetail2 = new OrderDetail()
    orderDetail2.amount = skuProfile2.price * 2
    orderDetail2.quantity = 2
    orderDetail2.status = OrderDetailStatusEnum.CHECKOUT.domain
    orderDetail2.skuProfile = skuProfile2
    order.addToOrderDetails(orderDetail2)

    order.save(validate: false)

    order
  }

  private OrderPayment initializeOrderPayment(Order order, String paymentMethod, OrderPaymentStatus status){
    OrderPayment orderPayment = new OrderPayment()
    orderPayment.amount = order.total
    orderPayment.paidDate = DateTime.now().minusDays(5)
    orderPayment.status = status
    orderPayment.order = order
    orderPayment.paymentMethod = initializePaymentMethod(paymentMethod)
    orderPayment.reference = 1
    orderPayment.dateCreated = getDefaultDate() 
    orderPayment.save(validate: false)
    orderPayment
  }

  private PaymentMethod initializePaymentMethod(String paymentIdentifier){
    PaymentMethod paymentMethod = new PaymentMethod()
    paymentMethod.active = true
    paymentMethod.expireMinutes = 30
    paymentMethod.minAmount = 0
    paymentMethod.maxAmount = 1000000
    paymentMethod.offline = false
    paymentMethod.identifier = paymentIdentifier

    paymentMethod.save(validate: false)
    paymentMethod
  }

  private User obtainUser() {
    if (!User.count()) {
      User.metaClass.encodePassword = {-> }
      User user = new User(email: "a@b.com", password: "123", enabled: true, profile: obtainProfile())
      user.save(validate: false)
    }

    User.get(1)
  }

  private Profile obtainProfile(){
    Profile profile = new Profile(name: "naim", lastName: "apellido", bitsId: 1L)
    profile.save(validate: false)
    profile
  }
  
  private DateTime getDefaultDate(){
     new DateTime(2014,1,1,12,5,0,0)
  }


}
