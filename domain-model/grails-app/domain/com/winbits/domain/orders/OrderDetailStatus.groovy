package com.winbits.domain.orders

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class OrderDetailStatus implements Enumable<OrderDetailStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  OrderDetailStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OrderDetailStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OrderDetailStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}