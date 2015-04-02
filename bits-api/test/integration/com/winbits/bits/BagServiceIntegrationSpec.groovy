package com.winbits.bits

import grails.plugin.spock.IntegrationSpec

/**
 * Created by winbits on 2/5/15.
 */
class BagServiceIntegrationSpec extends IntegrationSpec {

  def bagService

  void "check balance for bags"() {
    setup:
    bagService.createBag('bag1', 'descriptionbag')
    def bag = Bag.findByName('bag1')
    if( depositAmount > 0) {
      Deposit.build(openingBalance: depositAmount, amount: depositAmount, account: bag.account)
    }

    when:
    def amount = bagService.calculateBagBalance(bag)

    then:
    amount != null
    amount == depositAmount

    where:
    depositAmount << [0, 100, 1000000]

  }

  def createBag(String name, partialDepositAmount, minBalance) {
    bagService.createBag(name, "description_$name")
    def bag = Bag.findByName(name)
    bag.partialDepositAmount = partialDepositAmount
    bag.minBalance = minBalance
    bag.duration = 365
    bag.save()
  }

  void "find bags who its balance is coming to an end"() {
    setup:
    def bag1 = createBag('bag1', 1000, 100)
    def bag2 = createBag('bag2', 1000, 100)
    def bag3 = createBag('bag3', 1000, 100)

    Deposit.build(openingBalance: 1000, amount: 101, account: bag1.account)
    Deposit.build(openingBalance: 1000, amount: 100, account: bag2.account)
    Deposit.build(openingBalance: 1000, amount:  99, account: bag3.account)

    when:
    def bags = bagService.findBagsBeforeAmountPeterOut()

    then:
    bags
    bags.name.containsAll(['bag2', 'bag3'])
    !bags.name.contains('bag1')

  }

  void "generate deposit for bag"() {
    setup:
    def bag = createBag('bag1', 1000, 100)

    when:
    def result = bagService.generateDeposit(bag)

    then:
    result
    result.balance == 1000

  }

}
