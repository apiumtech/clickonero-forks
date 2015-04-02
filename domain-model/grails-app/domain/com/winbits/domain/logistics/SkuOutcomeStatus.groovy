package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class SkuOutcomeStatus implements Enumable<SkuOutcomeStatusEnum> {

  String name
  String description

  static transients = ['enum']
  static hasMany = [carrierStatus : CarrierStatus]
  static belongsTo = CarrierStatus


  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  SkuOutcomeStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      SkuOutcomeStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(SkuOutcomeStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
