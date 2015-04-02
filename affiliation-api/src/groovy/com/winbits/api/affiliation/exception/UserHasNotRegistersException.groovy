package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

class UserHasNotRegistersException extends ApiClientException {

  String code = 'AFER035'
  String mobile

    UserHasNotRegistersException(String mobile) {
    this.mobile = mobile
  }
}
