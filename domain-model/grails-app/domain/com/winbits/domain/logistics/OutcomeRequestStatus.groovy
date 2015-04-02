package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class OutcomeRequestStatus implements Enumable<OutcomeRequestStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  OutcomeRequestStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OutcomeRequestStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OutcomeRequestStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
