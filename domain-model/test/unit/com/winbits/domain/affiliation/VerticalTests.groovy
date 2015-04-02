package com.winbits.domain.affiliation

import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Vertical)
class VerticalTests {

  void testSubscriptionMap() {
    def vertical = new Vertical(name: 'verti')
    vertical.id = 56
    def subscription = new Subscription(vertical: vertical)
    def subscriptions = [subscription]
    def subscriptionMap = vertical.toSubscriptionMap(subscriptions)

    assert subscriptionMap
    assert subscriptionMap.id == vertical.id
    assert subscriptionMap.name == vertical.name
    assert subscriptionMap.active == true
  }
}
