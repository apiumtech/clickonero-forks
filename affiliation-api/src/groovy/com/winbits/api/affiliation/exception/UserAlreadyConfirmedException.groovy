package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/29/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
class UserAlreadyConfirmedException extends ApiClientException {
  String code = 'AFER002'

  UserAlreadyConfirmedException(String email) {
    this.data = [email: email]
  }
}
