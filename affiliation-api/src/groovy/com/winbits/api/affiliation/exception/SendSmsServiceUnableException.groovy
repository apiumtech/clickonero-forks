package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiException

class SendSmsServiceUnableException extends ApiException {

  String code = 'AFER037'
  String mobile

    SendSmsServiceUnableException(String mobile) {
    this.mobile = mobile
  }
}
