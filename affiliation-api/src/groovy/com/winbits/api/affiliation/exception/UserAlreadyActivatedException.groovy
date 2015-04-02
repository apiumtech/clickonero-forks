package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiException

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 5/2/13
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
class UserAlreadyActivatedException extends ApiException {

  String code = 'AFER005'
  User user

  UserAlreadyActivatedException(User user) {
    this.data = [email: user.email]
    this.user = user
  }
}
