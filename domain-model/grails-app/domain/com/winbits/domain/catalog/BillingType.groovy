package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class BillingType implements Enumable<BillingTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  BillingTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      BillingTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(BillingTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
