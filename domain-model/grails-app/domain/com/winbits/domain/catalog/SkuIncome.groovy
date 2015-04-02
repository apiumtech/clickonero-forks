package com.winbits.domain.catalog

import com.winbits.domain.logistics.InOutSkuHistory
import com.winbits.domain.logistics.IncomeType
import com.winbits.domain.logistics.OdcDetail
import org.joda.time.LocalDateTime

class SkuIncome {
  BigDecimal cost
  Integer quantity = 0
  LocalDateTime dateCreated
  Boolean available = true
  IncomeType incomeType
  InOutSkuHistory history
  static belongsTo = [odcDetail: OdcDetail]

  static constraints = {
    quantity min: 0
  }

}
