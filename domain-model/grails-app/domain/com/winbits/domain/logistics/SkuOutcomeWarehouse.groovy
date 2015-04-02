package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import org.joda.time.LocalDateTime

class SkuOutcomeWarehouse {

  Warehouse warehouse
  OutcomeType type
  Integer quantity
  String receiver
  LocalDateTime dateCreated

  static belongsTo = [sku: Sku]
  static hasMany = [histories : InOutSkuHistory]

  static constraints = {
  }
}
