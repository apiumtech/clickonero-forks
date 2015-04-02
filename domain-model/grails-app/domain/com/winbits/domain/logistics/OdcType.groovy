package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OdcType implements Enumable<OdcTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OdcTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      OdcTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OdcTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
