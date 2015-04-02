package com.winbits.api.clients.mailing.data

import javax.validation.constraints.NotNull

class ResetPasswordEmailData extends BaseEmailData {

  final String key = 'reset.password'

  @NotNull
  @org.hibernate.validator.constraints.URL
  String resetUrl
}
