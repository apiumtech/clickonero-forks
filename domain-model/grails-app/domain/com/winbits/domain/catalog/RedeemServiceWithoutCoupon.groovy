package com.winbits.domain.catalog

import com.winbits.domain.orders.OrderDetail

class RedeemServiceWithoutCoupon{
  Integer quantity

  static belongsTo = [orderDetail:OrderDetail]

}
