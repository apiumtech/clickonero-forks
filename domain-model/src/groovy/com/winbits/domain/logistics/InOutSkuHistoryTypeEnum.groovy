package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum


public enum InOutSkuHistoryTypeEnum implements PersistentEnum<InOutSkuHistoryType> {

  IN(1L),
  OUT(2L),
  IN_EXTRA(3L),
  OUT_EXTRA(4L)

  final Serializable id

  InOutSkuHistoryTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  InOutSkuHistoryType getDomain() {
    InOutSkuHistoryType.load(id)
  }

  @Override
  boolean equals(InOutSkuHistoryType domainInstance) {
    id == domainInstance?.getId()
  }
}

