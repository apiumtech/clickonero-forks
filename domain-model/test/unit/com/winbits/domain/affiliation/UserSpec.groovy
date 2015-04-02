package com.winbits.domain.affiliation

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
@Mock([User, Profile])
class UserSpec extends Specification {

  def setup() {
    User.metaClass.encodePassword = {->}
  }

  void testConfirmUrl() {
    setup:
    def user = new User(salt: 'aetrer')
    def mockConfig = new ConfigObject()
    mockConfig.api.winbits.baseUrl = 'api.winbits.com'
    user.grailsApplication = [config: mockConfig]

    when:
    def confirmUrl = user.confirmUrl()

    then:
    confirmUrl
    confirmUrl.contains(user.salt)
  }

  void testProfileMap() {
    setup:
    def user = new User()

    when:
    def map = user.profileMap()

    then:
    !map
  }

  void "profile map with profile"() {
    setup:
    def user = new User().save(false)
    def profile = new Profile(name: 'Ana', user: user).save(false)

    when:
    def result = user.profileMap()

    then:
    profile.toMap() == result
  }

  void "test Vertical name"() {
    setup:
    def vertical = new Vertical(name: 'clickonero')
    def user = new User(vertical: vertical)

    when:
    def result = user.verticalName()

    then:
    vertical.name == result
  }

  void "test event base"() {
    setup:
    def vertical = new Vertical(name: 'clickonero')
    def user = new User(vertical: vertical, email: 'probando@test.com')
    user.id = 77

    when:
    def result = user.toEventBase()

    then:
    result
    user.email == result.userName
    user.id == result.userId
    def resultDate = new LocalDate(result.date)
    resultDate == LocalDate.now()
    result.vertical == vertical.name
  }
}
