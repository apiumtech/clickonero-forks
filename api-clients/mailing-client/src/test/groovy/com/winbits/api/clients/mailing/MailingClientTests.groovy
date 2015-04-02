package com.winbits.api.clients.mailing

import com.winbits.api.clients.mailing.utils.EmailSender
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Test cases for MailingClientImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
@Ignore
class MailingClientTests {

  private static final String TEST_EMAIL = 'ignacio.arces@clients.sms.com'
  private static Logger log = LoggerFactory.getLogger(MailingClientTests)

  @Autowired
  private MailingClientImpl mailingClient

  @BeforeClass
  static void setupClass() {
    EmailSender.metaClass.static.sendEmail = {String email, String subject, String content ->
      log.info('Sending email to {} [subject: {}, content: {}]', email, subject, content)
    }
  }

  @Test
  void sendRegisterConfirmationEmail() {
    try {
      mailingClient.sendRegisterConfirmationEmail(TEST_EMAIL,
          [confirmationUrl: 'http://api.winbits.com/confirm?hash=1JAS62GE8'])
    } catch (e) {
      Assert.fail(e.message)
    }
  }

  @Test
  void sendWelcomeEmail() {
    try {
      mailingClient.sendWelcomeEmail(TEST_EMAIL, [name: "Steve Jobs"])
    } catch (e) {
      Assert.fail(e.message)
    }
  }

  @Test
  void sendPasswordResetEmail() {
    try {
      mailingClient.sendPasswordResetEmail(TEST_EMAIL, [resetUrl: 'http://api.winbits.com/reset?hash=1JAS62GE8'])
    } catch (e) {
      Assert.fail(e.message)
    }
  }
}
