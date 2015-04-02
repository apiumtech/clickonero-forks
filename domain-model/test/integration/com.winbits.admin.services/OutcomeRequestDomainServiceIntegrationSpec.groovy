package com.winbits.admin.services

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.logistics.OutcomeRequest
import com.winbits.domain.logistics.OutcomeRequestStatusEnum
import com.winbits.domain.logistics.SkuOutcome
import com.winbits.domain.logistics.SkuOutcomeStatusEnum
import com.winbits.domain.logistics.Warehouse
import com.winbits.domain.logistics.WarehouseTypeEnum
import com.winbits.domain.logistics.WmsStatusEnum
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.orders.ShippingOrder
import com.winbits.orders.utils.OrderDataUtils
import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared

class OutcomeRequestDomainServiceIntegrationSpec extends IntegrationSpec {

  @Shared
  Order order
  @Shared
  def skuProfile
  def outcomeRequestDomainService

  void setupSpec() {
    def commonAddress = new CommonAddress(street: "street", county: "county", zipCode: "12345", location: "true", phone: "7896541230", state: "state", internalNumber: "4", externalNumber: "8")
    skuProfile = OrderDataUtils.createSkuProfile()
    order = OrderDataUtils.createOrder(skuProfile)
    order.status = OrderStatusEnum.PAID.domain
    order.save()
    def shippingOrder = new ShippingOrder(betweenStreets: "sss", commonAddress: commonAddress, firstName: "firstName", lastName: "lastName", order: order)
    shippingOrder.save()
    order.shippingOrder = shippingOrder
    order.save()
  }

  void "firstTestOutcomeRequest"() {
    setup:
    def warehouse = Warehouse.build(type: WarehouseTypeEnum.PROVIDER.domain )
    def orderDetail = order.orderDetails.first()
    orderDetail.status = OrderDetailStatusEnum.PAID.domain
    orderDetail.save()
    SkuOutcome.build(orderDetail: orderDetail, status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, warehouse: warehouse)
    SkuOutcome.build(orderDetail: orderDetail, status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, warehouse:  warehouse)
    Order order1 = OrderDataUtils.createOrder(skuProfile)
    order1.status = OrderStatusEnum.PAID.domain
    order1.save()
    def orderDetail1 = order1.orderDetails.first()
    orderDetail1.status = OrderDetailStatusEnum.PAID.domain
    orderDetail1.save()
    def outcomeRequest = OutcomeRequest.build(status: OutcomeRequestStatusEnum.IN_WAREHOUSE.domain,
        wmsStatus: WmsStatusEnum.PENDING.domain, shippingOrder: order.shippingOrder)
    SkuOutcome.build(orderDetail: orderDetail1, outcomeRequest: outcomeRequest)
    when:
    def skuOutcomeMap = outcomeRequestDomainService.findAllSkuOutcomeWhitoutOutcomeRequest()
    outcomeRequestDomainService.findAllSkuOutcome()
    def listOfIds = []
    skuOutcomeMap.each {
      listOfIds.addAll(it.value*.id)
    }
    def skuOutcomes = outcomeRequestDomainService.findAllSkuOutcomeInSkuIds(listOfIds)
    then:
    assert skuOutcomes
  }

}