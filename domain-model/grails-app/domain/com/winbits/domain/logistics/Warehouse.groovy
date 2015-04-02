package com.winbits.domain.logistics

import com.winbits.domain.catalog.SkuIncome

class Warehouse {

  String name
  Boolean enabled

  String country
  String city
  String state
  String county
  String postalCode

  String street
  String externalNumber
  String betweenStreets
  String indications
  WarehouseType type

  static hasMany = [skuIncomes: SkuIncome]


  static constraints = {
    name blank: false
  }
}
