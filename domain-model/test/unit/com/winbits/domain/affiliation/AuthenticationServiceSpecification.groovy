package com.winbits.domain.affiliation

import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.plugin.redis.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AuthenticationService)
class AuthenticationServiceSpecification extends Specification {

  void "test login api token"() {
    def apiToken = 'fdsfv34'
    def userService = Mock(UserService)
    def redisService = Mock(RedisService)
    service.redisService = redisService
    service.userService = userService
    def springSecurityService = Mock(SpringSecurityService)
    service.springSecurityService = springSecurityService
    def user = new User(email: 'me@work.com')

    when:
    def result = service.loginApiToken(apiToken)

    then:
    result
    1 * userService.byHashRedis(null) >> user
  }
}
