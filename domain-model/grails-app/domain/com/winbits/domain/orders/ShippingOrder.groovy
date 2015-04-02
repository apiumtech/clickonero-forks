package com.winbits.domain.orders

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.catalog.ZipCodeInfo
import org.joda.time.DateTime

class ShippingOrder {

  String firstName
  String lastName
  String indications
  String betweenStreets

  DateTime dateCreated
  DateTime lastUpdated

  ZipCodeInfo zipCodeInfo
  CommonAddress commonAddress

  static belongsTo = [order: Order]

  static embedded = ['commonAddress']

  static transients = ['city', 'fullName']

  static constraints = {
    firstName blank: false, maxSize: 30, defaultValue: 'firstName'
    lastName blank: false, maxSize: 50, defaultValue: 'lastName'
    betweenStreets  nullable: true
    indications maxSize: 150, nullable: true
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
    "$firstName $lastName"
  }

  String getPhone() {
    commonAddress.phone
  }

  String getStreet() {
    commonAddress.street
  }

  String getInternalNumber() {
    commonAddress.internalNumber
  }

  String getExternalNumber() {
    commonAddress.externalNumber
  }

  String getFullAddress() {
    " ${getStreet()} No. ext. ${getExternalNumber()} " +
        "${getInternalNumber()? ("No. Int. " + getInternalNumber()): ""} Col. ${getLocation()} " +
        "${getCounty()} ${getState()}, ciudad: ${getCity()}, " +
        "Indicaciones: ${getIndications()?:''} ${getBetweenStreets()?:''}"
  }
}
