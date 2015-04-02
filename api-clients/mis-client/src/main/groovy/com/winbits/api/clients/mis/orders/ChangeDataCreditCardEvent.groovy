package com.winbits.api.clients.mis.orders

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
@Component(ChangeDataCreditCardEvent.EVENT_NAME)
@Scope('prototype')
class ChangeDataCreditCardEvent extends MisOrderBase {

  static final String EVENT_NAME = 'change-data-credit'

  @NotNull
  Long creditCardId

  ChangeDataCreditCardEvent() {
    event = EVENT_NAME
  }
}
