package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.Gender
import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.VerticalPartner

import org.apache.commons.lang.RandomStringUtils
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate

class MigrationService {

  def migrationClient
  def shippingAddressService
  def messagingService
  def bitsService

  private Map obtainPerson(String email) {
    def response 
    try{
      response = migrationClient.obtainPersonByEmail (email)?.response
    }catch (Exception e) {
      log.error 'error to connect to person migration service: ', e
      response = [status:400]
    }
    response
  }

  boolean isUserForMigration(String email) {
    User user = obtainUserByEmail(email)
    user && (!user.salt && !user.enabled) 
  }

  boolean isUserOnlyWithSalt(User user) {
    user && !user.salt && user.enabled
  }

  User obtainUserByEmail(String email) {
    User.findByEmail(email)  
  }

  Map obtainUserInfo(String email) {
    def response 
    try{
      response = migrationClient.obtainUserInfo(email)?.response
    }catch (Exception e) {
      log.error 'error to connect to person migration service: ', e
      response = [status:400]
    }
    response
  }

  boolean isValidResponse(response) {
    response && response.status == 200
  }
  
  def obtainGenderFromPerson(String gender) {
    gender?obtainGender(gender): null 
  }

  def obtainLocalDateFromLong(Long birthdate) {
    birthdate ? new LocalDate(birthdate):null 
  } 

  def obtainGender(String gender) {
   if(gender == 'M')
     return Gender.male  

    Gender.female
  }

  User obtainProfile(User user) {
    def  response = obtainPerson (user.email)
    if (isValidResponse(response)) {
      def person = response.person
      Profile profile = user.profile()
      profile.with {
        name = person.firstName
        lastName = person.lastName
        gender = person.gender ? obtainGender(person.gender): null
        birthdate = obtainBirthDate(person?.birthdate) 
        location = person.location
        phone = obtainPhoneFromResponse(person.phone)
        clickoneroId = person.idClickonero
        zipCode = person.zipCode?person.zipCode:null
      }
    }
    user
  }

  LocalDate obtainBirthDate(def milis) {
    log.info "milis $milis"
    def localDate = null
    if (milis){
      log.info "birthdate ${new LocalDate(milis)}"
      def timeZone = DateTimeZone.forID('UTC')
      localDate = new LocalDate(milis, timeZone)
      log.info "datetime ${new DateTime(milis,timeZone)}"
    }
    localDate
  }

  String obtainPhoneFromResponse(def phone) {
    phone?:null
  }

  User updateFacebookProfile(User user) {
    def response = obtainPerson(user.email)
    if(isValidResponse(response)){
      def person = response.person
      Profile profile = user.profile()
      profile.with {
        name = name?:person.firstName
        lastName = lastName?:person.lastName
        gender = gender?:obtainGenderFromPerson(person.gender) 
        birthdate = birthdate?: obtainLocalDateFromLong(person.birthdate) 
        location = location?:person.location
        phone = phone?:obtainPhoneFromResponse(person.phone) 
        zipCode = person.zipCode?person.zipCode:null
      }
    }
    user.generateSalt()
    user
  }
  
  def migrateClickonerUser(User user) {
    def response = obtainUserInfo(user.email)
    if(isValidResponse(response)){
      depositClickoneroCredit(user, response.clickoneroCredits)
      createShippingAddress(user, response.shippingAddress)
    }
    user
  }

  void depositClickoneroCredit(User user, List credit) {
    credit.each{ clickoneroCredit ->
      bitsService.transferBitsForMigration(user, clickoneroCredit)
    }  
  }

  def createVerticalPartner(User user){
    new VerticalPartner(user: user, vertical: user.vertical, 
      status: MigrationStatusEnum.PENDING.domain).save()
  }

  void migrateUserSendMessage(User user) {
    def map = [userId: user.id]
    messagingService.publishMessage("migrateUserFromPartner", map)
  }

  void migrateUserInfo (VerticalPartner verticalPartner) {
    def user = verticalPartner.user
    migrateClickonerUser(verticalPartner.user)
    verticalPartner.status = MigrationStatusEnum.COMPLETE.domain
    verticalPartner.save()
  }

  Map getShippingAddressByEmail(String email) {
    def response
    try{
      response = migrationClient.getShippingAddressByEmail (email)?.response
    }catch (Exception e) {
      log.error 'error to connect to person migration service: ', e
      response = [status:400]
    }
    response
  }

  def createShippingAddress(User user, List shippingAddresses) {
    boolean mainShippingAddress = true
    shippingAddresses.each{ it->
      ShippingAddress shippingAddress = shippingAddressService.
        createShippingAddressMigration(it, user, mainShippingAddress)
      if(shippingAddress.indications)  
        shippingAddress.save()  
      mainShippingAddress = false
    }
    user
  }

  def generateSaltAndRandomPassword(User user) {
    user.password = RandomStringUtils.randomAlphanumeric(10).toUpperCase()
    user.generateSalt()
  }
}
