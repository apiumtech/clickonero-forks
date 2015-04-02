package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum BillingTypeEnum implements PersistentEnum<BillingType> {
  BILLING_REQUIRED(1L),
  BILLING_NOT_REQUIRED(2L),
  BILLING_ISSUED(3L)

  final Serializable id

  BillingTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  BillingType getDomain() {
    BillingType.load(id)
  }

  @Override
  boolean equals(BillingType domainInstance) {
    id == domainInstance?.getId()
  }
}