package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum IncomeTypeEnum implements PersistentEnum<IncomeType> {
  VIRTUAL(1L),
  CONSIGNMENT(2L),
  FIRM(3L)

  final Serializable id

  IncomeTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  IncomeType getDomain() {
    IncomeType.load(id)
  }

  @Override
  boolean equals(IncomeType domainInstance) {
    id == domainInstance?.getId()
  }
}