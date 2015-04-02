package com.winbits.domain.logistics

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class InOutSkuHistoryType implements Enumable<InOutSkuHistoryTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  InOutSkuHistoryTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      InOutSkuHistoryTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(InOutSkuHistoryTypeEnum enumConstant) {
    getId() == enumConstant.id
  }

}
