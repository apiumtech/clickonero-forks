package com.winbits.domain.sms

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability
import com.winbits.domain.orders.OrderStatusEnum

@Enumability
class UserMobileStatus implements Enumable<UserMobileStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  UserMobileStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      UserMobileStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(UserMobileStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
