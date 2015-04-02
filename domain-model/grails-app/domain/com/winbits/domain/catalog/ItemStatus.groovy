package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class ItemStatus implements Enumable<ItemStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  ItemStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      ItemStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(ItemStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
