package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum DeliveryDateTypeEnum implements PersistentEnum<DeliveryDateType> {
  ACTIVE_END_DATE_RELATIVE(1L),
  PAID_DATE_RELATIVE(2L)

  final Serializable id

  DeliveryDateTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  DeliveryDateType getDomain() {
    DeliveryDateType.load(id)
  }

  @Override
  boolean equals(DeliveryDateType domainInstance) {
    id == domainInstance?.getId()
  }
}