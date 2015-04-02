package com.winbits.domain.catalog

class ProviderContact {
  String email
  String firstName
  String lastName
  String mainTelephone
  TelephoneType mainTelephoneType
  String secondaryTelephone
  TelephoneType secondaryTelephoneType
  ProviderContactType providerContactType

  Boolean deleted = false

  Provider provider

  static constraints = {
    email email: true, maxSize: 150
    firstName maxSize: 75
    lastName maxSize: 75
    mainTelephone maxSize: 15, matches: "[0-9]{10}"
    secondaryTelephone maxSize: 15, matches: "[0-9]{10}", nullable: true
    secondaryTelephoneType nullable: true
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

  String getFullName() {
    "$firstName $lastName"
  }
}
