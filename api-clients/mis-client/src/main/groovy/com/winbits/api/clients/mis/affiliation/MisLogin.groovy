package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 5/2/13
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(MisLogin.EVENT_NAME)
@Scope('prototype')
class MisLogin  extends MisAffiliationBase{

  static final String EVENT_NAME ='mis-login'
    MisLogin()
    {
        this.event= EVENT_NAME
    }

}
