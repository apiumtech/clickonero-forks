package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.EmailNotFoundException
import com.winbits.api.clients.mailing.MailingClient
import com.winbits.api.clients.mis.MisClient
import com.winbits.api.clients.mis.affiliation.MisLogin
import com.winbits.api.clients.mis.affiliation.MisRecoverPassword
import com.winbits.domain.affiliation.AuthenticationService
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.UserService
import grails.buildtestdata.mixin.Build
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LoginService)
@Mock(User)
@Build(User)
@TestMixin(GrailsUnitTestMixin)
class LoginServiceSpec extends Specification {
  private User user
  private String tokenOK
  private String tokenNoOK
  private MisClient misClient
  private String salt

  def setup() {
    tokenOK = 'ABCDE'
    tokenNoOK = 'XYZ'
    User.metaClass.encodePassword {->
    }
    User.metaClass.generateApiToken {->
      apiToken = 'asdasdadsasd'
      apiToken
    }
    salt = 'fdsfdsfdsf467gb'
    user = User.build(email: "user1@mail.com", password: "12345", enabled: true, accountLocked: false, salt: salt)
    User.build(email: "user2@mail.com", password: "12345", enabled: true, accountLocked: false)
    def userService = Spy(UserService)
    service.userService = userService

    def mailingClient = Mock(MailingClient)
    service.mailingClient = mailingClient
    misClient = Mock(MisClient)
    service.misClient = misClient
    service.grailsLinkGenerator = Mock(LinkGenerator)
  }

  void "test login facebook"() {
    setup:
    def facebookToken = 'fdsgdfer45'
    def user = User.build(facebookToken: facebookToken)
    def authenticateService = Mock(AuthenticationService)
    service.authenticationService = authenticateService

    when:
    def result = service.loginFacebookToken(facebookToken)

    then:
    user == result
    1 * authenticateService.isAuthenticated(user) >> true
  }

  void "test send  Mail liga reset Password"() {
    setup:
    def emailOk = "user1@mail.com"
    def authenticateService = Mock(AuthenticationService)
    service.authenticationService = authenticateService

    when:
    service.recover(emailOk, 1)

    then:
    1 * misClient.logMessage(_ as MisRecoverPassword)
  }


  void "test send No existe Mail liga reset Password"() {
    when:
    def emailNoOk = "user323@mail.com"
    service.recover(emailNoOk, 1)
    then:
    thrown(EmailNotFoundException)
  }

  void "test migrate user when user is not enabled"(){
    setup:
    def email = 'test-migration@email.com'
    def password = '12345'
    User user = User.build(email: email, password: password, enabled: true, accountLocked: false, salt: '')
    
    and:
    MultipleLoginService multipleLoginService = Mock()
    service.multipleLoginService = multipleLoginService
    and:
    def authenticationService = Mock(AuthenticationService)
    service.authenticationService = authenticationService
    when:
    service.recover(email,1)
    then:
    1 * service.authenticationService.createCRCintoRedis() >> '1234'
  }

  void "test reset Password"() {
    setup:
    def newPassword = "54321"

    when:
    def res = service.updatePassword(salt, newPassword)

    then:
    res == user
    1 * misClient.logMessage(_ as MisRecoverPassword)
  }

  void testLogin() {
    setup:
    def authenticationManager = Mock(AuthenticationManager)
    service.authenticationManager = authenticationManager
    def authenticationService = Mock(AuthenticationService)
    user.authenticationService = authenticationService

    when:
    def result = service.login(user.email, user.password)

    then:
    user == result
    1 * authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken)
    1 * misClient.logMessage(_ as MisLogin)
  }


}
