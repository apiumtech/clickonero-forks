package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.UserBadCredentialsException
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.messaging.MessagingService
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import groovy.mock.interceptor.Ignore
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MultipleLoginService)
@Mock([User])
@Build([User,Profile])
@TestMixin(GrailsUnitTestMixin)
class MultipleLoginServiceSpec extends Specification {

  def setup() {
    User.metaClass.encodePassword {->
    }
    User.metaClass.generateApiToken {->
      apiToken = 'asdasdadsasd'
      apiToken
    }
    service.bebitosService = Mock(BebitosService)
    service.loginService = Mock(LoginService)
    service.registerService = Mock(RegisterService)
    service.messagingService = Mock(MessagingService)
    service.migrationService = Mock(MigrationService)
  }

  def cleanup() {
  }

  void "test login by winbits"() {
    setup:
    def email = 'winbits_partner_dsds321@mail.com'
    def password = '123456789'
    User.build(email: email, password: password, enabled: true, accountLocked: false, salt: '23123')

    when:
    service.login(email, password)

    then:
    0 * service.bebitosService.migrateUser(email, password)
    1 * service.loginService.login(email, password)
    0 * service.registerService.activate(_)
  }



  void "should migrate user"() {
    setup:
    def email = 'winbits_partner_dsds321@mail.com'
    def password = '123456789'
    User.build(email: email, password: password, enabled: false, accountLocked: false, salt: '')

    when:
    service.login(email, password)

    then:
    0 * service.bebitosService.migrateUser(email, password)
    1 * service.loginService.login(email, password)
    0 * service.migrateHistoryFromBebitosifIsNecesary(_)
    //1 * service.migrationService.obtainProfile(_)*/
  }


  void "test login bebitos user"() {
    setup:
    def email = 'rene_hernandez_sanchez@hotmail.com'
    def password = 'passw2'
    def dummyUser = new User(email: email, password: password, enabled: true, accountLocked: false, salt: '2okoko3123')

    service.bebitosService = Stub(BebitosService) { migrateUser(email, password) >> dummyUser }
    service.registerService = Stub(RegisterService) { activate(_) >> dummyUser }

    when:
    service.login(email, password)

    then:
    1 * service.loginService.login(email, password)
    1 * service.messagingService.publishMessage("migrateUserFromPartner", _)
  }



  void "test login fail"() {
    setup:
    def email = 'unknown_user_123456a@mail.com'
    def password = 'dfgdg68dsfdsf'

    when:
    service.login(email, password)

    then:
    thrown(UserBadCredentialsException)
    1 * service.bebitosService.migrateUser(email, password)
    0 * service.loginService.login(email, password)
    0 * service.messagingService.publishMessage("migrateUserFromPartner", _)
  }

  void "needes migrate user" () {
  setup:
    User user = new User(salt: salt, enabled:enabled).save (validate:false)
  when:
    def responseService = service.isDontNeedMigrate(user)
  then:
    responseService == response
  where:
  response      |       salt        |      enabled
  true          |       ''          |       true
  true          |       '123121'    |       false
  false         |       ''          |       false
  true          |       '1231'      |       true
  }
}
