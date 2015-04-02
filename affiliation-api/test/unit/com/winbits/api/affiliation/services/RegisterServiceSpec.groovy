package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.FacebookCommand
import com.winbits.api.affiliation.controllers.RegisterCommand
import com.winbits.api.affiliation.exception.UserAlreadyConfirmedException
import com.winbits.api.clients.mailing.MailingClient
import com.winbits.api.clients.mailing.data.ConfirmAccountData
import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.api.clients.mis.MisClient
import com.winbits.domain.affiliation.*
import com.winbits.exceptions.api.client.EntityNotExistsException
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.plugins.codecs.Base64Codec
import org.joda.time.LocalDate
import spock.lang.Specification

@TestFor(RegisterService)
@Mock([User, Profile])
@Build(User)
class RegisterServiceSpec extends Specification {

  def verticalService
  def bitsService

  private MailingClient mailingClient
  private String salt
  private User user
  private String email
  private String facebookToken

  def setup() {
    mockCodec(Base64Codec)
    User.metaClass.encodePassword {->
    }
    User.metaClass.confirmUrl {->
      'api.winbits'
    }
    User.metaClass.generateApiToken {->
      apiToken = 'asdasdadsasd'
      apiToken
    }
    salt = 'fdsgdr56'
    email = 'me@home.com'
    facebookToken = '56565gg'
    user = User.build(salt: salt, password: '12345', email: email, facebookToken: facebookToken)
    def authenticationService = Mock(AuthenticationService)
    user.authenticationService  = authenticationService
    verticalService = Mock(VerticalService)
    service.verticalService = verticalService
    bitsService = Mock(BitsService)
    service.bitsService = bitsService
    mailingClient = Mock(MailingClient)
    service.mailingClient = mailingClient
  }

  def testRegister() {
    setup:
    def email = 'me@work.com'
    def password = '12345'
    def command = new RegisterCommand(email: email, password: password, verticalId: 1, referredBy: 'dsfsf4545')
    def vertical = new Vertical()

    when:
    def user = service.register(command)

    then:
    1 * verticalService.byId(1) >> vertical
    user
    user.email == email
    user.vertical == vertical
    user.referrerCode
    command.referredBy == user.referredBy
    1 * mailingClient.sendEmail(_ as ConfirmAccountData)
  }

  def activate() {
    when:
    def result = service.activate(salt)
    then:
    result == user
    result.enabled
    user.profile().bitsId
    bitsService.createAccount() >> 23
  }

  def "should activate user withoutsalt"() {
  setup:
    bitsService.createAccount() >> 23
  when:
    def result = service.activateUserWithoutSalt(user)
  then:
    assert result
    assert result == user
  }


  def "resend Confirmation Mail with exception"() {
    when:
    service.resendConfirmationMail('me@work.com')

    then:
    thrown(EntityNotExistsException)
  }

  def "resend Confirmation Mail with already confirmed exception"() {
    setup:
    if (!user.respondsTo('isDirty')) {
      user.metaClass.isDirty = { String field -> }
    }
    user.enabled = true
    user.save()

    when:
    service.resendConfirmationMail(email)

    then:
    thrown(UserAlreadyConfirmedException)
  }

  def resendConfirmationMail() {
    when:
    service.resendConfirmationMail(email)

    then:
    1 * mailingClient.sendEmail(_ as ConfirmAccountData)
  }

  def facebookResponse() {
    setup:
    def command = new FacebookCommand(facebookToken: facebookToken, locale: new Locale("es", "MX"), email: user.email, providerUserId: '12345')
    def apiToken = user.apiToken
    def userResult
    def existing
    def misClient = Mock(MisClient)
    service.misClient = misClient

    when:
    (userResult, existing) = service.facebookLogin(command)

    then:
    user == userResult
    existing
    apiToken != userResult.apiToken
    1 * misClient.logMessage({ it.userName == user.email })
  }

  def facebookRegister() {
    setup:
    def command = new FacebookCommand(email: 'me@facebook.com', verticalId: 1, name: 'Pedro', lastName: 'Paramo',
        birthdate: new LocalDate(), gender: Gender.male, locale: Locale.US, facebookToken: 'fsdf435')
    def user
    def existing

    when:
    (user, existing) = service.facebookLogin(command)

    then:
    !existing
    bitsService.createAccount() >> 23
    user
    user.enabled
    user.email == command.email
    def profile = user.profile()
    profile
    command.name == profile.name
    command.birthdate == profile.birthdate
    profile.gender
    profile.locale
    command.providerUserId == user.facebookToken
    verticalService.byId(command.verticalId) >> new Vertical()
    1 * mailingClient.sendEmail(_ as ConfirmWelcomeData)
  }

}
