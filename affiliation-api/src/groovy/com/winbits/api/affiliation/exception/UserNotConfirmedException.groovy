package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 5/2/13
 * Time: 2:55 AM
 * To change this template use File | Settings | File Templates.
 */
class UserNotConfirmedException extends ApiClientException {

  String code = 'AFER004'

  UserNotConfirmedException(User user) {
    this.data = [resendConfirmUrl: user.resendConfirmUrl()]
  }
}
