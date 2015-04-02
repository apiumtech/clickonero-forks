package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum AttributeTypeEnum implements PersistentEnum<AttributeType> {
  TEXT(1L),
  COLOR(2L),
  URL(3L),
  HIDDEN(4L)

  final Serializable id;

  AttributeTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  AttributeType getDomain() {
    AttributeType.load(id)
  }

  @Override
  boolean equals(AttributeType domainInstance) {
    id == domainInstance?.getId()
  }
}