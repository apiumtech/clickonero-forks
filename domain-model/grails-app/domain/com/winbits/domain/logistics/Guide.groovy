package com.winbits.domain.logistics

import org.joda.time.DateTime

class Guide {
  String guide

  DateTime dateCreated
  DateTime lastUpdated
  Carrier carrier
  CarrierStatus carrierStatus

  static constraints = {
  }


  def afterInsert() {
    GuideCarrierHistoryStatus.withNewSession {
      new GuideCarrierHistoryStatus(date: new DateTime(),
          guide: this, carrierStatus: this.carrierStatus
      ).save()
    }
  }

}
