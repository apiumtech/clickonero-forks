package com.winbits.domain.logistics

import com.winbits.domain.Enumable

class WmsStatus implements Enumable<WmsStatusEnum> {

  String name
  String description

  static transients = ['enum']


  static constraints = {
    name blank: false
    description nullable: true
  }

  @Override
  WmsStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      WmsStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(WmsStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
