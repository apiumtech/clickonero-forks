package com.winbits.bits

class Bag {
  String name
  String description
  Account account
  BigDecimal partialDepositAmount
  BigDecimal minBalance
  Integer duration

  static constraints = {
    name unique: true
    account unique: true
    description nullable: true
    partialDepositAmount nullable: true
    minBalance nullable: true
    duration nullable: true
  }

  static mapping = {
    version false
    account fetch: "join"
  }
}
