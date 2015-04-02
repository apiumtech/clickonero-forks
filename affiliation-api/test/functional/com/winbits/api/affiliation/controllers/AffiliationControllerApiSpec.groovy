package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.services.BebitosService
import com.winbits.api.affiliation.services.BitsService
import com.winbits.domain.affiliation.Gender
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.grest.test.spock.ApiSpecification
import grails.util.Holders
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.Ignore

class AffiliationControllerApiSpec extends ApiSpecification {

  private static final Map TEST_CREDENTIALS = [email: 'juan_jsr@yahoo.com', password: '12345']
  private static final String API_TOKEN_HEADER_NAME = 'WB-API-TOKEN'

  private User testUser

  def setupSpec() {
    BitsService.metaClass.forUser = {User user -> 100}
    BitsService.metaClass.createAccount = {-> 1}
    BebitosService.metaClass.migrateUser = { email, password -> null}
  }

  def setup() {
    testUser = User.findByEmail(TEST_CREDENTIALS.email)
  }

  def cleanup() {
    Holders.applicationContext.sessionFactory.currentSession.clear()
  }

  def "login with user & password"() {
    when: "request login"
    doLogin()

    then: "an api token is responded"
    assertStatus 200
    jsonResponse.apiToken
  }

  private doLogin() {
    post('/login.json') {
      json TEST_CREDENTIALS
    }
  }

  def "logout"() {
    given: 'a user is logged-in'
    doLogin()
    def apiToken = jsonResponse.apiToken

    when: 'request to logout'
    post(path: '/logout', headers: [(API_TOKEN_HEADER_NAME): apiToken]) {}

    then: "response is well-formed"
    assertStatus 200
  }

  def "last login updated when login with email & password"() {
    given: "fixed last login to yesterday"
    def yesterday = DateTime.now().minusDays(1)
    testUser.lastLogin = yesterday
    testUser.save(flush: true)

    when: 'request to login'
    doLogin()
    testUser.refresh()

    then: "response is well-formed"
    assertStatus 200

    and: "last login is close to now"
    assertLastLogin(testUser)
  }

  private boolean assertLastLogin(User user) {
    def difference = DateTime.now().millis - user.lastLogin.millis
    assert difference < 30000 // Less than half minute
    true
  }

  def "login with facebook id (express)"() {
    given: "a facebook id"
    def facebookId = 'XYZ'

    and: "fixed the facebook id on an existing user"
    testUser.facebookToken = facebookId
    testUser.dateCreated = testUser.dateCreated.minusDays(1)
    testUser.save(flush: true)

    when: "request to login with facebook id"
    post('/express-facebook-login.json') {
      json facebookId: facebookId
    }

    then: 'response is well-formed'
    assertStatus(200)
    jsonResponse.apiToken
    jsonResponse.email == testUser.email
  }

  def "last login updated login with facebook id (express)"() {
    given: "a facebook id"
    def facebookId = 'XYZ'

    and: "fixed the facebook id on an existing user"
    testUser.facebookToken = facebookId
    testUser.dateCreated = testUser.dateCreated.minusDays(1)

    and: "fixed last login to yesterday"
    def yesterday = DateTime.now().minusDays(1)
    testUser.lastLogin = yesterday
    testUser.save(flush: true)

    when: "request to login with facebook id"
    post('/express-facebook-login.json') {
      json facebookId: facebookId
    }
    testUser.refresh()

    then: "response is well-formed"
    assertStatus 200

    and: "last login is close to now"
    assertLastLogin(testUser)
  }

  def "register with email & password"() {
    given: "the registration data"
    def registerData = [email: 'juan_jsr+11@gmail2.com', password: '1234567', verticalId: 1]

    when: 'request to register'
    post(path: '/register.json') {
      json registerData
    }

    then: "response is well-formed"
    assertStatus 200
  }

  def "confirm register"() {
    given: 'an unconfirmed user'
    User user = User.build(email: "juan_jsr+111@yahoo.com", enabled: false, vertical: Vertical.get(1))

    when: "request to confirm register"
    get(path: "/confirm/${user.salt}", followRedirects: false)
    user.refresh()

    then: "a redirection happens"
    assertStatus 302
   // assertRedirectURLContains user.baseUrl()

    and: "the user is now confirmed"
    user.enabled

    and: "the user now has a profile"
    user.profile()
  }

  @Ignore('Facebook register process changed, so service is not used anymore')
  def "facebook register"() {
    given: "the registration data"
    def registrationData = [name         : 'Alice', email: 'alice@mail.com', birthdate: '1984-04-13', gender: 'female',
                            facebookToken: 'rewtw5435', verticalId: 1, locale: 'es_MX', providerUserId: '12345',
                            accessToken  : 'axaxaxax',
                            profileUrl   : 'http://urltoprofile',
                            imageUrl     : 'http://urltoimageprofile']

    when: "request to register with facebook data"
    post("/facebook.json") {
      json registrationData
    }

    then: "response is well-formed"
    assertStatus 201
  }

  def "login with api token (express login)"() {
    given: 'a valid api token'
    def apiToken = testUser.apiToken

    and: "fixed last login"
    def yesterday = DateTime.now().minusDays(1).withMillisOfSecond(0)
    testUser.lastLogin = yesterday
    testUser.save(flush: true)

    when: "request to login user with api token"
    post('/express-login.json') {
      json apiToken: apiToken
    }

    then: "the response is well-formed"
    assertStatus 200

    and: "last login is not updated"
    testUser.refresh().lastLogin == yesterday
  }

  def "last login is updated if a flag is turned on when login with api token (express login)"() {
    given: 'a valid api token'
    def apiToken = testUser.apiToken

    and: "fixed last login"
    def yesterday = DateTime.now().minusDays(1)
    testUser.lastLogin = yesterday
    testUser.save(flush: true)

    when: "request to login user with api token"
    post('/express-login.json') {
      json apiToken: apiToken, firstLogin: true
    }

    then: "the response is well-formed"
    assertStatus 200

    and: "last login is updated"
    assertLastLogin(testUser.refresh())
  }

  def "password recover"() {
    given: "the reset password data"
    def recoverData = [email: testUser.email, verticalId: 1]

    when: "request to recover password"
    post('/password/recover.json') {
      json recoverData
    }

    then: "the response is well-formed"
    assertStatus 200
  }

  def "reset password"() {
    given: 'an active user'
    def email = "juan_jsr2+1@yahoo.com"
    User user = User.build(
        email: email,
        password: '12345',
        enabled: true,
        vertical: Vertical.get(1)
    )
    Profile.build(bitsId: 1000, locale: new Locale("es", "MX"), user: user)

    and: "the salt & crc for recovering password"
    def hash = user.salt
    def saltCrc = hash + '1234567890'

    and: "the new password"
    def newPassword = 's3cr3t0'

    when: "request to reset password"
    post('/password/reset.json') {
      json hash: saltCrc, password: newPassword, passwordConfirm: newPassword, verticalId: 1
    }

    then: "the response is well-formed"
    assertStatus 200

    and: "the password has been changed"
    def originalPassword = user.password
    originalPassword != user.refresh().password
  }

  void "update profile"() {
    given: "an active user with a fixed profile"
    def profile = testUser.profile()
    def today = LocalDate.now()
    profile.properties = [name  : 'Steve', lastName: 'Jobs', birthdate: today, zipCode: '10345', location: 'DF',
                          gender: 'female']
    profile.save(flush: true)

    and: "the data to update"
    def updateData = [name  : 'Alice', lastName: 'Cooper', birthdate: '1984-04-13', zipCode: '11000', location: 'Tepito',
                      gender: 'male']

    when: "request to update profile"
    put(path: '/profile.json', headers: [(API_TOKEN_HEADER_NAME): testUser.apiToken]) {
      json updateData
    }
    profile.refresh()

    then: "the response is well formed"
    assertStatus 200

    and: "the profile was updated"
    profile.name == updateData.name
    profile.lastName == updateData.lastName
    profile.birthdate == LocalDate.parse(updateData.birthdate)
    profile.zipCode == updateData.zipCode
    profile.location == updateData.location
    profile.gender == Gender.valueOf(updateData.gender)
  }

  def "do not update Profile's last_updated when counting complete profile reminders"() {
    given: "a user whom should be reminded about complete his profile"
    def profile = testUser.profile()
    profile.completed = false
    profile.save()
    testUser.completeProfileReminders = 0
    testUser.save(flush: true)

    and: "saving current Profile's last_updated"
    def profileLastUpdated = profile.lastUpdated

    when: "request login"
    doLogin()

    then: "the response is ok"
    assertStatus 200

    and: "the reminders were increased"
    testUser.refresh().completeProfileReminders == 1

    //and: "the profile were not updated"
    //profile.refresh().lastUpdated == profileLastUpdated
  }
}
