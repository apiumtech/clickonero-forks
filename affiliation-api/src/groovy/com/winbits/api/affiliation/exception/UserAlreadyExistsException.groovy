package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Exception for User that already is registered.
 */
class UserAlreadyExistsException extends ApiClientException {

  String code = 'AFER001'

  UserAlreadyExistsException(Map data) {
    this.data = data
  }

  UserAlreadyExistsException(String message) {
    super(message)
  }

  UserAlreadyExistsException(String message, Throwable cause) {
    super(message, cause)
  }

  UserAlreadyExistsException(Throwable cause) {
    super(cause)
  }

  UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace)
  }
}
