package com.winbits.domain.orders

import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ItemGroupProfile
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.utils.DomainModelUtils
import grails.buildtestdata.TestDataConfigurationHolder
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Order)
@Build([Order, OrderDetail, SkuProfile, ItemGroupProfile])
@Mock([OrderStatus, OrderDetailStatus])
class OrderSpec extends Specification {

  @Shared Order order

  def setup() {
    DomainModelUtils.createPersistentEnumModel(OrderStatusEnum)
    DomainModelUtils.createPersistentEnumModel(OrderDetailStatusEnum)
    order = initializePaidOrder()
  }

  private Order initializePaidOrder() {
    User.metaClass.encodePassword = {}
    def orderDetailStatus = OrderDetailStatusEnum.PAID.domain
    TestDataConfigurationHolder.sampleData["${OrderDetail.name}"] = [status: orderDetailStatus]
    def order = Order.build(status: OrderStatusEnum.PAID.domain)

    OrderDetail.build(order: order, skuProfile: SkuProfile.build())
    OrderDetail.build(order: order, skuProfile: SkuProfile.build())
    OrderDetail.build(order: order, skuProfile: SkuProfile.build())

    order
  }

  def 'order cashback is computed correctly'() {
    setup:
    order.orderDetails.eachWithIndex {OrderDetail entry, int i ->
      entry.quantity = quatities[i]
      entry.save()
      entry.skuProfile.itemGroupProfile.cashback = cashbacks[i]
      entry.skuProfile.itemGroupProfile.save()
    }

    when:
    def cashback = order.cashback

    then:
    cashback == expectedCashBack

    where:
    cashbacks          | quatities || expectedCashBack
    [10.0, 15.0, 20.0] | [2, 3, 4] || 145.00
    [12.0, 0, 13.0]    | [4, 5, 2] || 74.00
  }
}