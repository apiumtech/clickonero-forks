package com.winbits.domain.exception

import com.winbits.exceptions.api.ApiClientException
import groovy.transform.InheritConstructors

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 4/30/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
@InheritConstructors
class VerticalNotFoundException extends ApiClientException {
  String code = 'CAER001'

  VerticalNotFoundException(long id) {
    this.data = [verticalId: id]
  }
}
