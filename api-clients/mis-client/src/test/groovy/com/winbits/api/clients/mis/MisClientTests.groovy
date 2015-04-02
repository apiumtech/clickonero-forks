package com.winbits.api.clients.mis

import com.winbits.api.clients.mis.affiliation.*
import com.winbits.api.clients.mis.orders.ChangeDataCreditCardEvent
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Test cases for mis API.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml', '/mis-ctx.xml'])
class MisClientTests {

  @Autowired
  MisClient misClient

  @Test
  void sendSocialMedia() {
    def request = new MisLoginSocialMedia().with {
      // module = "affiliation"
      //event = "loginSocialMedia"
      date = new Date()
      vertical = "looq"
      userId = 89798
      userName = "correo@email.com"
      socialMediaName = "facebook"
      socialToken = "89798563121324689"

      it
    }
    assert misClient.logMessage(request)
    // assert true
  }

  @Test
  void sendRecoverPassword() {
    def request = new MisRecoverPassword().with
        {
          // module = "affiliation"
          // event = "recoverPassword"
          date = new Date()
          vertical = "looq"
          userId = 89798
          userName = "correo@email.com"
          socialToken = "87898987879798"
          action = MisRecoverPassword.TypeAction.sendEmail
          status = MisRecoverPassword.StatusUser.block
          it
        }
    //assert true
    assert misClient.logMessage(request)
  }

  @Test
  void sendRemoveShippingAddress() {
    def request = new MisRemoveShippingAddress().with
        {
          //module = "affiliation"
          //event = "removeShippingAddress"
          date = new Date()
          vertical = "looq"
          userId = 89798
          userName = "correo@email.com"
          socialToken = "87898987879798"
          shippingAddressId = 5
          it
        }
    assert misClient.logMessage(request)

  }

  @Test
  void sendSubscriptions() {
    def request = new MisSubscriptions().with
        {
          // module = "affiliation"
          // event = "subscriptions"
          date = new Date()
          vertical = "looq"
          userId = 89798
          userName = "correo@email.com"
          socialToken = "87898987879798"
          periodicity = MisSubscriptions.TypePeriodicity.monthly
          subscriptions = ["clickOnero": "valor1", "ke2": "valor2"]
          // unsubscriptions = ["Autos", "zapatos"]
          action = MisSubscriptions.TypeAction.created
          it
        }
    assert misClient.logMessage(request)
    //assert true
  }

  @Test
  void sendLogin() {
    def request = new MisLogin().with
        {
          // module = "affiliation"
          // event = "subscriptions"
          date = new Date()
          vertical = "looq"
          userId = 89798
          userName = "correo@email.com"
          socialToken = "87898987879798"
          it
        }
    assert misClient.logMessage(request)
    //assert true
  }

  @Test
  void sendChangePasswordEvent() {
    def request = new ChangePasswordEvent().with
        {
          userId = 89798
          salesAgentId = 1L
          vertical = 'mylooq'
          userName = 'x@y.com'
          it
        }
    assert misClient.logMessage(request)
  }

  @Test
  void sendChangeCreditCardEvent() {
    def request = new ChangeDataCreditCardEvent().with
        {
          userId = 89798
          salesAgentId = 1L
          vertical = 'mylooq'
          userName = 'x@y.com'
          creditCardId = 23L
          it
        }
    assert misClient.logMessage(request)
  }

  @Test
  void sendChangeDataUserEvent() {
    def request = new ChangeDataUserEvent().with
        {
          userId = 89798
          salesAgentId = 1L
          vertical = 'mylooq'
          userName = 'x@y.com'
          it
        }
    assert misClient.logMessage(request)
  }

  @Test
  void sendChangeShippingAddressEvent() {
    def request = new ChangeShippingAddressEvent().with
        {
          userId = 89798
          salesAgentId = 1L
          vertical = 'mylooq'
          userName = 'x@y.com'
          shippingAddressId = 2342
          it
        }
    assert misClient.logMessage(request)
  }
}
