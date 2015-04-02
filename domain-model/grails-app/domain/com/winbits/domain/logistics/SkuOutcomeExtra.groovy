package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import org.joda.time.LocalDateTime

class SkuOutcomeExtra {
  Warehouse warehouse
  SkuOutcomeStatus status
  OutcomeRequestExtra outcomeRequestExtra
  Guide guide
  WmsStatus wmsStatus

  static belongsTo = [sku: Sku]
  static hasMany = [histories : InOutSkuHistory, wmsErrors: WmsError]

  LocalDateTime dateCreated
  LocalDateTime lastUpdated

  Integer quantity
  BigDecimal unitCost
  BigDecimal totalCost
  OdcDetail odcDetail

  static constraints = {
    wmsStatus nullable: true
    guide nullable: true
    odcDetail nullable: true
  }

  static mapping = {
    histories column: 'sku_outcome_extra_id'
  }

  def isCancelable() {
    this.status.id == SkuOutcomeStatusEnum.IN_WAREHOUSE.id &&
       !this.wmsStatus &&
        outcomeRequestExtra.wmsStatus.id == WmsStatusEnum.SUCCESS.id
  }

  Long getOutcomeRequestParentIdentifier() {
    outcomeRequestExtra?.id
  }
}
