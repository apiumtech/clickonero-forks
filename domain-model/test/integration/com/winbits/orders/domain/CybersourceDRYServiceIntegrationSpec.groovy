package com.winbits.orders.domain

import com.winbits.domain.affiliation.User
import com.winbits.domain.orders.CardSubscription

import grails.plugin.spock.IntegrationSpec

import groovy.mock.interceptor.Ignore

import spock.lang.Ignore

class CybersourceDRYServiceIntegrationSpec extends IntegrationSpec {
  
  def cybersourceDRYService 

	def setup() {
	}

	def cleanup() {
	}

  @Ignore
	void "should return a map with cardInfo"() {
  setup:
  List subscriptions = []
  User.metaClass.encodePassword = {-> }
  User user = User.build (email:'mailsubscription@mail.com')
  CardSubscription cardSubscription = new CardSubscription (subscriptionId:'9997000108694932', user:user)
  subscriptions << cardSubscription
  when:
    def response = cybersourceDRYService.obtainCardSubscriptions (subscriptions)
  then:
    assert response
	}
}
