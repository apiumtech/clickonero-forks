package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import com.winbits.domain.orders.OrderDetail
import org.joda.time.LocalDateTime

class SkuOutcome {

  Warehouse warehouse
  OrderDetail orderDetail
  SkuOutcomeStatus status
  OutcomeRequest outcomeRequest
  Guide guide
  WmsStatus wmsStatus

  static hasMany = [histories : InOutSkuHistory, wmsErrors: WmsError]

  LocalDateTime dateCreated
  LocalDateTime lastUpdated

  Integer consecutive
  Integer quantity

  static constraints = {
    outcomeRequest nullable: true
    consecutive min: 1
    guide nullable: true
    wmsStatus nullable: true
  }

  static mapping = {
    consecutive sqlType: 'SMALLINT'
    histories column: 'sku_outcome_id'
  }

  static transients = ['sku', 'outcomeRequestParentIdentifier']

  Sku getSku() {
    orderDetail?.skuProfile?.sku
  }

  Long getOutcomeRequestParentIdentifier() {
    outcomeRequest?.id
  }
}
