package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import org.joda.time.LocalDateTime

class InOutSkuHistory {

  Sku sku
  String description
  Integer quantity
  Integer balance
  LocalDateTime dateCreated
  InOutSkuHistoryType type

  static constraints = {
    description nullable: true
  }
  static mapping = {
    version false
  }
}
