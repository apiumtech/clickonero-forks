package com.winbits.domain.orders

import com.winbits.domain.PersistentEnum

public enum OrderDetailStatusEnum implements PersistentEnum<OrderDetailStatus> {
  CHECKOUT(1L),
  ATTEMPTED(2L),
  PENDING(3L),
  PAID(4L),
  CANCELLED(5L),
  REFUNDED(6L),
  FRAUD(7L),
  QUARANTINE(8L),
  COLLECTED(9L)

  final Serializable id

  OrderDetailStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OrderDetailStatus getDomain() {
    OrderDetailStatus.load(id)
  }

  @Override
  boolean equals(OrderDetailStatus domainInstance) {
    id == domainInstance?.getId()
  }
}