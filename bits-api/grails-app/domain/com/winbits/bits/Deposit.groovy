package com.winbits.bits

import org.joda.time.DateTime

class Deposit {
  BigDecimal amount
  BigDecimal openingBalance
  DateTime activationDate
  DateTime expirationDate
  DateTime dateCreated
  DateTime lastUpdated
  Account account
  Transaction transaction

  Boolean active = true


  static constraints = {
    expirationDate nullable: false
    activationDate nullable: true
  }
}
