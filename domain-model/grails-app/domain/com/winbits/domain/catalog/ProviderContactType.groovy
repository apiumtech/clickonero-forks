package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class ProviderContactType implements Enumable<ProviderContactTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  ProviderContactTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      ProviderContactTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(ProviderContactTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
