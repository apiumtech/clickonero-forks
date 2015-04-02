package com.winbits.domain.orders

import com.winbits.domain.campaigns.OrderDetailCampaign
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.logistics.DeliveryDateTypeEnum
import com.winbits.domain.reference.ReferenceCode
import org.joda.time.DateTime

class OrderDetail {

  BigDecimal shippingAmount = 0.0
  BigDecimal cashAmount = 0.0
  BigDecimal bitsAmount = 0.0
  BigDecimal amount = 0.0
  BigDecimal cashback = 0.0
  Integer quantity
  Integer outputQuantity = 0
  OrderDetailStatus status

  DateTime dateCreated
  DateTime lastUpdated

  SkuProfile skuProfile
  OrderDetail refundedDetail

  List<Map> warnings

  static belongsTo = [order: Order]
  static hasOne= [orderDetailCampaign : OrderDetailCampaign]
  static hasMany = [refundDetails: RefundDetail, referenceCodes: ReferenceCode]
  static transients = ['warnings']

  static constraints = {
    shippingAmount min : 0.0, max:10000000000.0, scale: 2
    cashAmount min : 0.0, max:10000000000.0, scale: 2
    bitsAmount min : 0.0, max:10000000000.0, scale: 2
    cashback min : 0.0, max:10000000000.0, scale: 2
    amount max:10000000000.0, scale: 2
    quantity min : 0
    refundedDetail nullable:true
    orderDetailCampaign nullable: true
  }

  static mapping = {
    quantity sqlType:'smallint'
    sort id:'desc'
    refundDetails cascade:"all-delete-orphan", lazy:false
  }

    DateTime getEstimatedDeliveryDate() {
        def itemGroup = skuProfile?.itemGroupProfile?.itemGroup
        if (itemGroup?.deliveryDateType) {
            def baseDate = itemGroup.deliveryDateType.enum == DeliveryDateTypeEnum.ACTIVE_END_DATE_RELATIVE ?
                itemGroup.activeEnd : order.paidDate

            if (baseDate) {
                baseDate.plusDays(itemGroup.deliveryDateDays ?: 0)
            }
        }
    }
}
