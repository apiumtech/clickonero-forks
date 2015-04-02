package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class DeliveryDateType implements Enumable<DeliveryDateTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  DeliveryDateTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      DeliveryDateTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(DeliveryDateTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
