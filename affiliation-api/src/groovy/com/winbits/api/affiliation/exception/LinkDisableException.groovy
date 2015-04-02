package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: juan
 * Date: 30/04/13
 * Time: 01:45 PM
 * To change this template use File | Settings | File Templates.
 */
class LinkDisableException extends ApiClientException {
  String code = 'AFER022'

    LinkDisableException(String message) {
    super(message)
  }
}
