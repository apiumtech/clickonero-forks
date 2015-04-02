package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum TelephoneTypeEnum implements PersistentEnum<TelephoneType> {
  MOBILE(1L),
  HOME(2L),
  OFFICE(3L)

  final Serializable id

  TelephoneTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  TelephoneType getDomain() {
    TelephoneType.load(id)
  }

  @Override
  boolean equals(TelephoneType domainInstance) {
    id == domainInstance?.getId()
  }
}