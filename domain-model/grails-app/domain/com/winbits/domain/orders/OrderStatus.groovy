package com.winbits.domain.orders

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class OrderStatus implements Enumable<OrderStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  OrderStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      OrderStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(OrderStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
