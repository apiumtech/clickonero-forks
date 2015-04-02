package com.winbits.bits

import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured
import grails.validation.Validateable
import org.joda.time.DateTime
import org.joda.time.LocalDate

@Secured(['ROLE_ADMIN'])
class AccountsController {

  static allowedMethods = [save: 'POST', balance: 'GET']

  def accountService
  def springSecurityService

  def save(CreateCommand command) {
    log.debug "params $params"

    def resultado = withCommand(command) {
      accountService.createAccount(command.type, command.status)
    }

    //TODO: agregar log al mis
    log.info "Numero de cuenta:'${resultado?.id}' Usuario:'${springSecurityService?.principal?.username}'"
    restpond data: resultado, status: 201
  }

  def balance(Long id) {
    log.debug "params $params id:$id"
    def balance = accountService.getBalance(id)
    log.debug "Balance for account ${id} = ${balance}"

    restpond([balance: balance])
  }

  def deposit(DepositBitCommand command) {
    log.debug "params $params command:$command"

    def resultado = withCommand(command) {
      accountService.addBits(command)
    }

    restpond resultado
  }

  def withdraw(WithdrawBitCommand command) {
    log.debug "params $params command:$command"

    def resultado = withCommand(command) {
      accountService.withdrawBits(command)
    }

    restpond resultado
  }

  def transfer(TransferBitCommand command) {
    log.debug "params $params"
    def resultado = withCommand(command) {
      log.debug "account ${command.tAccount} - ${command.sAccount}"
      accountService.tranferBits(command)
    }

    restpond resultado
  }

  def history(HistoryBitCommand command) {
    def resultado = withCommand(command) {
      accountService.history(command)
    }

    def total = withCommand(command) {
         accountService.historyCount(command)
      }

    restpond data: resultado, meta: total
  }

  def rollback(RefundBitsCommand command) {
    def result = withCommand(command){
      accountService.rollback(command)
    }

    restpond result
  }

  def rewardRegister(RewardRegisterCommand command) {
    def result = withCommand(command) {
      accountService.registerReward(command)
    }

    restpond result
  }

  def findRewardRegister(Long orderDetailId) {
    log.debug "params $params orderDetailId:$orderDetailId"
    def register = accountService.getRewardRegister(orderDetailId)
    log.debug "Reward register ${orderDetailId}: ${register}"

    restpond([register: register])
  }

  def giftRegister(GiftRegisterCommand command) {
    def result = withCommand(command) {
      accountService.registerGift(command)
    }

    restpond result
  }

  private def withCommand(command, Closure c) {
    command.beforeValidate()
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }

    c.call command
  }
}

@Validateable
class HistoryBitCommand extends BitCommand{
  LocalDate from, to
  Integer max
  Integer offset
  String sort
  String order
  def beforeValidate() {
    super.beforeValidate()
    from = from ?: account?.dateCreated?.toLocalDate()
    to = to ?: new LocalDate()
  }
}

@Validateable
class CreateCommand {
  AccountStatus status
  AccountType type

  def beforeValidate() {
    status = status?:AccountStatus.ACTIVE
    type = type?:AccountType.USER
  }

}

@Validateable
class TransferBitCommand extends OperationBitCommand {
  String bagName
  Long targetAccount
  Account sAccount, tAccount

  def beforeValidate() {
    super.beforeValidate()
    sAccount = Bag.findByName(bagName)?.account
    tAccount = Account.get(targetAccount)
  }

  static constraints = {
    id nullable: true
  }

}

@Validateable
class DepositBitCommand extends OperationBitCommand {
  String bagName

  def beforeValidate() {
    super.beforeValidate()
    account = Bag.findByName(bagName)?.account
  }

  static constraints = {
    id nullable: true
  }
}

@Validateable
class WithdrawBitCommand extends BitCommand {
  String bagName
  Long orderNumber

  def beforeValidate() {
    super.beforeValidate()
  }

  static constraints = {
    bagName nullable: true
    orderNumber nullable: true
  }
}

@Validateable
class OperationBitCommand extends BitCommand {
  DateTime activationDate
  AccountValidity validity
  Integer duration

  def beforeValidate() {
    super.beforeValidate()
    activationDate = activationDate ?: new DateTime()
  }

  static constraints = {
    validity nullable: false
    duration nullable: false
  }

}

@Validateable
class RefundBitsCommand extends BitCommand {
  Long transactionId
  String orderNumber

  static constraints = {
    id nullable: true
    amount nullable: true
    orderNumber nullable: true
  }
}

@Validateable
class BitCommand {
  Account account
  Long id
  BigDecimal amount
  String concept

  def beforeValidate() {
    if (!account && id) {
      account = Account.get(id)
    }
  }

  static constraints = {
    account nullable: true
    id nullable: false
  }
}

@Validateable
class RewardRegisterCommand {
  Account account
  Long accountId
  Long bitsCategoryId
  Long orderDetailId
  BigDecimal amount
  Integer duration
  String validity
  String bagName
  String awarder
  String concept
  String justification
  BitsCategory bitsCategory
  DateTime awardedDate
  DateTime lastUpdated

  def beforeValidate() {
    account = Account.get(accountId)
    bitsCategory = BitsCategory.get(bitsCategoryId)
    awardedDate = DateTime.now()
    lastUpdated = DateTime.now()
  }

  static constraints = {
    account nullable: false
    validity nullable: false
    orderDetailId nullable: false
    amount nullable: false
    duration nullable: false
    bagName nullable: false
    awarder nullable: false
    concept nullable: false
    justification nullable: false
    bitsCategory nullable: false
    awardedDate nullable: false
  }
}

@Validateable
class GiftRegisterCommand {
  Account account
  Long accountId
  Long bitsCategoryId
  Long cknOrderId
  BigDecimal amount
  Integer duration
  String validity
  String bagName
  String awarder
  String concept
  String justification
  BitsCategory bitsCategory
  DateTime awardedDate
  DateTime lastUpdated

  def beforeValidate() {
    account = Account.get(accountId)
    bitsCategory = BitsCategory.get(bitsCategoryId)
    awardedDate = DateTime.now()
    lastUpdated = DateTime.now()
  }

  static constraints = {
    account nullable: false
    validity nullable: false
    cknOrderId nullable: true
    amount nullable: false
    duration nullable: false
    bagName nullable: false
    awarder nullable: false
    concept nullable: false
    justification nullable: false
    bitsCategory nullable: false
    awardedDate nullable: false
  }
}
