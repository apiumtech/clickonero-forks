package com.winbits.api.clients.bits

import org.hibernate.validator.constraints.NotEmpty
import org.joda.time.LocalDate

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Representation of a request to perform a bits deposit.
 */
class BitsDepositRequest {

  @NotNull
  String bagName

  @NotNull
  @DecimalMin('0.00')
  BigDecimal amount

  @NotEmpty
  String concept

  @NotNull
  BitsValidity validity = BitsValidity.ABSOLUTE

  @NotNull
  @Min(1L)
  Integer duration = 1

  LocalDate activationDate
}
