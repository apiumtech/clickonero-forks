package com.winbits.domain.logistics

import com.winbits.domain.orders.ShippingOrder
import org.joda.time.LocalDateTime

class OutcomeRequest extends OutcomeRequestParent {

  OutcomeRequestStatus status
  ShippingOrder shippingOrder

  static hasMany = [skuOutcomes: SkuOutcome]

  static constraints = {
  }

  def beforeValidate() {
    if (!type) {
      type = OutcomeTypeEnum.VENTA_CLIENTE.domain
    }
  }
}
