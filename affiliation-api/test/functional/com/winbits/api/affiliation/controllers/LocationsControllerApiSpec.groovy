package com.winbits.api.affiliation.controllers

import com.grailsrocks.functionaltest.*
import com.winbits.grest.test.spock.ApiSpecification
import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class LocationsControllerApiSpec extends ApiSpecification {

  final String resource = '/locations.json'

  def "list locations"() {
    given: "an existing zip code"
    def zipCode = "55130"

    when: "request to get zip code's locations"
    def itemResource = getItemResource(zipCode)
    get(path: itemResource)

    then: 'response is well-formed'
    assertStatus 200
    assertJsonArray()
    jsonResponse.size()
    jsonResponse.every {
      assertZipCodeResponse(it)
    }
  }

  private boolean assertZipCodeResponse(zipCodeInfoResponse) {
    def expectedFields = ['id', 'locationName', 'locationCode', 'locationType', 'county', 'city', 'state', 'zipCode']
    assert zipCodeInfoResponse.size() == expectedFields.size()
    expectedFields.each {
      assert zipCodeInfoResponse.containsKey(it)
    }
    true
  }
}
