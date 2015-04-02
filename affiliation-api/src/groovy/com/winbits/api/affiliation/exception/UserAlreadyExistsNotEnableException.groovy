package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Exception for User that already is registered and not enabled.
 */
class UserAlreadyExistsNotEnableException extends ApiClientException {

  String code = 'AFER026'

  UserAlreadyExistsNotEnableException(Map data) {
    this.data = data
  }

}
