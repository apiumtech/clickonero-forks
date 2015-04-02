package com.winbits.bits.exception

import com.winbits.exceptions.api.ApiClientException

class InvalidBitsOperationException extends ApiClientException {
  String code = 'BITS002'

  Serializable id

  InvalidBitsOperationException(Serializable id) {
    this.id = id
  }
}
