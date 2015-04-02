package com.winbits.bits

import com.winbits.bits.exception.BitsAccountNotFoundException
import grails.plugin.spock.IntegrationSpec
import org.joda.time.DateTime

class AccountServiceIntegrationSpec extends IntegrationSpec {
  def accountService

  void setup() {
    new Account(
        number:100,
        balance:0,
        type: AccountType.USER,
        status:AccountStatus.ACTIVE

    ).save(failOnError: true)

  }

  void "añadir bits una cuenta"() {
    setup:
    def account = Account.build()
    command.id = account.id
    when:
    command?.beforeValidate()
    def resultado = accountService.addBits(command)
    then:
    resultado
    resultado.depositId
    resultado.balance == balance.balance
    resultado.expirationDate == balance.expirationDate
    Transaction.countByAccount(command?.account) == balance != null ? 1 : 0
    Deposit.count == balance != null ? 1 : 0
    where:
    command                                                                                                                         | balance
    new OperationBitCommand(concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.ABSOLUTE) | [balance: 100, expirationDate: new DateTime().plusDays(5).withTimeAtStartOfDay() ]
    new OperationBitCommand(concept: "prueba", amount: 100, duration: 10, validity: AccountValidity.ABSOLUTE)| [balance: 100, expirationDate: new DateTime().plusDays(10).withTimeAtStartOfDay()]
    new OperationBitCommand(concept: "prueba", amount: 200, duration: 12, validity: AccountValidity.RELATIVE) | [balance: 200, expirationDate: new DateTime().plusMonths(12).dayOfMonth().withMaximumValue().withTimeAtStartOfDay()]
  }

  void "añadir bits a una cuenta datos invalidos"() {
    when:
    command?.beforeValidate()
    def resultado = accountService.addBits(command) ?: [null, null]
    then:
    def ex = thrown(exception)
    where:
    command                                                              | exception
    new OperationBitCommand(id: 1000000, concept: "prueba", amount: 100) | BitsAccountNotFoundException
  }

  def "añadir bits varias veces a una cuenta"() {
    when:
    def account = accountService.createAccount(AccountType.USER, AccountStatus.ACTIVE)
    def command = new OperationBitCommand(id: account.id, concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.ABSOLUTE)
    command.beforeValidate()
    then:
    accountService.addBits(command).balance == 100
    accountService.addBits(command).balance == 200
  }
  //TODO del migue
  /*
  def "retiro de puntos"() {
    when:
    Account account = Account.get(accountService.createAccount(AccountType.USER, AccountStatus.ACTIVE).id)

    def command = new BitCommand(id: account.id, concept: "prueba", amount: 100)
    def commandAdd1 = new OperationBitCommand(id: account.id, concept: "prueba", amount: 100, duration: 5, validity: AccountValidity.ABSOLUTE)
    def commandAdd2 = new OperationBitCommand(id: account.id, concept: "prueba", amount: 50, duration: 2, validity: AccountValidity.ABSOLUTE)
    def commandAdd3 = new OperationBitCommand(id: account.id, concept: "prueba", amount: 50, duration: 3, validity: AccountValidity.UNLIMITED)
    def commandAdd4 = new OperationBitCommand(id: account.id, concept: "prueba", amount: 50, duration: 3, validity: AccountValidity.ABSOLUTE)
    commandAdd1.beforeValidate()
    commandAdd2.beforeValidate()
    commandAdd3.beforeValidate()
    commandAdd4.beforeValidate()
    accountService.addBits(commandAdd1)
    accountService.addBits(commandAdd2)
    accountService.addBits(commandAdd3)
    accountService.addBits(commandAdd4)
    then:
    account.refresh()
    command.beforeValidate()
    command.account.refresh()
    accountService.withdrawBits(command)?.balance == 150
    accountService.withdrawBits(command)?.balance == 50
    !accountService.withdrawBits(command)?.balance
  } */

}