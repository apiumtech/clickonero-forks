package com.winbits.domain.reference

import com.winbits.domain.PersistentEnum

public enum ReferenceCodeStatusEnum implements PersistentEnum<ReferenceCodeStatus> {
  PENDING(1L),
  SENDING(2L)

  final Serializable id

  ReferenceCodeStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  ReferenceCodeStatus getDomain() {
    ReferenceCodeStatus.load(id)
  }

  @Override
  boolean equals(ReferenceCodeStatus domainInstance) {
    id == domainInstance?.getId()
  }
}

