package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.FindCommand
import com.winbits.api.clients.service.ServiceClient
import com.winbits.domain.logistics.Carrier
import com.winbits.domain.logistics.CarrierStatusEnum
import com.winbits.domain.logistics.Guide
import grails.plugin.spock.IntegrationSpec
import com.winbits.orders.utils.OrderDataUtils
import com.winbits.orders.utils.InOutDataUtils
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.logistics.Warehouse
import com.winbits.domain.logistics.SkuOutcomeStatusEnum
import com.winbits.domain.catalog.ItemGroupTypeEnum
import spock.lang.Shared


/**
 * Created by manuel on 7/23/14.
 */
class OrdersServiceIntegrationSpec extends IntegrationSpec {

  @Shared
  def ordersService

  @Shared
  Warehouse warehouse

  void setupSpec(){
    def serviceClient = Mock(ServiceClient)
    ordersService.couponService.serviceClient = serviceClient
    warehouse = Warehouse.build()
  }

  def createOrder(itemGroupType) {
    def skuProfile1 = OrderDataUtils.createSkuProfile()
    def itemGroup = skuProfile1.itemGroupProfile.itemGroup
    itemGroup.itemGroupType = itemGroupType
    itemGroup.save()
    def order = OrderDataUtils.createOrder(skuProfile1)
    order.status = OrderStatusEnum.PAID.domain
    order.save()
    order
  }

  def createOuputFromWarehouse(order, status) {
    def skuOutcome = InOutDataUtils.createSkuOutcome(order, warehouse)
    skuOutcome.status = status
    skuOutcome.save()
  }

  def createOuputFromWarehouse(order) {
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.IN_WAREHOUSE.domain)
  }


  void "get orders with delivery status"() {
    setup:
    def order = createOrder(ItemGroupTypeEnum.PRODUCT.domain)
    createOuputFromWarehouse(order)

    when:
    def result = ordersService.findAllByUserAndFilter(order.user, new FindCommand() )


    then:
    assert result
    assert result.first().orderDetails.first().statusOutcome.first().status == SkuOutcomeStatusEnum.IN_WAREHOUSE.toString()

  }

  void "get orders without outputs from warehouse"() {
    setup:
    def order = createOrder(ItemGroupTypeEnum.PRODUCT.domain)

    when:
    def result = ordersService.findAllByUserAndFilter(order.user, new FindCommand() )

    then:
    assert result
    assert result.first().orderDetails.first().statusOutcome.first().status == SkuOutcomeStatusEnum.IN_WAREHOUSE.toString()
    assert result.first().orderDetails.first().statusOutcome.first().quantity == order.orderDetails.first().quantity

  }

  void "get orders with incomplete outputs"() {
    setup:
    def order = createOrder(ItemGroupTypeEnum.PRODUCT.domain)
    def orderDetail = order.orderDetails.first()
    orderDetail.outputQuantity = 7
    orderDetail.quantity = 10
    orderDetail.save()
    //the default status for outputs is in_warehouse
    createOuputFromWarehouse(order)
    createOuputFromWarehouse(order)
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.PICKING.domain)
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.PICKING.domain)
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.PICKING.domain)
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.IN_ROUTE.domain)
    createOuputFromWarehouse(order, SkuOutcomeStatusEnum.IN_ROUTE.domain)

    when:
    def result = ordersService.findAllByUserAndFilter(order.user, new FindCommand() )

    then:
    assert result
    assert result.first().orderDetails.first().statusOutcome.find{ it.status == SkuOutcomeStatusEnum.IN_WAREHOUSE.toString() }.quantity == 5
    assert result.first().orderDetails.first().statusOutcome.find{ it.status == SkuOutcomeStatusEnum.PICKING.toString() }.quantity == 3
    assert result.first().orderDetails.first().statusOutcome.find{ it.status == SkuOutcomeStatusEnum.IN_ROUTE.toString() }.quantity == 2

  }

  void "get status with guides"() {
    setup:
    def order = createOrder(ItemGroupTypeEnum.PRODUCT.domain)
    def orderDetail = order.orderDetails.first()
    orderDetail.outputQuantity = 1
    orderDetail.quantity = 10
    orderDetail.save()

    def skuOutcome = createOuputFromWarehouse(order)
    def carrier = Carrier.build()
    def numberGuide = 'abcd321654xyz'
    def guide = new Guide(guide: numberGuide, carrier: carrier, carrierStatus: CarrierStatusEnum.COMFIRMED.domain).save()
    skuOutcome.guide = guide
    skuOutcome.save()

    when:
    def result = ordersService.findAllByUserAndFilter(order.user, new FindCommand() )

    then:
    assert result
    result.first().orderDetails.first()
    result.first().orderDetails.first().hasProperty('statusOutcome')
    result.first().orderDetails.first().hasProperty('trackingGuide')
    assert result.first().orderDetails.first().statusOutcome.find{ it.status == SkuOutcomeStatusEnum.IN_WAREHOUSE.toString() }.quantity == 9
    assert result.first().orderDetails.first().statusOutcome.find{ it.status == CarrierStatusEnum.COMFIRMED.toString() }.quantity == 1
    assert result.first().orderDetails.first().trackingGuide.find{ it.trackingGuide == numberGuide }
  }

  void "get status when item group type isn't a PRODUCT"() {
    setup:
    def order = createOrder(ItemGroupTypeEnum.SERVICE.domain)

    when:
    def result = ordersService.findAllByUserAndFilter( order.user, new FindCommand() )

    then:
    result
    result.first().orderDetails.first()
    !result.first().orderDetails.first().hasProperty('statusOutcome')
    !result.first().orderDetails.first().hasProperty('trackingGuide')

  }

  void "test count"() {
    setup:
    def order1 = createOrder(ItemGroupTypeEnum.PRODUCT.domain)
    def order2 = createOrder(ItemGroupTypeEnum.SERVICE.domain)
    def order3 = createOrder(ItemGroupTypeEnum.TRAVEL.domain)
    def cmd = new FindCommand()

    when:
    def count = ordersService.countByUserAndFilter( order1.user,  cmd)
    def orders = ordersService.findAllByUserAndFilter( order1.user, cmd)

    then:
    count
    orders
    count == orders.size()

  }

}
