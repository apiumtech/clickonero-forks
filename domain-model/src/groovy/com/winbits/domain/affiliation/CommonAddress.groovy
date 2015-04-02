package com.winbits.domain.affiliation

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class CommonAddress {
  String street
  String internalNumber
  String externalNumber
  String phone
  String zipCode
  String location
  String county
  String state

  static mapping = {
    street column: 'street'
    internalNumber column: 'internal_number'
    externalNumber column: 'external_number'
    phone column: 'phone'
    zipCode column: 'zip_code'
    location column: 'location'
    county column: 'county'
    state column: 'state'
  }

  static constraints = {
    street(blank: false, maxSize: 75)
    externalNumber(blank: false, maxSize: 25, defaultValue: 'X')
    internalNumber(nullable: true, maxSize: 25)
    zipCode(nullable: true, maxSize: 5)
    location(nullable: true, blank: false, size: 2..50)
    phone(nullable: true, maxSize: 15)
    county(nullable: true, blank: false, size: 2..50)
    state(nullable: true, blank: false, size: 2..30)
  }

  @Override
  int hashCode() {
    new HashCodeBuilder()
    .append(street)
    .append(internalNumber)
    .append(externalNumber)
    .append(phone)
    .append(zipCode)
    .append(location)
    .append(county)
    .append(state).toHashCode()
  }

  @Override
  boolean equals(Object obj) {
    if (!(obj instanceof CommonAddress)) {
      return false
    }
    if (super.equals(obj)) {
      return true
    }
    def rhs = obj as CommonAddress
    new EqualsBuilder()
        .append(street, rhs.street)
        .append(internalNumber, rhs.internalNumber)
        .append(externalNumber, rhs.externalNumber)
        .append(phone, rhs.phone)
        .append(zipCode, rhs.zipCode)
        .append(location, rhs.location)
        .append(county, rhs.county)
        .append(state, rhs.state)
        .isEquals()
  }
}
