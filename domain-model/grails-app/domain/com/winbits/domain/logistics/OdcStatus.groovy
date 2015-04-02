package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OdcStatus implements Enumable<OdcStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OdcStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OdcStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OdcStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
