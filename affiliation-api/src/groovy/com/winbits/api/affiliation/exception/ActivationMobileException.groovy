package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

class ActivationMobileException extends ApiClientException {

  String code = 'AFER034'
  String mobile

  ActivationMobileException(String mobile) {
    this.mobile = mobile
  }
}
