package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class SkuIncomeExtraType implements Enumable<SkuIncomeExtraTypeEnum> {
  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  SkuIncomeExtraTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      SkuIncomeExtraTypeEnum.values().find { it.id == theId }
    }
  }

  @Override
  boolean equals(SkuIncomeExtraTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
