package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class ImageType implements Enumable<ImageTypeEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  ImageTypeEnum getEnum() {
    def theId = getId()
    if (theId) {
      ImageTypeEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(ImageTypeEnum enumConstant) {
    getId() == enumConstant.id
  }
}
