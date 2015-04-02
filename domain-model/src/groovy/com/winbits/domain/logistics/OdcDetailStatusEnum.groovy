package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum OdcDetailStatusEnum implements PersistentEnum<OdcDetailStatus> {
  PENDING(1L),
  COMPLETE(2L),
  CANCELLED(3L),
  INCOMPLETE(4L)

  final Serializable id

  OdcDetailStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OdcDetailStatus getDomain() {
    OdcDetailStatus.load(id)
  }

  @Override
  boolean equals(OdcDetailStatus domainInstance) {
    domainInstance.enum == this
  }
}