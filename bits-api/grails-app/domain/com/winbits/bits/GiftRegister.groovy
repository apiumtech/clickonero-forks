package com.winbits.bits

import org.joda.time.DateTime
import org.joda.time.contrib.hibernate.PersistentDateTime

class GiftRegister {
  Account account
  BigDecimal amount
  String awarder
  String concept
  String justification
  BitsCategory bitsCategory
  DateTime awardedDate
  DateTime lastUpdated
  Long cknOrderId

  static belongsTo = [bitsCategory: BitsCategory]

  static constraints = {
    account nullable: false
    amount nullable: false
    awarder nullable: false
    concept nullable: false
    justification nullable: false
    bitsCategory nullable: false
    awardedDate type: PersistentDateTime, nullable: false
    lastUpdated type: PersistentDateTime, nullable: false
    cknOrderId nullable: true
  }
}
