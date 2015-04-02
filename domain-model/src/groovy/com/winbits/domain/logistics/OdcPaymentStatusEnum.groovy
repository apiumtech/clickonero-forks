package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum OdcPaymentStatusEnum implements PersistentEnum<OdcPaymentStatus> {
  PAID(1L),
  PENDING(2L)

  final Serializable id

  OdcPaymentStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OdcPaymentStatus getDomain() {
    OdcPaymentStatus.load(id)
  }

  @Override
  boolean equals(OdcPaymentStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
