package com.winbits.bits.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Thrown when a bits account is not found.
 */
class BitsAccountNotFoundException extends ApiClientException {

  String code = 'BITS001'

  Serializable id

  BitsAccountNotFoundException(Serializable id) {
    this.id = id
    this.data = [id: id]
  }
}
