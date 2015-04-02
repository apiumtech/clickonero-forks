package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.SubscriptionCommand
import com.winbits.api.affiliation.exception.BitsApiNotAvailableException
import com.winbits.api.affiliation.exception.LinkDisableException
import com.winbits.api.affiliation.exception.PasswordNoMatchException
import com.winbits.api.affiliation.exception.UserAlreadyActivatedException
import com.winbits.api.affiliation.exception.UserBadCredentialsException
import com.winbits.domain.affiliation.*
import com.winbits.domain.catalog.ZipCodeInfo
import com.winbits.exceptions.api.client.EntityValidationException

import grails.plugins.springsecurity.Secured

import org.joda.time.LocalDate
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices

class AffiliationController {
  def registerService
  def profileService
  def loginService
  def userService
  def subscriptionService
  def authenticationService
  def bitsService
  def grailsApplication
  def facebookService
  def socialConnectService
  def trackingService
  def springSecurityService
  def multipleLoginService
  def migrationService

  static allowedMethods = [register: 'POST', confirm: 'GET', facebook: 'POST', profile: ['PUT', 'POST'], login: 'POST', logout: 'POST', accounts: 'GET',
      deleteAccount: 'DELETE', bitsAccount: 'GET', subscriptions: 'GET', updateSubscriptions: 'PUT', recover: 'POST', getProfile: 'GET',
      reset: 'POST', loginApiToken: 'POST', resendConfirmation: 'GET', tokens: 'GET', loginFacebookToken: 'POST', changePassword: 'PUT']

  def register(RegisterCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }

    def user = registerService.register(command)
    trackingService.trackRegister(user, command.utms)
    restpond([active: user.enabled])
  }

  def confirm(String salt) {
    if (!salt) {
      errors.rejectValue('salt', 'errors.required', ['salt'].toArray())
      throw new EntityValidationException(errors)
    }

    def person
    def pageReference
    try {
      person = registerService.activate(salt)
      pageReference = "#wb-complete-register-${person.apiToken}"
      def  hasBeenUserMigratedfromBebitos = multipleLoginService.migrateHistoryFromBebitosifIsNecesary(person,person.email, person.password)
        if(hasBeenUserMigratedfromBebitos){
            try {
                person.metaClass.hasBeenUserMigratedfromBebitos=true;
                messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
            }catch(Exception e){
               info.log "Exception ${e}"
            }

        }
    } catch (UserAlreadyActivatedException e) {
      person = e.user
      pageReference = '#err-AFER005'
    } catch (BitsApiNotAvailableException banfe) {
      person = banfe.user
      pageReference = '#err-AFER027'
    }
    redirect uri: buildUri("${person.baseUrl()}${pageReference}")
  }

  private def buildUri(String baseUrl, Map params = [:], boolean escape = true) {
    def queryParams = params.collect { key, value ->
      escape ? [key.toString().encodeAsURL(), value.toString().encodeAsURL()].join('=') :
        [key.toString(), value.toString()].join('=')
    }
    def queryString = queryParams.join('&')
    queryString ? "${baseUrl}?${queryString}" : "${baseUrl}"
  }

  def facebook(FacebookCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    def person, existing
    (person, existing) = registerService.facebookLogin(command)
    if (existing) {
      restpond(profileService.toProfileData(person))
    } else {
      facebookService.saveFacebookAccount(command)
      restpond data: profileService.toProfileData(person), status: 201
    }
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def profile(ProfileCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    def user = getAuthenticatedUser()
    user.salesAgentId = authenticationService.getCurrentSalesAgentId(request)
    def (profile, transferResponse) = profileService.editProfile(user, command)
    def profileData = profileService.toProfileData(profile.user, transferResponse.targetBalance)
    profileData.cashback = transferResponse.cashback ?: 0
    restpond profileData
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def getProfile() {
    def user = getAuthenticatedUser()
    user.salesAgentId = authenticationService.getCurrentSalesAgentId(request)
    def profile = Profile.findByUser(user)
    restpond profileService.toProfileData(profile.user)
  }

  def login(LoginCommand command) {
    log.debug "logged in controller"
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }

    def user 
    try{
      user = multipleLoginService.login(command.email, command.password)
    }catch(UserBadCredentialsException ube){
      if(ube?.user?.hasProperty('isMigrateUser') && ube?.user?.isMigrateUser) {
        migrationService.createVerticalPartner(ube.user)
        migrationService.migrateUserSendMessage(ube.user)
      }
      throw new UserBadCredentialsException(user)
    }
    def profile = profileService.toProfileData(user)
    checkForCompleteRegisterRemainder(user, profile)
    trackingService.trackLogin(user, command.utms)
    trackingService.updateUserUTMs(user, command.utms)
    profile.isMigrateUser = user.isMigrateUser
    if(user.isMigrateUser){
      migrationService.createVerticalPartner(user)
      migrationService.migrateUserSendMessage(user)
    }
    restpond(profile)
  }

  private checkForCompleteRegisterRemainder(User user, Map profileData) {
    profileData.showRemainder = profileService.mustShowCompleteProfileRemainder(user)
  }

  def loginApiToken(ExpressLoginCommand cmd) {
    if (cmd.hasErrors()) {
      throw new EntityValidationException(cmd.errors)
    }

    User user = authenticationService.authenticateByToken(cmd.apiToken, cmd.firstLogin)
    trackingService.updateUserUTMs(user, cmd.utms)
    def profile = profileService.toProfileData(user)
    if (user?.salesAgentId) {
      profile.switchUser = user.email
    }
    if (cmd.firstLogin) {
      trackingService.trackLogin(user, cmd.utms)
    }

    restpond profile
  }

  def loginFacebookToken(String facebookId) {
    if (!facebookId) {
      errors.rejectValue('facebookId', 'errors.required', ['facebookId'].toArray(), 'Error in param facebookId')
      throw new EntityValidationException(errors)
    }

    User user = loginService.loginFacebookToken(facebookId)
    boolean migrateUser = false
    if(migrationService.isUserForMigration(user?.email)) {
      migrateUser = true
    }
    def profile = profileService.toProfileData(user)
    checkForCompleteRegisterRemainder(user, profile)
    def status = registerService.determineStatus(user)
    if (status == 201) {
      trackingService.trackFacebookRegister(user, params.utms)
    } else {
      trackingService.trackLogin(user, params.utms)
    }
    trackingService.updateUserUTMs(user, params.utms)
    if(migrateUser){
      status = 201
      user.metaClass.isMigrateUser=true
      migrationService.updateFacebookProfile(user) 
      migrationService.createVerticalPartner(user)
      migrationService.migrateUserSendMessage(user)
    }
    restpond data: profile, status: status
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def accounts() {
    restpond([socialAccounts: socialConnectService.accounts()])
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def deleteAccount() {
    def user = getAuthenticatedUser()
    socialConnectService.removeConnection(params.id)
    if (params.id.equals(SocialProviderEnum.FACEBOOK.provider))
      registerService.updateUserToken(user, null)
    restpond([:])
  }

  def bitsAccount(Long id) {
    restpond([bitsAccount: profileService.bitsAccount(id)])
  }

  def recover(RecoverCommand command) {
    if (command.hasErrors())
      throw new EntityValidationException(command.errors)

    User user = migrationService.obtainUserByEmail(command.email)
    //Si el usuario no existe, tratamos de taerlo de bebitos
    if(!user){
        if(multipleLoginService){
            user = multipleLoginService.migrateBebitosUserByEmail(command.email)
        }
    }
    if(migrationService.isUserForMigration(command.email)) {
      multipleLoginService.migrateClickoneroUser(user)
      migrationService.createVerticalPartner(user)
      migrationService.migrateUserSendMessage(user)
      migrationService.generateSaltAndRandomPassword(user)
    }else if (migrationService.isUserOnlyWithSalt(user)) {
      migrationService.generateSaltAndRandomPassword(user)
    }
    
    
    restpond([message: loginService.recover(command.email, command.verticalId)])
  }

  def reset(ResetCommand command) {
    String hash = command.hash[0..-11]
    String crc = command.hash[64..-1]
    def message = "";

    if (!command.validate()) {
      throw new PasswordNoMatchException('grest.api.errors.AFER022.message')
    }

    if (!authenticationService.isActiveCRC(crc)) {
      throw new LinkDisableException('grest.api.errors.AFER022.message')
    }

    loginService.updatePassword(hash, command.password)
    message = "Password reseteado correctamente"
    restpond([message: message])
  }


  @Secured(['IS_AUTHENTICATED_FULLY'])
  def subscriptions() {
    restpond(subscriptionService.getSubscriptions(getAuthenticatedUser()))
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def updateSubscriptions(UpdateSubscriptionsCommand command) {
    command.metaClass.subscriptions = params.subscriptions.collect {
      bindData(new SubscriptionCommand(), it)
    }
    def user = getAuthenticatedUser()
    def data = [subscriptions: subscriptionService.updateSubscriptions(user, command),
        newsletterPeriodicity: user.profile().newsletterPeriodicity, newsletterFormat: user.profile().newsletterFormat]
    restpond data: data
  }


  @Secured(['IS_AUTHENTICATED_FULLY'])
  def logout() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    authenticationService.deleteApiToken(springSecurityService.currentUser.apiToken)
    new SecurityContextLogoutHandler().logout(request, response, auth);
    new PersistentTokenBasedRememberMeServices().logout(request, response, auth);
    def logoutRedirectUrl = grailsApplication.config.api.winbits.logout.redirectUrl as String
    restpond([logoutRedirectUrl: logoutRedirectUrl])
  }

  def resendConfirmation(String email) {
    email = email.decodeURL()
    if (!email) {
      errors.rejectValue('email', 'errors.required', ['email'].toArray())
      throw new EntityValidationException(errors)
    }
    registerService.resendConfirmationMail(email)
    restpond([:])
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def changePassword(ChangePasswordCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    def user = getAuthenticatedUser()
    user.salesAgentId = authenticationService.getCurrentSalesAgentId(request)
    loginService.changePassword(user, command.password, command.newPassword)

    restpond 'Ok'
  }
}

class RegisterCommand {
  String email
  String password
  Long verticalId
  String referredBy
  Map<String, String> utms

  static constraints = {
    email nullable: false, email: true
    password nullable: false
    verticalId nullable: false
  }
}

class RecoverCommand {
  String email
  Long verticalId
  static constraints = {
    email nullable: false, email: true
    verticalId nullable: true
  }
}

@grails.validation.Validateable
class ResetCommand {
  String hash
  String password
  String passwordConfirm
  Long verticalId

  static constraints = {
    hash blank: false
    password blank: false
    passwordConfirm blank: false, validator: { passwordConfirm, command ->
      if (command.password != passwordConfirm) {
        return 'invalid.matchingpasswords'
      }
    }

    verticalId nullable: true

  }
}

class FacebookCommand {
  def grailsApplication

  String email
  Long verticalId
  String referredBy
  String name
  String lastName
  LocalDate birthdate
  Gender gender
  Locale locale
  String facebookToken
  String providerUserId
  String profileUrl
  String imageUrl
  Long expireTime

  static constraints = {
    email nullable: false, email: true
    verticalId nullable: false
    facebookToken nullable: false
  }

  Profile toProfile() {
    def locale = locale ?: grailsApplication.config.winbits.default.locale
    new Profile(name: name, lastName: lastName, birthdate: birthdate, gender: gender, locale: locale)
  }
}

class ProfileCommand {
  String name
  String lastName
  LocalDate birthdate
  Gender gender
  String zipCode
  String location
  String phone
  ZipCodeInfo zipCodeInfo

  void updateProfile(Profile profile) {
    profile.phone = phone
    profile.birthdate = birthdate
    profile.gender = gender
    profile.lastName = lastName
    profile.name = name
    profile.zipCode = zipCodeInfo?.zipCode ?: zipCode
    profile.location = zipCodeInfo?.location ?: location
    profile.zipCodeInfo = zipCodeInfo
  }

}

class LoginCommand {
  String email
  String password
  Map<String, String> utms

  static constrains = {
    email nullable: false, email: true
    password nullable: false
  }
}

class UpdateSubscriptionsCommand {
  NewsletterFormat newsletterFormat
  NewsletterPeriodicity newsletterPeriodicity
//  List<SubscriptionCommand> subscriptions

  void updateProfile(Profile profile) {
    if (newsletterFormat) {
      profile.newsletterFormat = newsletterFormat
    }
    if (newsletterPeriodicity) {
      profile.newsletterPeriodicity = newsletterPeriodicity
    }
  }
}

class ChangePasswordCommand {
  String password
  String newPassword
  String passwordConfirm

  static constraints = {
    password()
    newPassword nullable: false, blank: false
    passwordConfirm nullable: false, blank: false, validator: { val, obj ->
      obj.newPassword == val
    }
  }
}

class ExpressLoginCommand {
  String apiToken
  boolean firstLogin
  Map<String, String> utms

  static constraints = {
    apiToken nullable: false, blank: false
    firstLogin nullable: true
  }
}
