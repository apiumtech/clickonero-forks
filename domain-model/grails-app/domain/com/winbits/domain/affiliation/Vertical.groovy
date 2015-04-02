package com.winbits.domain.affiliation


class Vertical {
  String name
  String baseUrl
  String logo
  Boolean active = true
  Integer order = 0
  BigDecimal maxPerVertical = 0.0

  static constraints = {
    name(unique: true)
    baseUrl(unique: true)
    logo(nullable: true)
    maxPerVertical(min: 0.0)
    order nullable: false
  }
  
  static mapping = {
    active defaultValue: '1'
    order column: '`order`', defaultValue: '0'
  }

  Map toSubscriptionMap(subscriptions) {
    def active = subscriptions.any { it.vertical.id == id }
    [id: id, name: name, active: active]
  }
}
