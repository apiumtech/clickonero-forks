package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OutcomeType implements Enumable<OutcomeTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OutcomeTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      OutcomeTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OutcomeTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
