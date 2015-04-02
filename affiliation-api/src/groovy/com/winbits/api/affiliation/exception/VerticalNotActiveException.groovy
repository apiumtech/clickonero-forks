package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

class VerticalNotActiveException extends ApiClientException {
  final String domain
  final String code = 'AFER028'

  VerticalNotActiveException(String domain) {
    this.domain = domain
    this.args = [domain]
  }

  @Override
  String getMessage() {
    "An active vertical with domain '${domain}' was not found!"
  }
}
