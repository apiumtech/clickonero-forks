package com.winbits.domain.logistics

import com.winbits.domain.catalog.Provider
import com.winbits.domain.catalog.Sku
import org.joda.time.DateTime

class Bom {

  Provider provider
  Sku sku
  IncomeType incomeType

  Integer quantity
  BigDecimal cost

  DateTime dateCreated
  DateTime lastUpdated

  static constraints = {
      quantity min: 1
      cost min: 0.0
  }
}

