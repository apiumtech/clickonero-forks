package com.winbits.api.clients.mailing.data

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty

abstract class BaseEmailData {
  @NotEmpty
  @Email
  String email

  abstract String getKey();
}
