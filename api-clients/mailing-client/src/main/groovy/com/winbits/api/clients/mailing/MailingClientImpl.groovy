package com.winbits.api.clients.mailing

import com.google.gson.Gson
import com.winbits.api.clients.ApiClientsValidator
import com.winbits.api.clients.mailing.data.BaseEmailData
import com.winbits.api.clients.mailing.data.ConfirmAccountData
import com.winbits.api.clients.mailing.data.ConfirmOrderData
import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.api.clients.mailing.data.HsbcPaymentTicketEmailData
import com.winbits.api.clients.mailing.data.OxxoPaymentTicketEmailData
import com.winbits.api.clients.mailing.data.ResetPasswordEmailData
import com.winbits.api.clients.mailing.exception.EmailDataValidationException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Client to consume Winbits' com.winbits.api.clients.mailing API. Delegates to dummy implementation by default.
 */
@Component('mailingClient')
class MailingClientImpl implements MailingClient {

  final Gson GSON = new Gson()

  @Autowired
  ApiClientsValidator apiClientsValidator

  @Autowired
  RabbitTemplate rabbitTemplate


  @Override
  void sendRegisterConfirmationEmail(String email, Map data) {
    def registerConfirmation = loadEmailDataProperties(new ConfirmAccountData(email: email), data)
    sendEmail(registerConfirmation)
  }

  @Override
  void sendWelcomeEmail(String email, Map data) {
    def confirmWelcomeMail = loadEmailDataProperties(new ConfirmWelcomeData(email: email), data)
    sendEmail(confirmWelcomeMail)
  }

  @Override
  void sendPasswordResetEmail(String email, Map data) {
    def resetPassword = loadEmailDataProperties(new ResetPasswordEmailData(email: email), data)
    sendEmail(resetPassword)
  }

  @Override
  void sendOxxoPaymentTicketEmail(String email, Map data) {
    def oxxoData = loadEmailDataProperties(new OxxoPaymentTicketEmailData(email: email), data)
    sendEmail(oxxoData)
  }

  @Override
  void sendConfirmationOrderMail(Map data){
    def orderConfirmData = loadEmailDataProperties(new ConfirmOrderData(email: 'remove@thisemail.com'), data)
    sendEmail(orderConfirmData)
   }

   @Override
   void sendHsbcPaymentTicketEmail(String email, Map data) {
     def HsbcPaymentTicketData = loadEmailDataProperties(new HsbcPaymentTicketEmailData(email: email), data)
     sendEmail(HsbcPaymentTicketData)
   }

  @Override
  void sendEmail(BaseEmailData data) {
    def errors = apiClientsValidator.validate(data)
    if (errors.hasErrors()) {
      throw new EmailDataValidationException(data, errors)
    }
    rabbitTemplate.convertAndSend('mailing.tx', data.key, GSON.toJson(data))
  }


  private <T> T loadEmailDataProperties(T emailData, Map properties) {
    emailData.metaClass.setProperties(emailData, properties.findAll { emailData.hasProperty(it.key) })
    emailData
  }
}
