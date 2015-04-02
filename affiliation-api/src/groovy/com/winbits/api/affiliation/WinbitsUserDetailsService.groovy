package com.winbits.api.affiliation

import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/11/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
class WinbitsUserDetailsService extends GormUserDetailsService {
  protected UserDetails createUserDetails(user, Collection authorities) {
    assert grailsApplication
    new WinbitsUserDetails((GrailsUser) super.createUserDetails(user, authorities),
        user.salt
    )
  }
}
