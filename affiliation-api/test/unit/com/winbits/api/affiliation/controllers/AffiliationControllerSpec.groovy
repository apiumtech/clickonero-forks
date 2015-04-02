package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.services.*
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.UserService
import com.winbits.domain.tracking.TrackingStep
import com.winbits.domain.tracking.TrackingStepEnum
import com.winbits.domain.utils.DomainModelUtils
import com.winbits.exceptions.api.client.EntityValidationException
import grails.buildtestdata.mixin.Build
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Build(User)
@Mock([ShippingAddress, TrackingStep])
@TestFor(AffiliationController)
class AffiliationControllerSpec extends Specification {

  private LoginCommand command
  private RecoverCommand commandRecover
  private ResetCommand commandReset

  private userService
  private subscriptionService
  private profileService
  private bitsService
  private socialConnectService
  private User user
  private String apiToken
  private migrationService

  def response

  def setup() {
    command = new LoginCommand(email: 'juan@work.com', password: '12345')
    userService = Mock(UserService)
    subscriptionService = Mock(SubscriptionService)
    bitsService = Mock(BitsService)
    socialConnectService = Mock(SocialConnectService)
    profileService = Mock(ProfileService)
    migrationService = Mock(MigrationService)
    controller.userService = userService
    controller.subscriptionService = subscriptionService
    controller.profileService = profileService
    controller.bitsService = bitsService
    controller.socialConnectService = socialConnectService
    controller.migrationService = migrationService
    controller.metaClass.getAuthenticatedUser {->
      user
    }
    controller.metaClass.restpond { response ->
      this.response = response
    }

    apiToken = 'asdafr67'
    user = new User(apiToken: apiToken, profile: new Profile())

    DomainModelUtils.createPersistentEnumModel(TrackingStepEnum)
  }

  void "test login"() {
    setup:
    def multipleLoginService = Mock(MultipleLoginService)
    controller.multipleLoginService = multipleLoginService
    def mockConfig = new ConfigObject()
    mockConfig.api.name = 'wb-api'
    def trackingService = Mock(TrackingService)
    controller.trackingService = trackingService
    User.metaClass.isMigrateUser = false
    when:
    controller.login(command)

    then:
    1 * multipleLoginService.login(command.email, command.password) >> user
    1 * profileService.toProfileData(_) >> [apiToken: user.apiToken]
    apiToken == response.apiToken
  }

  void "test send Password"() {
    setup:
    User.metaClass.encodePassword {->}
    def loginService = Mock(LoginService)
    controller.loginService = loginService
    commandRecover = new RecoverCommand(email: 'juan@mail.com', verticalId: 1)
    when:
    controller.recover(commandRecover)
    then:
    1 * controller.migrationService.isUserForMigration(_) >> false
    1 * controller.loginService.recover(commandRecover.email, commandRecover.verticalId) >> "Ok"
    assert response.message == "Ok"
  }
  
  void "test send Password and migrate user"() {
    setup:
    User.metaClass.encodePassword {->}
    def loginService = Mock(LoginService)
    def multipleLoginService = Mock(MultipleLoginService)
    controller.multipleLoginService = multipleLoginService
    controller.loginService = loginService
    commandRecover = new RecoverCommand(email: 'juan@mail.com', verticalId: 1)
    when:
    controller.recover(commandRecover)
    then:
    1 * controller.migrationService.isUserForMigration(_) >> true
    1 * controller.migrationService.obtainUserByEmail(_) >> new User(email:'email@mail.com')
    1 * controller.multipleLoginService.migrateClickoneroUser(_)
    1 * controller.migrationService.createVerticalPartner(_)
    1 * controller.migrationService.migrateUserSendMessage(_)
    1 * controller.migrationService.generateSaltAndRandomPassword(_)
    1 * controller.loginService.recover(commandRecover.email, commandRecover.verticalId) >> "Ok"
    assert response.message == "Ok"
  }

    void "test send Password and migrate user from bebitos"() {
        setup:
        User.metaClass.encodePassword {->}
        def loginService = Mock(LoginService)
        def multipleLoginService = Mock(MultipleLoginService)
        controller.multipleLoginService = multipleLoginService


        controller.loginService = loginService
        commandRecover = new RecoverCommand(email: 'juan@mail.com', verticalId: 1)
        when:
        controller.recover(commandRecover)
        then:
        1 * controller.migrationService.obtainUserByEmail(_) >> new User(email:'email@mail.com')
        //1 * controller.multipleLoginService.migrateBebitosUserByEmail(_)
        1 * controller.migrationService.isUserForMigration(_) >> false


        1 * controller.loginService.recover(commandRecover.email, commandRecover.verticalId) >> "Ok"
        assert response.message == "Ok"
    }



  void "test regenerate salt"() {
    setup:
    User.metaClass.encodePassword {->}
    def loginService = Mock(LoginService)
    def multipleLoginService = Mock(MultipleLoginService)
    controller.multipleLoginService = multipleLoginService
    controller.loginService = loginService
    commandRecover = new RecoverCommand(email: 'juan@mail.com', verticalId: 1)
    when:
    controller.recover(commandRecover)
    then:
    1 * controller.migrationService.isUserForMigration(_) >> false
    1 * controller.migrationService.obtainUserByEmail(_) >> new User(email:'email@mail.com', enabled: true)
    0 * controller.multipleLoginService.migrateClickoneroUser(_)
    0 * controller.migrationService.createVerticalPartner(_)
    0 * controller.migrationService.migrateUserSendMessage(_)
    1 * controller.migrationService.isUserOnlyWithSalt(_) >> true
    1 * controller.migrationService.generateSaltAndRandomPassword(_)
    1 * controller.loginService.recover(commandRecover.email, commandRecover.verticalId) >> "Ok"
    assert response.message == "Ok"
  }

  void "test reset Password"() {
    setup:
    def springSecurityServiceFactory = mockFor(SpringSecurityService, true)
    def springSecurityService = springSecurityServiceFactory.createMock()
    def loginService = Mock(LoginService)
    controller.loginService = loginService
    controller.loginService.springSecurityService = springSecurityService
    user = new User(email: "user@mail.com")
    commandReset = new ResetCommand(hash: user.salt, password: "54321", passwordConfirm: "54321", verticalId: 1)
  }

  void "test should throws an error in login with facebook token"(){
  given:
    List errors = []
    errors.metaClass.rejectValue = {param1, param2, param3, param4 ->}
    controller.metaClass.errors = errors
  when:
    controller.loginFacebookToken('')
  then:
    thrown(EntityValidationException)
  }

  void "test should login with facebook"(){
  given:
    LoginService loginService = Mock()
    controller.loginService = loginService
  and:
    RegisterService registerService = Mock()
    registerService.determineStatus(_) >> 200
    controller.registerService = registerService
  and:
    TrackingService trackingService = Mock()
    controller.trackingService = trackingService
  and:
    controller.metaClass.restpond(_) >> [data:[:], status:200]
  when:
    controller.loginFacebookToken('tokenF$@c3book')
  then:
    notThrown(EntityValidationException)
    0 * controller.migrationService.createVerticalPartner(_)
    1 * controller.loginService.loginFacebookToken(_) >> new User()
    1 * controller.profileService.toProfileData(_) >> [:]
    1 * controller.profileService.mustShowCompleteProfileRemainder(_) >> [:]
    0 * controller.migrationService.updateFacebookProfile(_)
    0 * controller.migrationService.migrateUserSendMessage(_)
    1 * controller.trackingService.trackLogin(_, _) 
  }

  @Ignore('//TODO mejoras de migración')
  void "test should migrate user info login with facebook"(){
  given:
    LoginService loginService = Mock()
    controller.loginService = loginService
    user.salt = ''
  and:
    RegisterService registerService = Mock()
    registerService.determineStatus(_) >> 200
    controller.registerService = registerService
  and:
    TrackingService trackingService = Mock()
    controller.trackingService = trackingService
  and:
    controller.metaClass.restpond(_) >> [data:[:], status:200]
  when:
    controller.loginFacebookToken('tokenF$@c3book')
  then:
    notThrown(EntityValidationException)
    1 * controller.loginService.loginFacebookToken(_) >> new User(salt:'')
    1 * controller.profileService.toProfileData(_) >> [:]
    1 * controller.profileService.mustShowCompleteProfileRemainder(_) >> [:]
    1 * controller.migrationService.updateFacebookProfile(_)
    1 * controller.migrationService.migrateUserSendMessage(_)
    1 * controller.migrationService.createVerticalPartner(_)
    1 * controller.trackingService.trackLogin(_, _) 
  }
}
