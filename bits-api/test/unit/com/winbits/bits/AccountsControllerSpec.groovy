package com.winbits.bits

import com.winbits.exceptions.api.client.EntityValidationException
import grails.buildtestdata.mixin.Build
import grails.converters.JSON
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AccountsController)
@Build([Account, Transaction, Deposit])
class AccountsControllerSpec extends Specification {

  def accountService

  void setup() {
    request.contentType = "text/json"
    accountService = Spy(AccountService)
    controller.accountService = accountService

    controller.springSecurityService = [principal: [username: "admin"]]

    controller.metaClass.static.restpond = { data ->
      controller.render([response: data?.data, status: data?.status] as JSON)
    }
  }

  def "crear cuenta con datos validos"() {
    when:
    request.json = entrada
    controller.save()
    then:
    invokation * accountService.createAccount(type, status)
    response.json?.response?.id == jsonId
    response.json?.response?.balance == jsonBalance
    where:
    entrada << [""" { "type": "USER", "status":"ACTIVE"} """,
        """ { "type": "VERTICAL", "status":"ACTIVE"} """ ]
    type << [AccountType.USER, AccountType.VERTICAL]
    status << [AccountStatus.ACTIVE, AccountStatus.ACTIVE]
    invokation << [1, 1]
    jsonId << [1, 1]
    jsonBalance << [0, 0]
  }

  def "Crear cuenta con datos invalidos"(){
    when:
    request.json = entrada
    controller.save()
    then:
    def ex = thrown(EntityValidationException)
    where:
    entrada << [ """ { "type": "desconocido", "status":"ACTIVE"} """,
        """ { "type": "", "status":"kljaskdjklsajd"} """]
    type << [null, null]
    status << [null, null]
    invokation << [0, 0]
    jsonId << [null, null]
    jsonBalance << [null, null]
  }


  @Ignore
  def "regresar balance"() {
    setup:
    def accountService = Spy(AccountService)
    controller.accountService = accountService
    Account.build(balance: 0)
    Account.build(balance: 100)
    when:
    controller.balance(id)
    then:
    response.json.balance == balance
    1 * controller.accountService.getBalance(id)
    where:
    id  | balance
    1   | 0
    2   | 100
    100 | null
  }

  @Ignore
  def "añadir bits"() {
    setup:
    Account.build()
    Account.build(balance: 100)
    when:
    params.id = id
    request.json = entrada
    controller.deposit()
    then:
    response.json?.balance == balance
    where:
    id | entrada                                                       | balance
    1  | """{ "amount": 100, "concept": "Recomendación de amigos" }""" | 100
    2  | """{ "amount": 100, "concept": "Recomendación de amigos" }""" | 200
    1  | """{ "amount": 10}"""                                         | null
  }

  @Ignore
  def "cargar_winbits"() {
    setup:
    Account.build()
    Account.build(balance: 100)
    when:
    params.id = id
    request.json = entrada
    controller.withdraw()
    then:
    response.json?.balance == balance
    where:
    id | entrada                                           | balance
    1  | """{ "amount": 100, "concept":"prueba" }   """    | null
    2  | """{ "amount": 100, "concept":"prueba"}   """     | 0
    2  | """{ "amount": 50, "concept":"prueba" }   """     | 50
    2  | """{ "amount": 0.50, "concept":"prueba" }   """   | 99.50
    2  | """{ "amount": 100.50, "concept":"prueba" }   """ | null


  }


}
