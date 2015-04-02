package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class ItemGroupType implements Enumable<ItemGroupTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  ItemGroupTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      ItemGroupTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(ItemGroupTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
