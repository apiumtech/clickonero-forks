package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiClientException

class UserHasMobileActiveException extends ApiClientException {

  String code = 'AFER036'
  User user

    UserHasMobileActiveException(User user) {
    this.user = user
  }
}
