package com.winbits.domain.catalog

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

class ItemType {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

}
