package com.winbits.domain.logistics

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class WarehouseType implements Enumable<WarehouseTypeEnum>{

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  WarehouseTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      WarehouseTypeEnum.values().find { it.id == theId }
    }
  }

  @Override
  boolean equals(WarehouseTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
