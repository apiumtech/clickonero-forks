package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum ProviderContactTypeEnum implements PersistentEnum<ProviderContactType> {
  MAIN(1L),
  FINANCIAL(2L),
  MARKETING(3L)

  final Serializable id

  ProviderContactTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  ProviderContactType getDomain() {
    ProviderContactType.load(id)
  }

  @Override
  boolean equals(ProviderContactType domainInstance) {
    id == domainInstance?.getId()
  }
}