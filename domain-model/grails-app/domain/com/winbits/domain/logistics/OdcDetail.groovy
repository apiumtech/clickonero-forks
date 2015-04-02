package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import com.winbits.domain.catalog.SkuIncome

class OdcDetail {

  Sku sku
  Odc odc
  OdcDetailStatus status
  Integer quantityRequired
  Integer quantityReceived = 0
  BigDecimal cost
  BigDecimal totalCostRequired
  BigDecimal totalCostReceived

  static hasMany = [skuIncomes:SkuIncome]
  static mapping = {
    skuIncomes column: "odc_detail_id"
  }
  static constraints = {
    cost min: 0.0
    totalCostReceived nullable: true
  }
}
