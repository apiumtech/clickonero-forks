package com.winbits.domain.orders

import com.winbits.domain.PersistentEnum

public enum OrderStatusEnum implements PersistentEnum<OrderStatus> {
  CHECKOUT(1L),
  ATTEMPTED(2L),
  PENDING(3L),
  PAID(4L),
  CANCELLED(5L),
  FRAUD(6L),
  REFUNDED(7L)

  final Serializable id

  OrderStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OrderStatus getDomain() {
    OrderStatus.load(id)
  }

  @Override
  boolean equals(OrderStatus domainInstance) {
    id == domainInstance?.getId()
  }
}