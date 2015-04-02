package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.User
import grails.converters.JSON
import grails.plugin.spock.IntegrationSpec
import spock.lang.Shared
import wslite.rest.ContentType

class AffiliationControllerIntegrationSpec extends IntegrationSpec {

  def authenticationService
  def profileService
  def redisService

  private AffiliationController controller

  @Shared
  User testUser

  def setupSpec() {
    testUser = User.get(1)
  }

	def setup() {
    controller = new AffiliationController()
    controller.authenticationService = authenticationService
    controller.profileService = profileService
	}

	def cleanup() {
	}

	void "login with api token updated UTMs"() {
    given: "a valid request with UTMs"
    def utms = [campaign: 'Newsletter', medium: 'email']
    controller.params['apiToken'] = testUser.apiToken
    controller.params['utms'] = utms

    and: "ensure utms are different"
    redisService.hset(testUser.apiToken, 'utms', 'fake-utms-which-should-be-replaced')

    when: "perform action"
    controller.loginApiToken()

    then: "the UTMs are updated"
    redisService.hget(testUser.apiToken, 'utms') == (utms as JSON).toString()
	}
}