package com.winbits.bits

import org.joda.time.DateTime

class Account {
  String number
  BigDecimal balance
  AccountType type
  AccountStatus status
  DateTime dateCreated
  DateTime lastUpdated
  String description

  static hasMany = [transactions: Transaction]

  static constraints = {
    number nullable: true
    transactions nullable: true
    description nullable: true
  }
}
