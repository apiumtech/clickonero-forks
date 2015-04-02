package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OdcDetailStatus implements Enumable<OdcDetailStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OdcDetailStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OdcDetailStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OdcDetailStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
