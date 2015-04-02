package com.winbits.domain.logistics

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class IncomeType implements Enumable<IncomeTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  IncomeTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      IncomeTypeEnum.values().find { it.id == theId }
    }
  }

  @Override
  boolean equals(IncomeTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
