package com.winbits.domain.logistics

import org.joda.time.DateTime

class GuideCarrierHistoryStatus {

  DateTime date
  Guide guide
  CarrierStatus carrierStatus
  String transactionId

  static constraints = {
    transactionId nullable: true
  }
}
