package com.winbits.api.clients.mailing

import com.winbits.api.clients.ApiClientsValidator
import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.api.clients.mailing.data.ResetPasswordEmailData
import com.winbits.api.clients.mailing.exception.EmailDataValidationException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.validation.Errors
import spock.lang.Specification

class MailingClientImplSpec extends Specification {

  MailingClientImpl mailingClient

  def setup() {
    mailingClient = new MailingClientImpl()
  }

  def "sendEmail with valid email data send message to rabbit mailing.tx exchange"() {
    given: 'a vallid email data'
    def emailData = new ResetPasswordEmailData()

    and: 'mock collaborators'
    def errors = Mock(Errors)
    def apiClientsValidatorMock = Mock(ApiClientsValidator)
    mailingClient.apiClientsValidator = apiClientsValidatorMock
    def rabbitTemplateMock = Mock(RabbitTemplate)
    mailingClient.rabbitTemplate = rabbitTemplateMock

    when:
    mailingClient.sendEmail(emailData)

    then:
    1 * apiClientsValidatorMock.validate(emailData) >> errors
    1 * errors.hasErrors() >> false
    1 * rabbitTemplateMock.convertAndSend('mailing.tx', 'reset.password', _)
  }

  def "sendEmail must throw exception if email data has validation errors"() {
    given: 'an invallid email data'
    def emailData = new ResetPasswordEmailData()

    and: 'mock errors'
    def errors = Mock(Errors)
    def apiClientsValidatorMock = Mock(ApiClientsValidator)
    mailingClient.apiClientsValidator = apiClientsValidatorMock

    when:
    mailingClient.sendEmail(emailData)

    then:
    thrown(EmailDataValidationException)
    1 * apiClientsValidatorMock.validate(_) >> errors
    1 * errors.hasErrors() >> true
  }

  def 'sendWelcomeEmail works as expected'() {
    given: 'a vallid email data'
    def emailData = [idUserweb: 1, urlBanner: 'http://www.clients.sms.com', name:'Ignacio', lastname:'Arces', genre:'M',
        birthdate: '1986-10-18', postcode:'11400', phone:'53259000', banner:'XXXX']

    and: 'mock collaborators'
    def errors = Mock(Errors)
    def apiClientsValidatorMock = Mock(ApiClientsValidator)
    mailingClient.apiClientsValidator = apiClientsValidatorMock
    def rabbitTemplateMock = Mock(RabbitTemplate)
    mailingClient.rabbitTemplate = rabbitTemplateMock

    when:
    mailingClient.sendWelcomeEmail('ignacio.arces@clients.sms.com', emailData)

    then:
    1 * apiClientsValidatorMock.validate(_) >> errors
    1 * errors.hasErrors() >> false
    1 * rabbitTemplateMock.convertAndSend('mailing.tx', 'confirm.welcome', _)
  }
}
