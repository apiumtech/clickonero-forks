package com.winbits.bits

import org.joda.time.DateTime
import org.joda.time.contrib.hibernate.PersistentDateTime

class RewardRegister {
  Account account
  Long wbOrderDetailId
  BigDecimal amount
  String awarder
  String concept
  String justification
  BitsCategory bitsCategory
  DateTime awardedDate
  DateTime lastUpdated

  static belongsTo = [bitsCategory: BitsCategory]

  static constraints = {
    account nullable: false
    wbOrderDetailId nullable: false
    amount nullable: false
    awarder nullable: false
    concept nullable: false
    justification nullable: false
    bitsCategory nullable: false
    awardedDate type: PersistentDateTime, nullable: false
    lastUpdated type: PersistentDateTime, nullable: false
  }
}
