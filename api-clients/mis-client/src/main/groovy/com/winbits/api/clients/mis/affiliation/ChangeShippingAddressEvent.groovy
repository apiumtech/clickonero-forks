package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 28/02/14
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
@Component(ChangeShippingAddressEvent.EVENT_NAME)
@Scope('prototype')
class ChangeShippingAddressEvent extends MisAffiliationBase {

  static final String EVENT_NAME ='change-shipping-address'

  @NotNull
  Long shippingAddressId

  ChangeShippingAddressEvent() {
    event = EVENT_NAME
  }
}
