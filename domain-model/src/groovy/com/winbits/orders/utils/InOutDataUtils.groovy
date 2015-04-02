package com.winbits.orders.utils

import com.winbits.domain.admin.AdminUser
import com.winbits.domain.catalog.Sku
import com.winbits.domain.logistics.*
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail

/**
 * Created by manolo on 12/17/13.
 */
class InOutDataUtils {

  def static createOutcomeRequestExtra(){
    def adminUser = AdminUser.build()
    def type = OutcomeType.build()
    new OutcomeRequestExtra(receiver: "receiver",
        approver: adminUser, type: type, wmsStatus: WmsStatusEnum.PENDING.domain )
  }

  def static createSkuOutcomeExtra(outcomeRequestExtra, wmsStatus, quantity, sku, warehouse) {
    def skuOutcomeExtra = new SkuOutcomeExtra(sku: sku,
        warehouse: warehouse, status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain,
        wmsStatus: wmsStatus, unitCost: 10, totalCost: 50, quantity: quantity
    )
    outcomeRequestExtra.addToSkuOutcomeExtras(skuOutcomeExtra)
    outcomeRequestExtra.save()
    skuOutcomeExtra.save()
  }

  def static createSkuOutcome(Order order, Warehouse warehouse) {
    def orderDetail = order.orderDetails.first()
    def shippingOrder = OrderDataUtils.createShippingOrder(order)
    def outcomeRequest = new OutcomeRequest(status: OutcomeRequestStatusEnum.IN_WAREHOUSE.domain,
        shippingOrder: shippingOrder, wmsStatus: WmsStatusEnum.SUCCESS.domain).save()
    new SkuOutcome(warehouse: warehouse, orderDetail: orderDetail,
        status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, consecutive: 1, quantity: 1,
        outcomeRequest: outcomeRequest).save()
  }

  def static createSkuOutcomeWithoutOutcomeRequest(OrderDetail orderDetail, Warehouse warehouse) {
    orderDetail.outputQuantity += 1
    orderDetail.save()
    new SkuOutcome(warehouse: warehouse, orderDetail: orderDetail,
        status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, consecutive: 1, quantity: 1).save()
  }

  def static createSkuOutcomeToDetail(OrderDetail detail, Warehouse warehouse) {
    detail.outputQuantity += 1
    detail.save()
    new SkuOutcome(warehouse: warehouse, orderDetail: detail,
        status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, consecutive: 1, quantity: 1).save()
  }

  def static createSkuOutcomeExtra(Sku sku, Warehouse warehouse) {
    def adminUser = AdminUser.findOrSaveWhere(username: "test_admin${new Date().getTime()}@winbits.com", password: "s3cr3t0")
    def outcomeRequestExtra = new OutcomeRequestExtra(wmsStatus: WmsStatusEnum.SUCCESS.domain,
        receiver: 'Receiver', approver: adminUser,
        type: OutcomeTypeEnum.VENTA_EMPLEADO.domain).save()
    new SkuOutcomeExtra(warehouse: warehouse,
        status: SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, quantity: 10,
        unitCost: 10, totalCost: 100,
        outcomeRequestExtra: outcomeRequestExtra, sku: sku).save()
  }

  def static createSkuBalance(Sku sku, Warehouse warehouse, Integer balance) {
    def skuBalance = SkuBalance.findOrSaveWhere(sku: sku, warehouse: warehouse)
    skuBalance.balance = balance
    skuBalance.save()
  }

  def static setupWarehouses(sku1, balance1, balance2) {
    def warehouse1 = Warehouse.build()
    def warehouse2 = Warehouse.build()
    def provider = sku1.item.itemGroup.provider
    def skuBalance1 = new SkuBalance(sku: sku1, warehouse: warehouse1, balance: balance1).save()
    def skuBalance2 = new SkuBalance(sku: sku1, warehouse: warehouse2, balance: balance2).save()
    [skuBalance1, skuBalance2]
  }
}
