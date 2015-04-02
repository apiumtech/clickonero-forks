package com.winbits.domain.catalog

import org.joda.time.DateTime

class ProviderFiscalData {
  String name
  String taxId
  Integer paymentDay
  String accountingId

  String street
  String location
  String city
  String zipCode
  String internalNumber
  String externalNumber
  String state
  BillingType billingType

  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false

  Provider provider

  static hasMany = [bankAccounts: BankAccount]

  static constraints = {
    paymentDay min: 1, max: 31, nullable: true
    accountingId maxSize: 32
    city maxSize: 75
    externalNumber maxSize: 25
    taxId nullable: true, maxSize: 32, validator: { val, obj ->
      if (!(val ==~ /^(([A-ZÑ&]|[a-zñ&]){3,4})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))/)) {
         return ['providerFiscalData.taxId.number.message', val]
      }
      return true
    }
    internalNumber nullable: true, maxSize: 25
    location maxSize: 75
    name maxSize: 250
    state maxSize: 75
    street maxSize: 75
    zipCode maxSize: 10


  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }
}
