package com.winbits.api.clients.mailing.exception

import com.winbits.api.clients.mailing.data.BaseEmailData
import org.springframework.validation.Errors

class EmailDataValidationException extends MailingClientException {
  BaseEmailData data
  Errors error

  EmailDataValidationException(BaseEmailData data, Errors error){
    super ("The Email data has validation errors! : ${error}".toString())
    this.data = data
    this.error = error
  }
}
