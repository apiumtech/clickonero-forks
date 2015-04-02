package com.winbits.bits

import com.winbits.bits.exception.BitsAccountNotFoundException
import com.winbits.bits.rabbitmq.listener.messages.PublishMessageService
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.DateTime
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
@Mock([PublishMessageService])
@Build([Account, Deposit, Transaction,Bag])
class AccountServiceSpec extends Specification {

  def setup() {
    //WorkAround http://jira.grails.org/browse/GRAILS-8555
    Deposit.currentGormInstanceApi().failOnError = true
    Transaction.currentGormInstanceApi().failOnError = true
  }

  def "Crear una cuenta normal"() {
    when:
    String client = "Winbits"
    AccountType type = AccountType.USER
    AccountStatus status = AccountStatus.ACTIVE
    Account account = service.createAccount(type, status)
    then:
    account
  }

  def "regresar el balance"() {
    when:
    Account account = Account.build(balance: balance)
    then:
    service.getBalance(accountId) == balanceFinal
    where:
    balance | balanceFinal | accountId
    1000    | 1000         | 1
    200     | 200          | 1
    -100    | -100         | 1
  }

  def "regresar balance con cuenta no encontrada"() {
    setup:
    Account account = Account.build(balance: balance)
    when:
    service.getBalance(accountId) == balanceFinal
    then:
    def ex = thrown(BitsAccountNotFoundException)
    where:
    balance | balanceFinal | accountId
    100     | null         | 10
  }

  def "duracion de los bits"() {
    when:
    Account.build(status: AccountStatus.ACTIVE, balance: 0)
    then:
    command.beforeValidate()
    service.getValidity(command) == resultado
    where:
    command                                                                                                                                                               | resultado
    new OperationBitCommand(id: 1, concept: "prueba", amount: 100, duration: 5)                                                                                           | null
    new OperationBitCommand(id: 1, concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.ABSOLUTE, activationDate: new DateTime("2013-12-12T00:00:00"))  | new DateTime("2013-12-17T00:00:00")
    new OperationBitCommand(id: 1, concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.ABSOLUTE)                                                       | new DateTime().plusDays(5).withTimeAtStartOfDay()
    new OperationBitCommand(id: 1, concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.RELATIVE, activationDate: new DateTime("2013-12-12T00:00:00"))  | new DateTime("2014-05-31T00:00:00")
  }

}
