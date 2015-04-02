package com.winbits.bits

import org.joda.time.DateTime

class Withdrawal {
  BigDecimal amount
  Deposit deposit
  DateTime dateCreated
  DateTime lastUpdated
  Account account
  Deposit target
  Boolean refunded = false
  Transaction transaction

  static constraints = {
  }
}
