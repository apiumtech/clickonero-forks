package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.TransactionsCommand
import com.winbits.api.clients.balance.BalanceClient
import com.winbits.api.clients.bits.BitsAccountStatus
import com.winbits.api.clients.bits.BitsAccountType
import com.winbits.api.clients.bits.BitsClient
import com.winbits.api.clients.bits.BitsTransferRequest
import com.winbits.api.clients.bits.BitsValidity
import com.winbits.domain.affiliation.User
import com.winbits.domain.migration.UserBitsTrouble
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.LocalDate
import wslite.json.JSONObject

class BitsService {
  //static transactional = false

  BitsClient bitsClient
  GrailsApplication grailsApplication
  BalanceClient balanceClient
  def configurationService

  BigDecimal forUser(User user) {
    balanceClient.getBitsBalance(bitsAccountId(user))
  }
  
  BitsTransferRequest fillTransferRequest(String concept, Long bitsId, String bagName,
    Integer duration, LocalDate activationDate,  BigDecimal amount, def validity){
    BitsTransferRequest bitsTransferRequest = new BitsTransferRequest()

    bitsTransferRequest.concept = concept
    bitsTransferRequest.targetAccount = bitsId
    bitsTransferRequest.bagName = bagName
    bitsTransferRequest.duration = duration 
    bitsTransferRequest.activationDate = activationDate
    bitsTransferRequest.amount = amount
    bitsTransferRequest.validity = validity
    bitsTransferRequest
  }

  Map transferBitsForCompleteRegister(User user){
    def cashback = configurationService.getConfigValue('promo.completeRegisterCashbackAmount',BigDecimal)
    def bagName = configurationService.getConfigValue('promo.completeRegisterCashback.bag', String)
    def duration = configurationService.getConfigValue('promo.completeRegisterCashback.duration', Integer)

    def transferResponse = [:]
    if (cashback > 0){
      BitsTransferRequest bitsTransferRequest = new BitsTransferRequest()

      bitsTransferRequest = fillTransferRequest('Registro completo', 
        user.profile().bitsId, bagName, duration, LocalDate.now(),
        cashback, BitsValidity.ABSOLUTE)

      transferResponse = bitsClient.transferBits(bitsTransferRequest)
    }
    transferResponse.cashback = cashback
    transferResponse
  }

  boolean isJsonObject(def migrationCredit) {
    migrationCredit instanceof JSONObject
  }

  Long obtainExpirationDate(def migrationCredit) {
    def isJson = isJsonObject(migrationCredit) 
    isJson ? null : migrationCredit.expirationDate
  }

  Map transferBitsForMigration(User user, Map migrationCredit) {
    def bagName = configurationService.getConfigValue('credit.migration.bag', String)
    Integer duration = obtainDaysToExpire(obtainExpirationDate(migrationCredit))
    
    def transferResponse = [:]
    BitsTransferRequest bitsTransferRequest = new BitsTransferRequest()
    bitsTransferRequest = fillTransferRequest(migrationCredit.description, 
        user.profile().bitsId, bagName, duration, LocalDate.now(),
        migrationCredit.balance, BitsValidity.ABSOLUTE)
    try {
      transferResponse = bitsClient.transferBits(bitsTransferRequest)
      transferResponse.cashback = migrationCredit.balance
      if (transferResponse?.meta?.status != 200) 
        saveUserBitsTrouble(user, migrationCredit, transferResponse?.meta?.status)
    }catch (Exception e){
      saveUserBitsTrouble(user,migrationCredit)
    }
    transferResponse
  }

  UserBitsTrouble saveUserBitsTrouble(User user, Map migrationCredit, String status = ''){
    UserBitsTrouble userBitsTrouble = new UserBitsTrouble()
    userBitsTrouble.user = user
    userBitsTrouble.deposit = migrationCredit.balance
    userBitsTrouble.errorDescription = status
    userBitsTrouble.concept = migrationCredit.description
    userBitsTrouble.save()
  }

  Integer obtainDaysToExpire(Long expirationDate){
    expirationDate? obtainDaysToExpireBetweenDates(expirationDate) : 
      configurationService.getConfigValue('credit.migration.daysToExpireCredit', Integer)
  }

  int obtainDaysToExpireBetweenDates(long expiration){
    DateTime today = new DateTime().now()
    DateTime expirationDate = new DateTime(expiration)
    Days.daysBetween(today.withTimeAtStartOfDay() , expirationDate.withTimeAtStartOfDay()).getDays()
  }

  private long bitsAccountId(User user) {
    return user.profile().bitsId
  }

  Map bitsAccountHistory(User user, TransactionsCommand cmd) {
    Map map = cmd.filters() + cmd.params()
    bitsClient.getBitsAccountHistory(bitsAccountId(user), map)
  }

  Long createAccount() {
    def accountData = bitsClient.createBitsAccount(BitsAccountType.USER, BitsAccountStatus.ACTIVE)
    accountData.id
  }
}
