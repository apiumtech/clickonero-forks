package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.FindCommand
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ItemGroup
import com.winbits.domain.logistics.OutcomeRequest
import com.winbits.domain.logistics.OutcomeRequestStatusEnum
import com.winbits.domain.logistics.SkuOutcome
import com.winbits.domain.logistics.SkuOutcomeStatusEnum
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderPayment
import com.winbits.domain.orders.OrderPaymentStatusEnum
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.catalog.ItemGroupTypeEnum
import org.springframework.transaction.annotation.Transactional

class OrdersService {
  def max = 20
  def statusIn = [OrderStatusEnum.PAID.id, OrderStatusEnum.PENDING.id, OrderStatusEnum.REFUNDED.id]
  def couponService

  @Transactional(readOnly = true)
  def findAllByUserAndFilter(User user, FindCommand findCommand) {
    def orders = Order.createCriteria().list(max: (findCommand.max ?: max), offset: (findCommand.offset ?: 0) ) {
      createAlias("status", "status")
      if( findCommand.status ) {
        eq('status.id', findCommand.status)
      } else {
        'in'("status.id", statusIn)
      }
      eq 'user', user
      if ( findCommand.sort && !"dateCreated".equals(findCommand.sort) ) {
        order( findCommand.sort, "desc" )
      } else {
        order("dateCreated", "desc")
      }
      join 'status'
    }
    if (orders) {
      def details = OrderDetail.createCriteria().list() {
        'in'('order', orders)
        order("dateCreated", "desc")
        join 'status'
        join 'skuProfile'
        join 'skuProfile.itemGroupProfile'
        join 'skuProfile.itemGroupProfile.itemGroup'
        join 'skuProfile.itemGroupProfile.itemGroup.brand'
        join 'skuProfile.itemGroupProfile.itemGroup.itemGroupType'
        join 'skuProfile.vertical'
        join 'skuProfile.sku'
        join 'skuProfile.sku.item'
        select 'skuProfile.sku.attributes'
        select 'skuProfile.sku.item.images'
      }

      def detailsWithCouponFlag = getWithCouponFlag(details)
      def detailsWithGuides = findGuidesByDetails(detailsWithCouponFlag)
      def detailsByOrderId = detailsWithGuides.groupBy { it.orderId }
      orders.each { it.metaClass.details = detailsByOrderId[it.id] }
    }
    orders
  }

  def getWithCouponFlag(List<OrderDetail> orderDetails) {
    def itemGroupIds = orderDetails.skuProfile.itemGroupProfile.itemGroup.id.unique()
    def withCouponFlagMap = [:]
    itemGroupIds.each {
      def flag = couponService.determineWithCoupon(ItemGroup.get(it))
      withCouponFlagMap.put(it, flag)
    }
    orderDetails.each {
      it.metaClass.withCoupon = withCouponFlagMap.get(it.skuProfile.itemGroupProfile.itemGroup.id)
    }

    orderDetails
  }

  def findGuidesByDetails(List<OrderDetail> details) {

    def detailsAsProducts = details.findAll {
      def itemGroup = it.skuProfile.itemGroupProfile.itemGroup
      itemGroup.itemGroupType.enum == ItemGroupTypeEnum.PRODUCT && itemGroup.requiresShipping
    }
    if(detailsAsProducts) {
      def inWarehouseStatus = SkuOutcomeStatusEnum.IN_WAREHOUSE.domain
      def defaultStatus = (inWarehouseStatus.description?: inWarehouseStatus.name)
      detailsAsProducts.each {
        def outputs = SkuOutcome.findAllByOrderDetail(it)
        def statusOutcome = outputs ? fillStatusForDetailWithoutOutputs(getStatusByOutputs(outputs), it, defaultStatus) :
            [ [status: defaultStatus, quantity: it.quantity, sort: 0] ]
        it.metaClass.statusOutcome = statusOutcome
        it.metaClass.trackingGuide = getTrackingGuideByDetail(outputs)
      }
    }
    details
  }

  def fillStatusForDetailWithoutOutputs(statusList, detail, defaultStatus) {
    def totalWithoutOutput = detail.quantity - detail.outputQuantity
    if (totalWithoutOutput > 0 ) {
      def statusInWarehouse = statusList.find{ it.status == defaultStatus }
      if(statusInWarehouse) {
        statusInWarehouse.quantity = statusInWarehouse.quantity + totalWithoutOutput
      } else {
        statusInWarehouse = [status: defaultStatus, quantity: totalWithoutOutput, sort: 0]
        statusList << statusInWarehouse
      }
    }
    statusList
  }

  def getTrackingGuideByDetail(List<SkuOutcome> outputs) {
    def trackingGuides = []
    def trackingGuideNumbers = outputs?.findAll{ it.guide }?.groupBy { g -> g.guide.guide }
    if(trackingGuideNumbers) {
      trackingGuideNumbers.each { k, v ->
        trackingGuides << [quantity: v.size(), trackingGuide: k]
      }
    }
    trackingGuides
  }

  def getStatusByOutputs(List<SkuOutcome> outputs) {
    def outputsWithGuides = outputs.findAll {it.guide}
    def idsWithGuide = outputsWithGuides?.id
    def restOutputs = outputs.findAll { !(it.id in idsWithGuide)  }
    def availableStatusWithGuide = outputsWithGuides?.guide?.carrierStatus?.unique()
    def availableOutcomeStatus = restOutputs?.status?.unique()
    def statusWithGuide = getSkuOutcomeStatus( outputsWithGuides, availableStatusWithGuide ){
      outputsA, statusString ->
        outputsA.count { it.guide.carrierStatus.name == statusString}
    }

    def statusWithOutGuide = getSkuOutcomeStatus(restOutputs, availableOutcomeStatus){
      outputsA, statusString ->
        outputsA.count { it.status.name == statusString }
    }

    statusWithOutGuide?.sort {it.sort} + statusWithGuide?.sort {it.sort}
  }

  def getSkuOutcomeStatus(List<SkuOutcome> outputs, availableStatus, Closure calculateQuantity) {
    def statusResult = []
    availableStatus.each { item ->
      def statusItem = [status: (item.description?: item.name), quantity: calculateQuantity.call(outputs, item.name), sort: item.id ]
      statusResult << statusItem
    }
    statusResult
  }

  @Transactional(readOnly = true)
  def findAllTicketPaymentsInOrders(List<Order> orders) {
    def pendingOrders = orders.findAll{
      it.status.enum == OrderStatusEnum.PENDING
    }
    if (pendingOrders) {
      OrderPayment.withCriteria {
        eq('status',OrderPaymentStatusEnum.PENDING.domain)
        paymentMethod {
          'in'('identifier',['oxxo.bc','reference.hsbc'])
        }
        'in'('order',pendingOrders)
      }
    } else {
      []
    }
  }

  @Transactional(readOnly = true)
  def countByUserAndFilter(User user, FindCommand findCommand) {
    def orders = Order.createCriteria().count() {
      createAlias("status", "status")
      if( findCommand.status ) {
        eq('status.id', findCommand.status)
      } else {
        'in'("status.id", statusIn)
      }
      eq 'user', user
      join 'status'
    }
  }

  @Transactional(readOnly = true)
  def countOutcomePending(User user) {
    def orderDetailCount = 0
    def orders = Order.findAllByUserAndStatus user, OrderStatusEnum.PAID.domain
    def orderDetails = []
    orders.each{order ->
      order.orderDetails.each {od ->
        if(od.skuProfile.itemGroupProfile.itemGroup.itemGroupType.enum == ItemGroupTypeEnum.PRODUCT && od.status.enum == OrderDetailStatusEnum.PAID)
          orderDetails.add od
      }
    }
    def refundDetails = OrderDetail.findAllByRefundedDetailInList(orderDetails)
    def orderDetailsWithItemsNotDelivery = orderDetails.findAll{it.outputQuantity < it.quantity}
    def skuOutcomes = SkuOutcome.findAllByOrderDetailInListAndStatusInList(orderDetails,
          [SkuOutcomeStatusEnum.IN_WAREHOUSE.domain, SkuOutcomeStatusEnum.IN_ROUTE.domain, SkuOutcomeStatusEnum.PICKING.domain, SkuOutcomeStatusEnum.PACKING.domain])
    if(!skuOutcomes.isEmpty()){
      orderDetailCount += skuOutcomes.size()
    }
    orderDetailsWithItemsNotDelivery.each{
      orderDetailCount += (it.quantity -it.outputQuantity)
    }
    if(refundDetails){
    orderDetailCount -=  refundDetails.quantity.sum()
    }
    orderDetailCount
  }

  @Transactional(readOnly = true)
  getRefundOrders(Map detailsByOrderId){
    def orderDetailMapByOrder = [:]
    detailsByOrderId.each{key, value ->
      def refundDetails = value.findAll{od-> od.status.enum == OrderDetailStatusEnum.REFUNDED}.groupBy {it.refundedDetail}
      if(refundDetails){
        refundDetails.each{keyRefund, valuesRefund ->
          def orderDetails = value
          def refundDetail = orderDetails.find{keyRefund}
          def refundQuantity = valuesRefund.quantity.sum()
          orderDetails.remove refundDetail
          orderDetails.removeAll(valuesRefund)
          if(refundQuantity < refundDetail.quantity){
            refundDetail.quantity -= refundQuantity
            orderDetails.add refundDetail
          }
          def refundedDetail = valuesRefund.first()
          refundedDetail.quantity = refundQuantity
          orderDetails.add(refundedDetail)
          orderDetailMapByOrder.put(key, orderDetails)

        }
      }else{
        orderDetailMapByOrder.put(key, value)
      }

    }
    orderDetailMapByOrder
  }
}
