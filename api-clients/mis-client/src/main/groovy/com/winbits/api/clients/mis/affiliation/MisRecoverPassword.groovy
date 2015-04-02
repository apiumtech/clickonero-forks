package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/24/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(MisRecoverPassword.EVENT_NAME)
@Scope('prototype')
class MisRecoverPassword extends MisAffiliationBase {
  static final String EVENT_NAME = "mis-recover-password"

    MisRecoverPassword() {
    this.event = EVENT_NAME
  }


  enum TypeAction
  {
    sendEmail,
    resetPassword,
  }

  enum StatusUser
  {
    normal,
    block,
    disabled,
  }

  @NotNull
  TypeAction action

  @NotNull
  StatusUser status
}
