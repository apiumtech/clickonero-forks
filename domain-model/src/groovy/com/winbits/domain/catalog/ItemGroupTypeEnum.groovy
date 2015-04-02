package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum ItemGroupTypeEnum implements PersistentEnum<ItemGroupType> {
  PRODUCT (1L),
  SERVICE (2L),
  TRAVEL  (3L)

  final Serializable id

  ItemGroupTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  ItemGroupType getDomain() {
    ItemGroupType.load(id)
  }

  @Override
  boolean equals(ItemGroupType domainInstance) {
    id == domainInstance?.getId()
  }
}
