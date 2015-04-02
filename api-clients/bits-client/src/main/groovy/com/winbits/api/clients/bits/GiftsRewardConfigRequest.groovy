package com.winbits.api.clients.bits

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

class GiftsRewardConfigRequest {

  @NotNull
  Long accountId

  @NotNull
  String awarder

  @NotNull
  @DecimalMin('0.00')
  BigDecimal amount

  @NotNull
  String concept

  @NotNull
  String justification

  @NotNull
  Long bitsCategoryId

  Long cknOrderId
}
