package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 28/02/14
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
@Component(ChangePasswordEvent.EVENT_NAME)
@Scope('prototype')
class ChangePasswordEvent extends MisAffiliationBase {

  static final String EVENT_NAME = 'change-password'

  ChangePasswordEvent() {
    event = EVENT_NAME
  }
}
