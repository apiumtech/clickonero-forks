package com.winbits.api.clients.bits

import org.joda.time.LocalDate
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Test cases for BitsClient.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class BitsClientTests {

  private static Logger log = LoggerFactory.getLogger(BitsClientTests)

  @Autowired
  private BitsClient bitsClient
  def accountId = 1590L

  @Test
  void createBitsAccount() {
    def accountData = bitsClient.createBitsAccount(BitsAccountType.USER, BitsAccountStatus.ACTIVE)
    log.info('Accounts data: {}', accountData)
    assert accountData
  }

  @Test
  void getBitsBalance() {
    assert bitsClient.getBitsBalance(accountId) > 0
  }

  @Test
  void getBitsAccountHistory() {
    def now = LocalDate.now()
    def params = [from: now, to: now.plusDays(7)]
    def respuesta = bitsClient.getBitsAccountHistory(accountId, params)
    assert respuesta
    assert respuesta.response.balance > 0
    assert respuesta.response.transactions.size() > 0
  }

  @Test
  void depositBits() {
    def request = new BitsDepositRequest().with {
      bagName = "CHASHBACK"
      amount = 10.00
      concept = 'Test deposit'
      it
    }
    assert bitsClient.depositBits(request)
  }

//TODO implementar datos validos para prueba con SP
  @Test
  void withdrawBits() {
    def response =  bitsClient.withdrawBits(accountId, 50.0, "LOL", "CASHBACK_REFUNDED")
    assert response
  }

  @Test
  void transferBits() {
    def request = new BitsTransferRequest().with {
      bagName = "CASHBACK"
      targetAccount = accountId
      amount = 10.00
      concept = "Cashback de la orden XXX"
      validity = BitsValidity.ABSOLUTE
      duration = 30
      activationDate = LocalDate.now()
      it
    }
    assert bitsClient.transferBits(request)
  }
}
