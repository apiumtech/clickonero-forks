package com.winbits.domain.orders

import com.winbits.StockService
import com.winbits.api.orders.services.BitsDomainService
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.*
import com.winbits.domain.messaging.MessagingService
import com.winbits.domain.utils.DomainModelUtils
import com.winbits.orders.ActorStockService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.DateTime
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(OrderDRYService)
@Mock([Item, ItemGroup, Sku, SkuProfile, Order, OrderDetail, User, OrderStatus, OrderDetailStatus,
OrderPayment, OrderPaymentStatus, PaymentMethod, StockService, Profile, Vertical])
class OrderDRYServiceSpec extends Specification {

  def setup() {
    DomainModelUtils.createPersistentEnumModel(OrderPaymentStatusEnum)
    DomainModelUtils.createPersistentEnumModel(OrderDetailStatusEnum)
    DomainModelUtils.createPersistentEnumModel(OrderPaymentStatusEnum)
    DomainModelUtils.createPersistentEnumModel(OrderStatusEnum)
  }

  def cleanup() {
  }

  void "test cancel order"() {
    setup: "Initialize data"
    Order order = initializeOrder()
    OrderPayment orderPayment = initializeOrderPayment(order, "oxxo.bc", OrderPaymentStatusEnum.PENDING.domain)
    initializeOrderPayment(order, "user.bits", OrderPaymentStatusEnum.PAID.domain)
    StockService stockService = Mock()
    MessagingService messagingService = Mock()
    service.messagingService = messagingService
    ActorStockService actorStockService = Spy()
    actorStockService.stockService = stockService
    service.actorStockService = actorStockService
    BitsDomainService bitsDomainService = Mock(BitsDomainService)
    service.bitsDomainService = bitsDomainService
    when:
    def paymentResponse = service.cancelOrder(orderPayment.id)
    then:
    assert paymentResponse
    assert paymentResponse.status == OrderPaymentStatusEnum.CANCELLED
    assert paymentResponse.order.status == OrderStatusEnum.CANCELLED
    def statusDetail = paymentResponse.order.orderDetails.findAll {
      it.status == OrderDetailStatusEnum.CANCELLED 
    }
    assert statusDetail
    assert statusDetail.size () == 2
    1 * stockService.releaseStockForSkuProfileWithQuantity(_, _)
    1 * stockService.releaseStockForSkuProfileWithQuantity(_, _)
    1 * messagingService.publishMessage(_,_)
  }

  void "expire order details"() {
  setup: "Initialize data"
    Order order = initializeOrder()
    order.status = OrderStatusEnum.CHECKOUT.domain
    StockService stockService = Mock()

    ActorStockService actorStockService = Spy()
    actorStockService.stockService = stockService
    service.actorStockService = actorStockService

    BitsDomainService bitsDomainService = Mock(BitsDomainService)
    service.bitsDomainService = bitsDomainService
  when:
    def paymentResponse = service.expireOrder(order.id)
  then:
    assert paymentResponse
    assert paymentResponse.status == OrderStatusEnum.ATTEMPTED
    def statusDetail = paymentResponse.orderDetails.findAll {
      it.status == OrderDetailStatusEnum.ATTEMPTED 
    }
    assert statusDetail.size () == 2
  }

  private Order initializeOrder() {
    User user = obtainUser()
    Vertical vertical = new Vertical(baseUrl:'http://www.foo.com', name:'vertical') 
    vertical.save()

    Order order = new Order()
    order.status = OrderStatusEnum.CANCELLED.domain
    order.user = user
    order.itemsTotal = 400
    order.bitsTotal = 10
    order.shippingTotal = 79
    order.total = 479
    order.cashTotal = 469
    order.vertical = vertical

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

    order.save()

    order
  }

  private OrderPayment initializeOrderPayment(Order order, String paymentMethod, OrderPaymentStatus status) {
    OrderPayment orderPayment = new OrderPayment()
    orderPayment.amount = order.total
    orderPayment.paidDate = DateTime.now().minusDays(5)
    orderPayment.status = status
    orderPayment.order = order
    orderPayment.paymentMethod = initializePaymentMethod(paymentMethod)
    orderPayment.reference = 1
    orderPayment.save(validate: false, flush: true)
    orderPayment
  }

  private PaymentMethod initializePaymentMethod(String paymentIdentifier) {
    PaymentMethod paymentMethod = new PaymentMethod()
    paymentMethod.active = true
    paymentMethod.expireMinutes = 30
    paymentMethod.minAmount = 0
    paymentMethod.maxAmount = 1000000
    paymentMethod.offline = false
    paymentMethod.identifier = paymentIdentifier

    paymentMethod.save(validate: false, flush: true)
    paymentMethod
  }

  private User obtainUser() {
    if (!User.count()) {
      User.metaClass.encodePassword = {-> }
      User user = new User(email: "a@b.com", password: "123", enabled: true)
      user.save(validate: false)
      obtainProfile(user)
    }

    User.get(1)
  }

  private Profile obtainProfile(User user) {
    Profile profile = new Profile(name: "naim", lastName: "apellido", bitsId: 1L, user:user, locale: Locale.CANADA_FRENCH)
    profile.save(flush: true)
    profile
  }
}
