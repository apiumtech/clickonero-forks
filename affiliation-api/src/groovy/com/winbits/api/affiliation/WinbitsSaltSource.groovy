package com.winbits.api.affiliation

import org.springframework.security.authentication.dao.ReflectionSaltSource
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/11/13
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
class WinbitsSaltSource extends ReflectionSaltSource {

  Object getSalt(UserDetails user) {
    user[userPropertyToUse]
  }
}
