package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.UserService
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
@Mock(User)
@Build(User)
class UserServiceSpec extends Specification {

  def setup() {
  }

  void "test find by token"() {
    setup:
    User.metaClass.encodePassword {->
    }
    def apiToken = 'asdf456'
    def user = User.build(apiToken: apiToken)

    when:
    def foundUser = service.byToken(apiToken)

    then:
    user == foundUser
  }
}
