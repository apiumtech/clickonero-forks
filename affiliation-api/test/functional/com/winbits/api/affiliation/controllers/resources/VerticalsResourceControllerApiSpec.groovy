package com.winbits.api.affiliation.controllers.resources

import com.winbits.domain.affiliation.Vertical
import com.winbits.grest.test.spock.ApiSpecification

class VerticalsResourceControllerApiSpec extends ApiSpecification {

  final String resource = '/verticals.json'

  def "GET /verticals works as expected"() {
    given: "An active Vertical hostname"
    def hostname = "dev.mylooq.com"

    when: "GET /verticals"
    get(query: [hostname: hostname])

    then: "All active verticals are returned"
    assertStatus 200
    assertJsonArray()
    jsonResponse.length

    and: "response include a vertical with the active hostname"
    def activeVertical = jsonResponse.find {verticalResponse ->
      verticalResponse.baseUrl.contains(hostname)
    }

    and: "response's meta contains a 'currentVerticalId' property"
    def currentVerticalId = json.meta.currentVerticalId

    and: "that's equal to activeVertical's id"
    activeVertical.id == currentVerticalId
  }

  def "GET /verticals responds with error if hostname not supplied"() {
    when: "GET /verticals not supplying hostname"
    get()

    then: 'A error 400 is responded'
    assertStatus 400
    assertError 'ERR001'//, 'You must provide a "hostname" parameter!'
  }

  def "GET /verticals responds with error if hostname does not exists"() {
    given: 'A non existent vertical hostname'
    def hostname = 'xxx.unknown.com'

    when: "GET /verticals"
    get(query: [hostname: hostname])

    then: 'An error responded cause active vertical was not found'
    assertActiveVerticalNotFoundApiError(hostname)
  }

  private boolean assertActiveVerticalNotFoundApiError(String hostname) {
    assertStatus 422
    assertEmptyJsonObject()
    assertError 'AFER028', "No se encontró ninguna vertical activa con nombre de dominio ${hostname}!"
    true
  }

  def "GET /verticals responds with error if hostname belongs to an inactive vertical"() {
    given: 'The hostname of an inactive vertical'
    def inactiveVertical = findInactiveVertical()
    def hostname = new URL(inactiveVertical.baseUrl).host

    when: "GET /verticals"
    get(query: [hostname: hostname])

    then: 'An error responded cause active vertical was not found'
    assertActiveVerticalNotFoundApiError(hostname)
  }

  private Vertical findInactiveVertical() {
    def inactiveVertical = Vertical.findByActive(false)
    if (!inactiveVertical) {
      inactiveVertical = Vertical.get(1L)
      inactiveVertical.active = false
      inactiveVertical.save(flush: true)
    }
    inactiveVertical
  }
}
