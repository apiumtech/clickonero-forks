package com.winbits.domain.catalog

import com.winbits.domain.affiliation.ShippingAddress

class ZipCodeInfo {
  String zipCode
  String location
  String locationCode
  String locationType
  String locationTypeCode
  String county
  String countyCode
  String city = ''
  String cityCode = ''
  String state
  String stateCode
  String postalOfficeZipCode
  String zone

  static hasMany = [shippingAddresses: ShippingAddress]

  static constraints = {
    city nullable: true
    cityCode nullable: true
  }

  static mapping = {
    version(false)
  }
}
