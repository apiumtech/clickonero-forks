package com.winbits.domain.campaigns

import com.winbits.domain.orders.OrderDetail

class OrderDetailCampaign {

  Long campaignId
  Long campaignType

  static belongsTo = [orderDetail: OrderDetail]

  static constraints = {
    orderDetail nullable: false, unique: true
    campaignId nullable: false
    campaignType nullable: false
  }

}