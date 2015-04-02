package com.winbits.domain.campaigns

import com.winbits.domain.orders.CartDetail

class CartDetailCampaign {
  Long campaignId
  Long campaignType

  static belongsTo = [cartDetail: CartDetail]

  static constraints = {
    cartDetail nullable: false, unique: true
    campaignId nullable: false
    campaignType nullable: false
  }
}
