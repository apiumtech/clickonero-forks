package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.CouponNotFoundException
import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.domain.orders.OrderDetail
import grails.plugins.springsecurity.Secured
import org.joda.time.DateTime

class CouponListController {

  def couponService

  @Secured('IS_AUTHENTICATED_FULLY')
  def show(Long orderDetailId) {
    def user = getAuthenticatedUser()
    if (user) {
      def orderDetail = OrderDetail.get(orderDetailId)
      if( orderDetail ) {
        if( couponService.ownerValidation(user, orderDetail) ) {
          restpond couponService.findCouponsByOrderDetail(orderDetail)
        } else {
          throw new CouponNotFoundException()
        }
      } else {
        throw new CouponNotFoundException()
      }
    } else {
      throw new InvalidUserException()
    }
  }

}
