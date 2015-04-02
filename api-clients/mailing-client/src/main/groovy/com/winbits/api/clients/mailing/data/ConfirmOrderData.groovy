package com.winbits.api.clients.mailing.data

import javax.validation.constraints.NotNull

class ConfirmOrderData extends BaseEmailData {

  final String key = 'confirm.order'

  @NotNull
  String orderId

}
