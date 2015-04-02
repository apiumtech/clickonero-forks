package com.winbits.api.clients.mis.affiliation

import com.winbits.api.clients.mis.MisBase

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/30/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class MisAffiliationBase extends MisBase {

  String socialToken

  Long salesAgentId

  MisAffiliationBase() {
    this.module = "affiliation"
  }


}
