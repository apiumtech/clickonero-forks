package com.winbits.domain.orders

import com.winbits.domain.PersistentEnum

public enum OrderPaymentStatusEnum implements PersistentEnum<OrderPaymentStatus> {
  PENDING(1L),
  PAID(2L),
  CANCELLED(3L),
  FRAUD(4L),
  REFUNDED(5L),
  FAILED(6L),
  REVIEW(7L)

  final Serializable id

  OrderPaymentStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OrderPaymentStatus getDomain() {
    OrderPaymentStatus.load(id)
  }

  @Override
  boolean equals(OrderPaymentStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
