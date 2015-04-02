package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.*
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SubscriptionService)
@Mock([User, Profile])
class SubscriptionServiceSpec extends Specification {

  def setup() {
    User.metaClass.encodePassword = {->}
  }

  void "test get subscriptions"() {
    setup:
    def user = new User().save(false)
    def profile = new Profile(user: user).save(false)
    def vertical = new Vertical(name: 'Clickonero')
    def subscripcion = new Subscription(vertical: vertical)
    profile.subscriptions = [subscripcion]
    def verticalService = Mock(VerticalService)
    service.verticalService = verticalService

    when:
    def result = service.getSubscriptions(user)

    then:
    result
    1 == result.size()
    vertical.name == result.first().name
    1 * verticalService.getAll() >> [vertical]
  }
}
