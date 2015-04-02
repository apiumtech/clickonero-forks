package com.winbits.domain.reference

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class ReferenceCodeStatus implements Enumable<ReferenceCodeStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  ReferenceCodeStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      ReferenceCodeStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(ReferenceCodeStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
