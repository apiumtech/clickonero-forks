package com.winbits.domain.affiliation

import com.winbits.domain.catalog.ZipCodeInfo
import org.joda.time.DateTime

class ShippingAddress {

  String firstName
  String lastName
  String lastName2
  String betweenStreets
  String indications
  Boolean main = false

  DateTime dateCreated
  DateTime lastUpdated

  ZipCodeInfo zipCodeInfo
  CommonAddress commonAddress

  static belongsTo = [user: User]

  static embedded = ['commonAddress']

  static transients = ['city', 'fullName']

  static constraints = {
    firstName blank: false, maxSize: 30
    lastName blank: false, maxSize: 50
    lastName2 blank: true, maxSize: 50, nullable: true
    betweenStreets blank: false, nullable: true
    indications blank: false
    zipCodeInfo nullable: true
  }

  String getZipCode() {
    zipCodeInfo?.zipCode ?: commonAddress.zipCode
  }

  String getLocation() {
    zipCodeInfo?.location ?: commonAddress.location
  }

  String getCounty() {
    zipCodeInfo?.county ?: commonAddress.county
  }

  String getState() {
    zipCodeInfo?.state ?: commonAddress.state
  }

  String getCity() {
    zipCodeInfo?.city ?: getCounty()
  }

  String getFullName() {
    "${firstName} ${obtainLastNames()}".trim()
  }

  String obtainLastNames() {
    lastName2 ? "$lastName $lastName2".trim() : lastName
  }

  String getFullAddress() {
    def internalNumber = commonAddress.internalNumber? "Int. ${commonAddress.internalNumber}" : ""
    "${commonAddress.getStreet()} No. ${commonAddress.externalNumber} ${internalNumber} " +
        "Col. ${getLocation()} ${getCounty()} ${getState()} ${getCity()}"
  }
}
