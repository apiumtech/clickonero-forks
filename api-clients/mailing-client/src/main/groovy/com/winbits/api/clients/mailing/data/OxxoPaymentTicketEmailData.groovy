package com.winbits.api.clients.mailing.data

import javax.validation.constraints.NotNull

class OxxoPaymentTicketEmailData extends BaseEmailData {

  final String key = 'oxxo.payment'

  @NotNull
  @org.hibernate.validator.constraints.URL
  String downloadUrl
}
