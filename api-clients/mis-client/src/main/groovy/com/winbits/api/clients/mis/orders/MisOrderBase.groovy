package com.winbits.api.clients.mis.orders

import com.winbits.api.clients.mis.MisBase

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/30/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class MisOrderBase extends MisBase {

//  String socialToken

  Long salesAgentId

  MisOrderBase() {
    this.module = "order"
  }


}
