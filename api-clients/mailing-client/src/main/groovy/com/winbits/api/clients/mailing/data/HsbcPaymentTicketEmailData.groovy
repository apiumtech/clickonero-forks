package com.winbits.api.clients.mailing.data
import javax.validation.constraints.NotNull

class HsbcPaymentTicketEmailData extends BaseEmailData {

    final String key = 'hsbc.ticket.payment'

    @NotNull
    @org.hibernate.validator.constraints.URL
    String downloadUrl
}
