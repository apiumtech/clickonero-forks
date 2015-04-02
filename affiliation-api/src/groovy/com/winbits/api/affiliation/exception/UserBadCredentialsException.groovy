package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 5/2/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
class UserBadCredentialsException extends ApiClientException {

  String code = 'AFER006'
  User user

  UserBadCredentialsException(User user) {
    this.data = [email: user?.email]
    this.user = user
  }
}
