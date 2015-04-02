package com.winbits.bits

import com.grailsrocks.functionaltest.BrowserTestCase
import grails.converters.JSON
import org.joda.time.DateTime
import org.joda.time.LocalDate

class AccountsControllerFunctionalTests extends BrowserTestCase {

  void testCreateAccountWithNoParametersUsqeDefaults() {
    client.setAuth("basic", "admin", "admin")

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
    }
    assertStatus 201

    def json = JSON.parse(response.getContentAsString())
    def accountId = json.response.id
    assertNewAccount(accountId)
  }

  private assertNewAccount(Serializable accountId) {
    assert accountId
    def account = getAccount(accountId)
    assert account.status == AccountStatus.ACTIVE
    assert account.type == AccountType.USER
  }

  void testCreateAccountNotSpecifyingTypeUseDefault() {
    client.setAuth("basic", "admin", "admin")

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
          "status": "ACTIVE"
        }"""
      }
    }
    assertStatus 201

    def json = JSON.parse(response.getContentAsString())
    def accountId = json.response.id
    assertNewAccount(accountId)
  }

  void testCreateAccountNotSpecifyingStatusUseDefault() {
    client.setAuth("basic", "admin", "admin")

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
            "type": "USER"
        }"""
      }
    }
    assertStatus 201

    def json = JSON.parse(response.getContentAsString())
    def accountId = json.response.id
    assertNewAccount(accountId)
  }

  void testCreateAccountWithInvalidDataRespondWithError() {
    client.setAuth("basic", "admin", "admin")

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
            "type": "USdshjdsER"
        }"""
      }
    }
    assertStatus 400
  }

  //TODO por hacer del migue
  void testTransfer() {
    def bagName = "CASHBACK_TRANSFER_TEST"
    def sourceAccountId, targetAccountId
    client.setAuth("basic", "admin", "admin")

    post('/bag') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "name": "${bagName}",
                "description": "Bolsa de activacion"
                }"""
      }
    }
    assertStatus 201

    sourceAccountId = JSON.parse(response.getContentAsString()).response.accountId

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "type": "USER",
                "status": "ACTIVE",
                "amount": 100,
                "detail": "Recomendación de amigos"
                }"""
      }
    }
    assertStatus 201
    targetAccountId = JSON.parse(response.getContentAsString()).response.id


    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                        "amount": 100,
                        "concept": "Recomendación de amigos",
                        "validity": "RELATIVE",
                        "duration": 5,
			"bagName" : "${bagName}"
                    }"""
      }
    }
    assertStatus 200
    assertContentContains "balance"
    assertContentContains "100"

    post("/accounts/transfer") {
      headers['Content-Type'] = 'application/json'
      body {
        """
                {
                    "bagName": $bagName,
                    "targetAccount": $targetAccountId,
                    "amount": 20,
                    "concept": "Promoción de TotalMovie",
                    "validity": "RELATIVE",
                    "duration": 2,
                }
                """
      }
    }

    assertStatus 200
    def respuesta = JSON.parse(response.getContentAsString())
    assert respuesta?.response?.sourceBalance == 80
    assert respuesta?.response?.targetBalance == 20
   /* TODO: Descomentar cuando se soporte activationDate para transferencias
            de bolsa a cuentas de usuario.
    post("/accounts/transfer") {
      headers['Content-Type'] = 'application/json'
      body {
        """
                {
                    "bagName": $bagName,
                    "targetAccount": $targetAccountId,
                    "amount": 80,
                    "concept": "Promoción de TotalMovie",
                    "validity": "RELATIVE",
                    "duration": 2,
                    "activationDate": "${new DateTime().plusDays(10).toString("yyyy-MM-dd'T'HH:mm:ss")}"
                }
                """
      }
    }

    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta?.response?.sourceBalance == 0
    assert respuesta?.response?.targetBalance == 20*/
  }


  void testHistory() {
    def bagName = "CASHBACK_TEST_TRANSFER"
    def sourceAccountId, targetAccountId

    client.setAuth("basic", "admin", "admin")

    post('/bag') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "name": "${bagName}",
                "description": "Bolsa de activacion"
                }"""
      }
    }
    assertStatus 201

    sourceAccountId = JSON.parse(response.getContentAsString()).response.accountId

    post('/accounts') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "type": "USER",
                "status": "ACTIVE",
                "detail": "Recomendación de amigos"
                }"""
      }
    }
    assertStatus 201
    targetAccountId = JSON.parse(response.getContentAsString()).response.id

    def account = Account.get(targetAccountId)
    def balance = account.balance

    def parametros = ["amount": 10000,
        "concept": "Orígen divino",
        "validity": "RELATIVE",
        "duration": 5,
	"bagName": bagName
    ]
    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }
    assertStatus 200

    def respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.meta.status == 200
    assert respuesta.response.balance == 10000

    parametros."activationDate" = new DateTime().plusDays(10).toString("yyyy-MM-dd'T'HH:mm:ss")
    parametros.amount = 100
    parametros.bagName = bagName
    parametros.targetAccount = targetAccountId

    post("/accounts/transfer") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }
    assertStatus 200
    assertContentContains "targetBalance"

    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.meta.status == 200
    assert respuesta.response.targetBalance == balance + 100


    post("/accounts/${account.id}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                        "amount": .50,
                        "concept": "Recomendación de amigos",
                    }"""
      }
    }
    assertStatus 200
    assertContentContains "balance"


    post("/accounts/${account.id}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                        "amount": 10.50,
                        "concept": "Recomendación de amigos",
                    }"""
      }
    }
    assertStatus 200
    assertContentContains "balance"

    get("/accounts/${account.id}/history")
    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.meta.status == 200

    get("/accounts/${account.id}/history?from=${new LocalDate().toString()}")
    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.meta.status == 200
  }

  void testDepositos() {
    def accountId
    def bagName = "CASHBACK_TEST_DEPOSITS"
    client.setAuth("basic", "admin", "admin")
    
    post('/bag') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "name": "${bagName}",
                "description": "Bolsa de activacion"
                }"""
      }
    }
    assertStatus 201

    accountId = JSON.parse(response.getContentAsString()).response.accountId

    def parametros = [bagName: bagName, amount: 100, concept: "Deposito a la bolsa de chasback", validity: "ABSOLUTE", duration: "5"]
    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }

    assertStatus 200
    def respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 100

    parametros.activationDate = new DateTime().plusDays(10).toString("yyyy-MM-dd'T'HH:mm:ss")
    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }

    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 100

    parametros.activationDate = null
    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }
    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 200
  }

  void testRetiros() {
    def accountId
    def bagName = "CASHBACK_TEST_RETIROS"

    client.setAuth("basic", "admin", "admin")
    
    post('/bag') {
      headers['Content-Type'] = 'application/json'
      body {
        """{
                "name": "${bagName}",
                "description": "Bolsa de activacion"
                }"""
      }
    }
    assertStatus 201

    accountId = JSON.parse(response.getContentAsString()).response.accountId

    def parametros = [bagName: bagName, amount: 100, concept: "Deposito a la bolsa de chasback", validity: "ABSOLUTE", duration: "5"]
    post("/accounts/deposit") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }

    assertStatus 200
    def respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 100
   
    post('/accounts') {
      headers['Content-Type'] = 'application/json'
    }
    assertStatus 201
    respuesta = JSON.parse(response.getContentAsString())
    accountId = respuesta.response.id

    parametros = [bagName: bagName, targetAccount: accountId, amount: 100, concept: "Recomendación de amigos", validity: "ABSOLUTE", duration: "5"]
    post("/accounts/transfer") {
      headers['Content-Type'] = 'application/json'
      body { parametros as JSON }
    }

    post("/accounts/${accountId}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
          "amount": 50,
          "concept": "Compra de la order 123-121212"
        }"""
      }
    }
    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 50


    post("/accounts/${accountId}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
          "amount": 100,
          "concept": "Compra de la order 123-121212"
        }"""
      }
    }
    assertStatus 400

    post("/accounts/${accountId}/withdraw") {
      headers['Content-Type'] = 'application/json'
      body {
        """{
          "amount": 50,
          "concept": "Compra de la order 123-121212"
        }"""
      }
    }
    assertStatus 200
    respuesta = JSON.parse(response.getContentAsString())
    assert respuesta.response.balance == 0
  }

  Account getAccount(Serializable accountId) {
    //A bad hack
    Account.withTransaction { status ->
        status.flush()
    }

    Account.withTransaction { status ->
        Account.get(accountId)
    }

  }

}

