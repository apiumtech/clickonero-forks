package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.CouponNotAvailableException
import com.winbits.api.affiliation.exception.CouponNotFoundException
import com.winbits.api.clients.coupon.CouponClient
import com.winbits.api.clients.service.ServiceClient
import com.winbits.api.clients.travel.TravelClient
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ItemGroup
import com.winbits.domain.catalog.ItemGroupTypeEnum
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderPaymentStatusEnum

import org.joda.time.DateTime

class CouponService {

  public static final AVAILABLE_WHILE_RUNNING = "AVAILABLE_WHILE_RUNNING"
  public static final NOT_AVAILABLE_WHILE_RUNNING = "NOT_AVAILABLE_WHILE_RUNNING"
  public static final GENERATED_STATUS = "GENERATED"

  CouponClient couponClient
  ServiceClient serviceClient
  TravelClient travelClient

  def findCouponsByOrderDetail(OrderDetail orderDetail) {
    try {
      def result = couponClient.findCouponsByOrderDetailId(orderDetail.id)
      def expireCouponDate = getExpireCouponDate(orderDetail.skuProfile.itemGroupProfile.itemGroup)
      result.coupons = result.coupons.collect {
        def availableCouponDate = getAvailableCouponDate(orderDetail.id, it.couponType)?.toString("dd/MM/yyyy")
        it.code = filterCouponCode(it) // filter coupon code if not available
        it + [availableCouponDate: availableCouponDate, expireCouponDate: expireCouponDate]
      }
    } catch (e) {
      log.error("Error: when coupon-client-api was called", e)
      throw new CouponNotFoundException()
    }
  }

  def filterCouponCode(Map couponDetail) {
    couponDetail.status == GENERATED_STATUS ? '' : couponDetail.code
  }

  def getExpireCouponDate(ItemGroup itemGroup) {
    def result = getServiceInfoByItemGroup(itemGroup)
    result?.response?.json?.claimEnd
  }

  def getCouponById(Long couponId, String format, Long orderDetailId, Long userId) {
    def orderDetail = OrderDetail.get(orderDetailId)
    if( orderDetail ) {
      def result =  couponClient.getCouponById(couponId, format, orderDetail.order.id, userId)
      def statusCode = result.response?.statusCode
      if(statusCode && statusCode != 200) {
        switch (statusCode) {
          case 404: throw new CouponNotFoundException()
          case 405: throw new CouponNotAvailableException()
          default:  break
        }
      }
      return result.response?.json
    } else {
      throw new CouponNotFoundException()
    }
  }

  boolean ownerValidation(User user, OrderDetail orderDetail) {
    orderDetail.order.user.id == user.id
  }

  def getAvailableCouponDate(Long orderDetailId, String couponType) {
    def orderDetail = OrderDetail.get(orderDetailId)
    if(orderDetail.status.enum == OrderDetailStatusEnum.PAID) {
      if(AVAILABLE_WHILE_RUNNING == couponType) {
        return findOrderPayment(orderDetail)
      } else if(NOT_AVAILABLE_WHILE_RUNNING == couponType) {
        def bargainEndDate = findBargainEndDate(orderDetail)
        return bargainEndDate?.plusDays(1)
      }
    }
  }

  def DateTime findBargainEndDate(OrderDetail orderDetail) {
    orderDetail.skuProfile?.itemGroupProfile?.itemGroup?.activeEnd
  }

  def findOrderPayment(OrderDetail orderDetail) {
    orderDetail.order?.orderPayments?.find { it.status.enum == OrderPaymentStatusEnum.PAID }?.paidDate
  }

  def getServiceInfoByItemGroup(ItemGroup itemGroup) {
    if( itemGroup.itemGroupType.enum == ItemGroupTypeEnum.SERVICE) {
      serviceClient.findServiceByItemGroupId(itemGroup.id)
    } else if( itemGroup.itemGroupType.enum == ItemGroupTypeEnum.TRAVEL) {
      def response = travelClient.findTravelByItemGroupId(itemGroup.id)
      log.info "item group: $itemGroup.id  --  $response?.response?.json?.requireCoupon"
      response
    }
  }

  def determineWithCoupon(ItemGroup itemGroup) {
    def result = getServiceInfoByItemGroup(itemGroup)
    result?.response?.json?.requireCoupon?: false
  }
}
