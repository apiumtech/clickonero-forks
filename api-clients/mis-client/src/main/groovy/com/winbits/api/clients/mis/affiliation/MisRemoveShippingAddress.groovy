package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/24/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(MisRemoveShippingAddress.EVENT_NAME)
@Scope('prototype')
class MisRemoveShippingAddress extends MisAffiliationBase {

  static final String EVENT_NAME = "mis-remove-shipping-address"

  MisRemoveShippingAddress() {
    this.event = EVENT_NAME
  }

  Long shippingAddressId

}
