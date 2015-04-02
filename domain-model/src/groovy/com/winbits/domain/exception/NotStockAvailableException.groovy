package com.winbits.domain.exception

import com.winbits.exceptions.api.ApiClientException

class NotStockAvailableException extends ApiClientException {
  String code = 'CAER003'

  Long id

  NotStockAvailableException(Long id) {
    this.id = id
    this.data = [id:id]
  }
}
