package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum ItemStatusEnum implements PersistentEnum<ItemStatus> {
  ACTIVE(1L),
  INACTIVE(2L),
  SOLD_OUT(3L)

  final Serializable id

  ItemStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  ItemStatus getDomain() {
    ItemStatus.load(id)
  }

  @Override
  boolean equals(ItemStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
