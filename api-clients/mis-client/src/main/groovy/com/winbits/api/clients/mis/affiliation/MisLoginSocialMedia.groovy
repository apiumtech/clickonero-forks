package com.winbits.api.clients.mis.affiliation

import org.hibernate.validator.constraints.NotEmpty
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/24/13
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(MisLoginSocialMedia.EVENT_NAME)
@Scope('prototype')
class MisLoginSocialMedia extends MisAffiliationBase {

  static final String EVENT_NAME = "mis-login-social-media"

  MisLoginSocialMedia() {
    this.event = EVENT_NAME
  }

  @NotEmpty
  String socialMediaName


}
