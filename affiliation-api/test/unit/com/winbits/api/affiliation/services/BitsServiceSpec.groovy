package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.TransactionsCommand
import com.winbits.api.clients.balance.BalanceClient
import com.winbits.api.clients.bits.BitsAccountStatus
import com.winbits.api.clients.bits.BitsAccountType
import com.winbits.api.clients.bits.BitsClient
import com.winbits.bits.ConfigurationService
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.config.ConfigurationService
import com.winbits.domain.migration.UserBitsTrouble

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.joda.time.DateTime
import org.joda.time.LocalDate

import spock.lang.Specification

import wslite.json.JSONObject

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BitsService)
@Build([User, Profile])
@Mock([UserBitsTrouble])
class BitsServiceSpec extends Specification {

  private User user
  private Long bitsId
  private BitsClient bitsClient
  private BalanceClient balanceClient

  def setup() {
    User.metaClass.encodePassword {->}
    bitsId = 3456
    user = User.build()
    Profile.build(bitsId: bitsId, user: user)
    bitsClient = Mock(BitsClient)
    balanceClient = Mock(BalanceClient) 
    service.bitsClient = bitsClient
    service.balanceClient = balanceClient
  }


  void "test get bits"() {
    when:
    def bits = service.forUser(user)

    then:
    bits == 567
    balanceClient.getBitsBalance(bitsId) >> 567
  }

  void "test get bits history without date"() {
    setup:
    def map = [balance: 67, transactions: []]
    def dateFrom = new LocalDate(2012, 8, 5)
    TransactionsCommand cmd = [from: dateFrom, to: LocalDate.now()]
    Map mapCmd = cmd.filters() + cmd.params()

    when:
    def result = service.bitsAccountHistory(user, cmd)

    then:
    result == map
    1 * bitsClient.getBitsAccountHistory(bitsId, mapCmd) >> map
  }

  void "test create account"() {
    when:
    def result = service.createAccount()

    then:
    result
    1 * bitsClient.createBitsAccount(BitsAccountType.USER, BitsAccountStatus.ACTIVE) >> [id: 89, balance: 0]
  }

  void "test should obtain difference between days"() {
  when:
    Long differenceOfDays = new DateTime().plusDays(47).getMillis()
    int days = service.obtainDaysToExpireBetweenDates(differenceOfDays)
  then:
    assert days == 47
  }

  void "test should obtain days to expire"() {
  given:
    ConfigurationService configurationService = Mock()
    configurationService.getConfigValue (_, _) >> 365
    service.configurationService = configurationService
  when:
    int days = service.obtainDaysToExpire(expirationDate)
  then:
    assert days == response
  where:
    expirationDate                                              |       response
      (new DateTime().now().plusDays(47).getMillis())           |       47
        null                                                    |       365
  }

  void "should transfer bits 4 migration"() {
  given:
    ConfigurationService configurationService = Mock()
    configurationService.getConfigValue(_, _ as String) >> 'CREDIT-MIGRATION'
    configurationService.getConfigValue(_, _ as Integer) >> 365
    service.configurationService = configurationService
  when:
    Map response = service.transferBitsForMigration(user, [:])
  then:
    1 * service.bitsClient.transferBits(_) >> [meta:[status:200, message:'fooo', code: 'ok']]
    assert response
    assert response.meta.status == 200
  }
  
  void "should fail on response"() {
  given:
    ConfigurationService configurationService = Mock()
    configurationService.getConfigValue(_, _ as String) >> 'CREDIT-MIGRATION'
    configurationService.getConfigValue(_, _ as Integer) >> 365
    service.configurationService = configurationService
  when:
    Map response = service.transferBitsForMigration(user, [balance:10,description:'foo'])
  then:
    1 * service.bitsClient.transferBits(_) >>{ throw new Exception()}
    def userBitsTrouble =UserBitsTrouble.get(1)
    assert userBitsTrouble
    assert userBitsTrouble.deposit == 10
    assert userBitsTrouble.concept == 'foo'
  }

  void "should check 4 json object null"() {
  setup:
    JSONObject jsonObject = new JSONObject()
    jsonObject.expirationDate = null
  when:
    def result = service.isJsonObject (jsonObject)
  then:
    assert result
  }

  void "should obtain expirationDate"() {
  when:
    if (!(migrationCredit instanceof JSONObject)){
      Long expirationDate = 300
      migrationCredit.expirationDate = expirationDate    
    } 
    def result = service.obtainExpirationDate(migrationCredit)
  then:
    assert result == expectedResult
  where:
    migrationCredit     |       expectedResult
      [:]               |           300
      new JSONObject()  |           null
  }
}
