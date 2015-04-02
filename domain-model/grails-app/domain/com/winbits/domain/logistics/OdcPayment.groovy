package com.winbits.domain.logistics

/**
 * Suceptible a cambios
 */
class OdcPayment {

  Odc odc
  BigDecimal amount

  static constraints = {
    amount min: 0.0
  }
}
