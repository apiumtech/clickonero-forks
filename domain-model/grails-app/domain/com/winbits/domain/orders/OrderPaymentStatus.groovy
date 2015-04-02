package com.winbits.domain.orders

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class OrderPaymentStatus implements Enumable<OrderPaymentStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  OrderPaymentStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OrderPaymentStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OrderPaymentStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
