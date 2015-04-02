package com.winbits.bits

import org.joda.time.DateTime

class Transaction {
  BigDecimal amount
  BigDecimal balance
  String concept
  DateTime dateCreated
  DateTime lastUpdated

  static hasMany = [withdrawals: Withdrawal, deposits: Deposit]
  static belongsTo = [account: Account]

  static constraints = {
    concept blank: false, nullable: false
  }

  static mapping = {
  }
}
