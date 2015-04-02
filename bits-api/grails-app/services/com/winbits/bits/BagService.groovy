package com.winbits.bits

import com.winbits.bits.exception.BagAlreadyExistException
import org.joda.time.DateTime

class BagService {

  def accountService
  def defaultMinBalance = 50000
  def defaultDepositAmount = 500000
  def defaultDuration = 365
  def defaultConcept = "Abono por sistema"

  def createBag(String name, String description) {
    def bag = Bag.findByName(name)
    if( !bag ) {
      def result = accountService.createAccount(AccountType.PROMOTION , AccountStatus.ACTIVE)
      def account = Account.get(result.id)
      def newBag = new Bag(name: name, description: description, account: account).save()
      [id: newBag.id, accountId: account.id]
    
    } else {
      throw new BagAlreadyExistException()
    }

  }

  def checkBalances() {
    def bags = findBagsBeforeAmountPeterOut()
    bags?.each { generateDeposit(it) }
  }

  def findBagsBeforeAmountPeterOut() {
    def bags = Bag.findAllByNameNotInList(BagEnum.values().name)
    bags.findAll {
      def limit = it.minBalance?: defaultMinBalance
      def balance = calculateBagBalance(it)
      balance <= limit
    }
  }

  def calculateBagBalance(Bag bag) {
    Bag.executeQuery("select sum(amount) from Deposit where account = :accountParam",
        [accountParam: bag.account])?.first() ?: 0
  }

  def generateDeposit(Bag bag) {
    def amountToDeposit = bag.partialDepositAmount ?: defaultDepositAmount
    def duration = bag.duration ?: defaultDuration
    def depositCmd = new DepositBitCommand()
    depositCmd.account = bag.account
    depositCmd.bagName = bag.name
    depositCmd.activationDate = DateTime.now()
    depositCmd.amount = amountToDeposit
    depositCmd.validity = AccountValidity.ABSOLUTE
    depositCmd.duration = duration
    depositCmd.concept = defaultConcept
    accountService.addBits(depositCmd)
  }
}
