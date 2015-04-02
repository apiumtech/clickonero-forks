package com.winbits.api.clients.mailing

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.winbits.api.clients.mailing.data.ConfirmAccountData
import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.api.clients.mailing.data.HsbcPaymentTicketEmailData
import com.winbits.api.clients.mailing.data.OxxoPaymentTicketEmailData
import com.winbits.api.clients.mailing.data.ResetPasswordEmailData
import com.winbits.api.clients.mailing.exception.EmailDataValidationException
import org.junit.Ignore
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

import java.lang.reflect.Type
@Ignore
@ContextConfiguration(locations = ['/app-ctx.xml', '/mailing-ctx.xml'])
class MailingClientIntegrationSpec extends Specification {

  @Shared Gson gson
  @Shared Type type

  @Autowired
  MailingClient mailingClient

  @Autowired
  RabbitTemplate rabbitTemplate

  def setupSpec() {
    gson = new Gson()
    type = new TypeToken<Map<String, String>>(){}.getType();
  }

  def "TEST OXXO"() {
    given: 'a valid email data'
    def emailData = new  OxxoPaymentTicketEmailData(email: 'x@y.com', downloadUrl: 'http://www.example.com')

    when:
    mailingClient.sendEmail(emailData)

    then:
    def message = rabbitTemplate.receive('sendOxxoPaymentTicketMail')
    message.messageProperties.receivedExchange == 'mailing.tx'
    message.messageProperties.receivedRoutingKey == emailData.key
    def body = new String(message.body)
    def json = gson.fromJson(body, type)
    json.email == 'x@y.com'
    json.downloadUrl== 'http://www.example.com'
    json.key == emailData.key
  }

    def "TEST HSBC"() {
        given: 'a valid email data'
        def emailData = new  HsbcPaymentTicketEmailData(email: 'x@y.com', downloadUrl: 'http://www.example.com')

        when:
        mailingClient.sendEmail(emailData)

        then:
        def message = rabbitTemplate.receive('sendHsbcPaymentTicketMail')
        message.messageProperties.receivedExchange == 'mailing.tx'
        message.messageProperties.receivedRoutingKey == emailData.key
        def body = new String(message.body)
        def json = gson.fromJson(body, type)
        json.email == 'x@y.com'
        json.downloadUrl== 'http://www.example.com'
        json.key == emailData.key
    }

  def "TEST CONFIRM WELCOME"() {
    given: 'a valid email data'
    def emailData = new ConfirmWelcomeData(email: 'algo@hotmail.com',
                                           idUserweb:'algo78Welcome',
                                           urlBanner: 'http://www.banner.com',
                                           name: 'Gerardo',
                                           lastname: 'Martinez',
                                           genre: 'Masculino',
                                           birthdate: '1991-01-17',
                                           postcode:'55080',
                                           locality: 'Estado de Mexico',
                                           phone: '51166854',
                                           banner: 'Compras')

    when:
    mailingClient.sendEmail(emailData)

    then:
    def message = rabbitTemplate.receive('sendWelcomeMail')
    message.messageProperties.receivedExchange == 'mailing.tx'
    message.messageProperties.receivedRoutingKey == emailData.key
    def body = new String(message.body)
    def json = gson.fromJson(body, type)
    json.email == 'algo@hotmail.com'
    json.idUserweb == 'algo78Welcome'
    json.urlBanner ==  'http://www.banner.com'
    json.name == 'Gerardo'
    json.lastname == 'Martinez'
    json.genre == 'Masculino'
    json.birthdate =='1991-01-17'
    json.postcode == '55080'
    json.locality == 'Estado de Mexico'
    json.phone == '51166854'
    json.banner == 'Compras'
    json.key == emailData.key
    json.vertical == 1
  }


  def "TEST CONFIRM ACCOUNT"() {
    given: 'a valid email data'
    def emailData = new ConfirmAccountData(email: 'algo@hotmail.com',
                                           IdUserweb: 'algo78confirm',
                                           firm: '123456789',
                                           UrlConfirm: 'http://www.example.com')

    when:
    mailingClient.sendEmail(emailData)


    then:
    def message = rabbitTemplate.receive('sendConfirmAccountMail')
    message.messageProperties.receivedExchange == 'mailing.tx'
    message.messageProperties.receivedRoutingKey == emailData.key
    def body = new String(message.body)
    def json = gson.fromJson(body, type)
    json.key == emailData.key
    json.email == 'algo@hotmail.com'
    json.IdUserweb == 'algo78confirm'
    json.firm == '123456789'
    json.UrlConfirm == 'http://www.example.com'

  }



  def "TEST RESET PASSWORD"() {
    given: 'a valid email data'
    def emailData = new ResetPasswordEmailData(email: 'x@y.com', resetUrl: 'http://www.example.com')

    when:
    mailingClient.sendEmail(emailData)

    then:
    def message = rabbitTemplate.receive('sendResetMail')
    message.messageProperties.receivedExchange == 'mailing.tx'
    message.messageProperties.receivedRoutingKey == emailData.key
    def body = new String(message.body)
    def json = gson.fromJson(body, type)
    json.email == 'x@y.com'
    json.resetUrl == 'http://www.example.com'
    json.key == emailData.key
  }

  def "sendEmail must throw exception if email data has validation errors"() {
    given: 'an invallid email data'
    def emailData = new ResetPasswordEmailData(email: 'xxx')

    when:
    mailingClient.sendEmail(emailData)

    then:
    thrown(EmailDataValidationException)
  }
}
