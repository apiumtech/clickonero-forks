package com.winbits.api.clients.coupon

interface CouponClient {

  Map findCouponsByOrderDetailId(Long id)

  Map getCouponById(Long id, String format, Long orderId, Long userId)
}
