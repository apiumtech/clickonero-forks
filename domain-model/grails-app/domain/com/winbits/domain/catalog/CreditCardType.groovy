package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class CreditCardType implements Enumable<CreditCardTypeEnum> {

  String name

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 50 
  }

  @Override
  CreditCardTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      CreditCardTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(CreditCardTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
