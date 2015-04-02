package com.winbits.domain.orders

import com.winbits.domain.PersistentEnum

public enum RefundStatusEnum implements PersistentEnum<RefundStatus> {
  PENDING(1L),
  IN_VALIDATION(2L),
  REFUNDED(3L),
  CANCELLED(4L),
  REFUND_PROCESS(5L)

  final Serializable id

  RefundStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  RefundStatus getDomain() {
    RefundStatus.load(id)
  }

  @Override
  boolean equals(RefundStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
