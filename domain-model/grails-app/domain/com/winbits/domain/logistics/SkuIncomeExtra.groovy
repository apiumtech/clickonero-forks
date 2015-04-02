package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import org.joda.time.LocalDateTime

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 11/26/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
class SkuIncomeExtra {
  BigDecimal cost
  Integer quantity = 0
  LocalDateTime dateCreated
  Boolean available = true
  SkuIncomeExtraType incomeExtraType
  IncomeType incomeType
  InOutSkuHistory history
  static belongsTo = [sku: Sku,warehouse: Warehouse]

  static constraints = {
    quantity min: 0
  }
}
