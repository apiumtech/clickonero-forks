package com.winbits.api.clients.bits

import org.hibernate.validator.constraints.NotEmpty
import org.joda.time.DateTime
import org.joda.time.LocalDate

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 4/16/13
 * Time: 4:49 AM
 * To change this template use File | Settings | File Templates.
 */
class BitsTransferRequest {

  @NotNull
  String bagName

  @NotNull
  Long targetAccount

  @NotNull
  @DecimalMin('0.00')
  BigDecimal amount

  @NotEmpty
  String concept

  @NotNull
  BitsValidity validity = BitsValidity.ABSOLUTE

  @NotNull
  Integer duration = 0

  LocalDate activationDate
}
