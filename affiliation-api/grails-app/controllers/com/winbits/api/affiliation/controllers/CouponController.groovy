package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.CouponNotFoundException
import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.domain.orders.OrderDetail
import grails.plugins.springsecurity.Secured

class CouponController {

  def couponService

  @Secured('IS_AUTHENTICATED_FULLY')
  def show(CouponCommand cmd) {
    if( cmd.hasErrors() || !cmd.validate() ) {
      throw new CouponNotFoundException()
    } else {
      def user = getAuthenticatedUser()
      if (user) {
        def result = couponService.getCouponById(cmd.couponId, cmd.format, cmd.orderDetailId, user.id)
        if(result) {
          def orderDetail = OrderDetail.get(result.orderDetailId)
          if( orderDetail && couponService.ownerValidation(user, orderDetail) ) {
            def coupon = [coupon: result]
            restpond coupon
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
}

class CouponCommand {
  Long couponId
  String format
  Long orderDetailId

  static constraints = {
    couponId nullable: false, blank: false
    format nullable: false, blank: false
    orderDetailId nullable: false, blank: false
  }
}
