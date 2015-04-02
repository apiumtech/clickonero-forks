package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import org.joda.time.DateTime

class Brand {
  String name
  Boolean deleted = false
  String logo
  String description

  Vertical vertical

  DateTime dateCreated
  DateTime lastUpdated

  static constraints = {
    name maxSize: 25, blank: false
    logo url: true, nullable: true
    description maxSize: 100, nullable: true
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

}
