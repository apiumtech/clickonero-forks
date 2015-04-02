package com.winbits.domain.orders

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class RefundStatus implements Enumable<RefundStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  RefundStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      RefundStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(RefundStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
