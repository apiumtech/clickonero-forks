package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.UserService
import com.winbits.domain.catalog.ItemGroup
import com.winbits.domain.catalog.ItemGroupProfile
import com.winbits.domain.catalog.ItemGroupTypeEnum
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.logistics.SkuOutcome
import com.winbits.domain.logistics.SkuOutcomeStatusEnum
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.utils.DomainModelUtils
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(OrdersService)
@TestMixin(GrailsUnitTestMixin)
@Mock([User, Order, OrderDetail, SkuProfile, ItemGroupProfile, ItemGroup, SkuOutcome])
@Build([User, Order, OrderDetail, SkuProfile, ItemGroupProfile, ItemGroup, SkuOutcome])
class OrdersServiceSpec extends Specification {

  def setup() {
    User.metaClass.encodePassword = {}
    DomainModelUtils.createPersistentEnumModel(OrderDetailStatusEnum)
    DomainModelUtils.createPersistentEnumModel(OrderStatusEnum)
    DomainModelUtils.createPersistentEnumModel(ItemGroupTypeEnum)
//    DomainModelUtils.createPersistentEnumModelForTest(SkuOutcomeStatusEnum)
    DomainModelUtils.createPersistentEnumModel(SkuOutcomeStatusEnum)
  }

  void "quantity"() {
    setup:
    def user = User.build(email: 'test@winbits.com')
    def order = Order.build(user:user, status: OrderStatusEnum.PAID.domain)
    def itemGroup = ItemGroup.build(itemGroupType: ItemGroupTypeEnum.PRODUCT.domain)
    def itemGroupProfile = ItemGroupProfile.build(itemGroup: itemGroup)
    def skuProfile = SkuProfile.build(itemGroupProfile: itemGroupProfile)
    def orderDetail = OrderDetail.build(status: OrderDetailStatusEnum.PAID.domain, order:order, skuProfile: skuProfile, quantity: quantity, outputQuantity: 1)
    def skuOutcome = SkuOutcome.build(status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, orderDetail: orderDetail, quantity: 1)
    when:
    def resp = service.countOutcomePending(user)
    then:
    resp == quantity
    where:
    quantity<<[1,3,5]
  }


  void "quantity refunded"() {
    setup:
    def user = User.build(email: 'test@winbits.com')
    def order = Order.build(user:user, status: OrderStatusEnum.PAID.domain)
    def itemGroup = ItemGroup.build(itemGroupType: ItemGroupTypeEnum.PRODUCT.domain)
    def itemGroupProfile = ItemGroupProfile.build(itemGroup: itemGroup)
    def skuProfile = SkuProfile.build(itemGroupProfile: itemGroupProfile)
    def orderDetail = OrderDetail.build(status: OrderDetailStatusEnum.PAID.domain, order:order, skuProfile: skuProfile, quantity: quantity, outputQuantity: 1)
    def refundDetail = OrderDetail.build(status: OrderDetailStatusEnum.REFUNDED.domain, order:order, skuProfile: skuProfile, quantity: 1, refundedDetail: orderDetail)
    def skuOutcome = SkuOutcome.build(status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, orderDetail: orderDetail, quantity: 1)
    when:
    def resp = service.countOutcomePending(user)
    then:
    resp == quantity-1
    where:
    quantity<<[1,3,5]
  }

  void "refund orderDetails"(){
    setup:
    def user = User.build(email: 'test@winbits.com')
    def skuProfile = SkuProfile.build()
    def order = Order.build(user:user, status: OrderStatusEnum.PAID.domain)
    def orderDetail = OrderDetail.build(status: OrderDetailStatusEnum.PAID.domain, order:order, skuProfile: skuProfile, quantity: 3)
    def orderDetail1 = OrderDetail.build(status: OrderDetailStatusEnum.REFUNDED.domain, order:order, skuProfile: skuProfile, quantity: 1, refundedDetail: orderDetail)
    def orderDetail2 = OrderDetail.build(status: OrderDetailStatusEnum.REFUNDED.domain, order:order, skuProfile: skuProfile, quantity: 1, refundedDetail: orderDetail)
    order.addToOrderDetails orderDetail
    order.addToOrderDetails orderDetail1
    order.addToOrderDetails orderDetail2
    when:
    def resp = service.getRefundOrders([order:order.orderDetails])
    def orderInDB = Order.get(order.id)
    then:
    orderInDB.orderDetails == order.orderDetails
  }
}
