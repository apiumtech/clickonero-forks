package com.winbits.domain.catalog

import org.joda.time.DateTime

class BankAccount {
  String name
  String email
  String accountNumber
  String interbankAccountNumber
  Boolean main = false

  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false

  Bank bank

  static belongsTo = [providerFiscalData: ProviderFiscalData]

  static constraints = {
    email email: true, blank: false
    interbankAccountNumber size: 18..18, matches: "[0-9]+"
    accountNumber maxSize: 20
    interbankAccountNumber maxSize: 25
    name maxSize: 100
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }
}
