package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class AttributeType implements Enumable<AttributeTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  AttributeTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      AttributeTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(AttributeTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
