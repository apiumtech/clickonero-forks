package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum OdcStatusEnum implements PersistentEnum<OdcStatus> {
  PENDING(1L),    // es tambien no surtido
  CANCELLED(2L),
  FULL_CLOSED(3L),
  INCOMPLETY_CLOSED(4L),
  PARTIALLY_COMPLETE(5L),

  POR_VALIDAR(6L)

  final Serializable id

  OdcStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OdcStatus getDomain() {
    OdcStatus.load(id)
  }

  @Override
  boolean equals(OdcStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
