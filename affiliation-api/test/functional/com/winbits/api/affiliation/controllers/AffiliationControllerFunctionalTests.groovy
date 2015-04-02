package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.SubscriptionCommand
import com.winbits.domain.affiliation.NewsletterPeriodicity
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON

class AffiliationControllerFunctionalTests extends FunctionalTestCase {

  private static final String TEST_API_TOKEN = 'W1nb1tsT3st'

  private static final String CONTENT_TYPE_HEADER_NAME = 'Content-Type'
  private static final String JSON_CONTENT_TYPE = 'application/json'

  void testGetAccounts() {
    get('/social-accounts') {
      headers[CONTENT_TYPE_HEADER_NAME] = JSON_CONTENT_TYPE
      headers.'WB-API-TOKEN' = TEST_API_TOKEN
      redirectEnabled = false
    }
    assertStatus 200
  }

  void testBitsAccount() {
    get('/bitsAccount/1') {
      headers[CONTENT_TYPE_HEADER_NAME] = JSON_CONTENT_TYPE
      headers.'WB-API-TOKEN' = 'ABC'
      redirectEnabled = false
    }
    assertStatus 200
  }

  void testSubscriptions() {
    get('/subscriptions') {
      headers.'WB-API-TOKEN' = TEST_API_TOKEN
      redirectEnabled = false
    }
    assertStatus 200
  }

  void testUpdateSubscriptions() {
    def subscriptions = [new SubscriptionCommand(id: 1, active: true), new SubscriptionCommand(id: 2, active: false)]
    def command = [newsletterPeriodicity: NewsletterPeriodicity.daily, subscriptions: subscriptions]
    put('/subscriptions') {
      headers['WB-API-TOKEN'] = TEST_API_TOKEN
      headers[CONTENT_TYPE_HEADER_NAME] = JSON_CONTENT_TYPE
      body {
        command as JSON
      }
      redirectEnabled = false
    }
    assertStatus 200
  }

  void testResendConfirmationMail() {
    def email = 'me@work.com'
    def user = User.findByEmail(email)
    user = user ?: new User(email: email, password: '3344',
        vertical: Vertical.get(1), apiToken: 'gdfg', referrerCode: 'fsdfew').save()

    get("resend/${user.email}")
    assertStatus 200
  }
}
