package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException
import groovy.transform.InheritConstructors
import com.winbits.domain.affiliation.User

@InheritConstructors
class BitsApiNotAvailableException extends ApiClientException {
  String code = 'AFER027'
  User user

  BitsApiNotAvailableException(User user) {
    this.user = user
  }
}
