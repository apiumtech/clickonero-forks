package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OdcPaymentStatus implements Enumable<OdcPaymentStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OdcPaymentStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OdcPaymentStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OdcPaymentStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
