package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum ImageTypeEnum implements PersistentEnum<ImageType> {
  LARGE(1L),
  MEDIUM(2L),
  TINY(3L),
  THUMB(4L)

  final Serializable id;

  ImageTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  ImageType getDomain() {
    ImageType.load(id)
  }

  @Override
  boolean equals(ImageType domainInstance) {
    id == domainInstance?.getId()
  }
}