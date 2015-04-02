package com.winbits.domain.sms

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class UserMobileCarrier implements Enumable<UserMobileCarrierEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  UserMobileCarrierEnum getEnum() {
    def theId = getId()
    if (theId) {
      UserMobileStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(UserMobileCarrierEnum enumConstant) {
    getId() == enumConstant.id
  }
}
