package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical

class InviteRegisterService {

  def createLink(User user, Long verticalId) {
    Vertical vertical = Vertical.get(verticalId)
    "${vertical.baseUrl}?a=register&rc=${user.referrerCode}"
  }

}
