package com.winbits.bits

import com.grailsrocks.functionaltest.BrowserTestCase
import grails.converters.JSON
import org.joda.time.DateTime

/**
 * Created by winbits on 9/1/14.
 */
class WithdrawRollbackFunctionalTests extends BrowserTestCase {

  void setUp() {
    super.setUp()
    client.setAuth("basic", "admin", "admin")
  }

  Long createAccount() {
    post('/accounts') {
      headers['Content-Type'] = 'application/json'
    }
    assertStatus 201

    def json = JSON.parse(response.getContentAsString())
    def accountId = json.response.id
    accountId
  }

  long deposit(long accountId, double amount) {
    def parametros = [amount: amount, concept: "Recomendación de amigos", validity: "ABSOLUTE", duration: "5"]
    post("/accounts/${accountId}/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }

    assertStatus 200
    def result = JSON.parse(response.getContentAsString())
    assert result.response.depositId
    result.response.depositId
  }

  long withdrawal(long accountId, double amount){
    post("/accounts/${accountId}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
          "amount": ${amount},
          "concept": "Compra de la order ${accountId}-${new DateTime().millis}"
        }"""
      }
    }
    assertStatus 200
    def result = JSON.parse(response.getContentAsString())
    result.response.transactionId
  }

  void withdrawRollback(long accountId, long transactionId) {
    def parametros = [transactionId: transactionId, concept: 'Me afecto, me dio chorro!!']
    post("/accounts/${accountId}/rollback") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }
    assertStatus 200
    def respuesta = JSON.parse response.contentAsString
    def jsonResponse = respuesta.response
    assert jsonResponse
  }

  double findBalanceByDepositId(long depositId) {
    Deposit.get(depositId)?.amount
  }

  //deposit balance is enough to cover the amount of the charge.
  void rollbackOrder() {
    def accountId = createAccount()
    def depositId = deposit(accountId, 100)
    def transactionId = withdrawal(accountId, 80)
    withdrawRollback(accountId, transactionId)
    def balance = findBalanceByDepositId(depositId)
    def account = Account.get(accountId)
    assert balance == 100
    assert balance == account.balance
  }

  //try to rollback a deposit two or more times
  void tryMultipleRollback(){
    def accountId = createAccount()
    def depositId = deposit(accountId, 100)
    def transactionId = withdrawal(accountId, 80)
    withdrawRollback(accountId, transactionId)
    withdrawRollback(accountId, transactionId)
    withdrawRollback(accountId, transactionId)
    def balance = findBalanceByDepositId(depositId)
    def account = Account.get(accountId)

    assert balance == 100
    assert balance == account.balance
  }

  //try to rollback many deposits
  void moreThanOneDeposits(){
    def accountId = createAccount()
    def depositId1 = deposit(accountId, 100)
    def depositId2 = deposit(accountId, 50)
    def depositId3 = deposit(accountId, 20)

    def transactionId = withdrawal(accountId, 120)
    withdrawRollback(accountId, transactionId)
    withdrawRollback(accountId, transactionId)
    def balance1 = findBalanceByDepositId(depositId1)
    def balance2 = findBalanceByDepositId(depositId2)
    def balance3 = findBalanceByDepositId(depositId3)

    def account = Account.get(accountId)

    assert (balance1 + balance2 + balance3) == 170
    assert account.balance == 170

  }

  void testMain(){
    //rollbackOrder()
    //tryMultipleRollback()
    //moreThanOneDeposits()
  }


}
