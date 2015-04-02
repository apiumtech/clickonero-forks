package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiException


class MobileAlreadyRegisterException extends ApiException {

  String code = 'AFER033'
  String mobile

  MobileAlreadyRegisterException(String mobile) {
    this.mobile = mobile
  }
}
