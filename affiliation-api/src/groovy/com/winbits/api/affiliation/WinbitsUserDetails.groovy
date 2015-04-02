package com.winbits.api.affiliation

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/11/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
class WinbitsUserDetails extends GrailsUser {
  public final String salt

  WinbitsUserDetails(GrailsUser base, String salt) {
    super(base.username, base.password, base.enabled,
        base.accountNonExpired, base.credentialsNonExpired, base.accountNonLocked,
        base.authorities, base.id)
    this.salt = salt;
  }
}
