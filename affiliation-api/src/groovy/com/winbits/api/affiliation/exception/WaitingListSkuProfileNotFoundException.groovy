package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException
import groovy.transform.InheritConstructors

@InheritConstructors
class WaitingListSkuProfileNotFoundException extends ApiClientException {
  String code = 'AFER010'
}
