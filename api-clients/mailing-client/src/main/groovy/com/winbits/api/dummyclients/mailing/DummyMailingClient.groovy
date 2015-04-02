package com.winbits.api.dummyclients.mailing

import com.winbits.api.clients.mailing.MailingClient
import com.winbits.api.clients.mailing.data.BaseEmailData
import com.winbits.api.clients.mailing.utils.EmailSender
import org.codehaus.groovy.runtime.StackTraceUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component('mailingClient')
class DummyMailingClient implements MailingClient {
  private static Logger log = LoggerFactory.getLogger(DummyMailingClient)

  /**
   * The data map must have an entry called <b>confirmationUrl</b> for the message to gets sent.
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  @Override
  void sendRegisterConfirmationEmail(String email, Map data) {
    log.info('Sending register confirmation email to {} [data: {}]', email, data)
    Assert.notNull(email, 'Please, specify a valid email address!')
    Assert.notEmpty(data, 'Please, specify a non-empty data map!')
    Assert.notNull(data.confirmationUrl, 'Please, specify "confirmationUrl" inside the data map!')
    def subject = 'Bienvenido a Winbits!'
    def content = """
        <p>
          Para activar tu cuenta da click <a href="${data.confirmationUrl}">AQUI</a>
        </p>
    """
    safeSendEmail(email, subject, content)
  }

  @Override
  void sendConfirmationOrderMail(Map data) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
/**
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  @Override
  void sendWelcomeEmail(String email, Map data) {
    log.info('Sending welcome email to {} [data: {}]', email, data)
    Assert.notNull(email, 'Please, specify a valid email address!')
    Assert.notEmpty(data, 'Please, specify a non-empty data map!')
    def subject = 'Bienvenido a Winbits!'
    def content = '<p>Gracias x registrarte en Winbits <a href="www.winbits.com">www.winbits.com</a></p>'
    safeSendEmail(email, subject, content)
  }

  /**
   * The data map must have an entry called <b>resetUrl</b> for the message to gets sent.
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  @Override
  void sendPasswordResetEmail(String email, Map data) {
    log.info('Sending password reset email to {} [data: {}]', email, data)
    Assert.notNull(email, 'Please, specify a valid email address!')
    Assert.notEmpty(data, 'Please, specify a non-empty data map!')
    Assert.notNull(data.resetUrl, 'Please, specify "resetUrl" inside the data map!')
    def subject = 'Recuperación de contraseña!'
    def content = """
        <p>
          Para resetear tu contraseña da click <a href="${data.resetUrl}">AQUI</a>
        </p>
    """
    safeSendEmail(email, subject, content)
  }

  /**
   * @param email Email address to send message to.
   * @param data Email message notification model.
   */
  @Override
  void sendOxxoPaymentTicketEmail(String email, Map data) {
    log.info('Sending oxxo payment ticket to email {} with [data: {}]', email, data)
    Assert.notNull(email, 'Please, specify a valid email address!')
    Assert.notEmpty(data, 'Please, specify a non-empty data map!')
    def subject = 'Notificación de compra!'
    def content = "<p>Descarga el compobante <a href='${data.downloadUrl}'>aqui</a> </p> <p>Gracias por comprar en Winbits <a href='www.winbits.com'>www.winbits.com></a></p>"
    safeSendEmail(email, subject, content)
  }
  
  /**
   * @param email Email address to send message to.
   * @param data Email message notification model.
   */
  @Override
  void sendHsbcPaymentTicketEmail(String email, Map data) {
    log.info('Sending hsbc payment ticket to email {} with [data: {}]', email, data)
    Assert.notNull(email, 'Please, specify a valid email address!')
    Assert.notEmpty(data, 'Please, specify a non-empty data map!')
    def subject = 'Notificación de compra!'
    def content = "<p>Descarga el compobante <a href='${data.downloadUrl}'>aqui</a> </p> <p>Gracias por comprar en Winbits <a href='www.winbits.com'>www.winbits.com></a></p>"
    safeSendEmail(email, subject, content)
  }
  
  private void safeSendEmail(String email, String subject, String content) {
    try {
      EmailSender.sendEmail(email, subject, content)
    } catch(e) {
      log.error('Error while sending email!', StackTraceUtils.sanitizeRootCause(e))
    }
  }

  @Override
  void sendEmail(BaseEmailData data){}
}
