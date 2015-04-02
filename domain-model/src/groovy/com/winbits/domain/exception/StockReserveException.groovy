package com.winbits.domain.exception

import com.winbits.exceptions.api.ApiClientException

class StockReserveException extends ApiClientException {
  String code = 'CAER002'

  Long id
  Integer quantityReserved

  StockReserveException(Long id, Integer quantityReserved) {
    this.id = id
    this.quantityReserved = quantityReserved
    this.data = [id:id, quantityReserved: quantityReserved]
  }
}
