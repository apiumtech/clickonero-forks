package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.grest.test.spock.ApiSpecification
import org.apache.commons.lang.RandomStringUtils
import spock.lang.Shared

import static com.winbits.api.affiliation.controllers.ShippingAddressesControllerFunctionalSpec.*

class ShippingAddressControllerFunctionalSpec extends ApiSpecification {

  @Shared User testUser
  String apiToken
  Map shippingAddressInfo

  def setupSpec() {
    testUser = User.load(1L)
  }

  def setup() {
    apiToken = testUser.apiToken
    shippingAddressInfo = newShippingAddressInfo()
  }

  def 'update shipping address works as expected'() {
    given: 'A shipping address is created'
    def shippingAddress = createShippingAddress(user: testUser, main: false)

    and: 'Change some shipping address info properties'
    shippingAddressInfo.firstName = RandomStringUtils.randomAlphabetic(10)
    shippingAddressInfo.lastName = RandomStringUtils.randomAlphabetic(10)
    shippingAddressInfo.street = RandomStringUtils.randomAlphabetic(20)
    shippingAddressInfo.internalNumber = ''
    def resource = "/shipping-addresses/${shippingAddress.id}.json"

    when: "PUT ${resource}"
    put(resource) {
      json shippingAddressInfo
    }
    shippingAddress.refresh()

    then:
    assertStatus 200
    assertJsonObject()
    assertShippingAddressResponse(json.response)
    json.response.id == shippingAddress.id
    json.response.firstName == shippingAddressInfo.firstName
    json.response.lastName == shippingAddressInfo.lastName
    json.response.street == shippingAddressInfo.street
    shippingAddress.firstName == shippingAddressInfo.firstName
    shippingAddress.commonAddress.street == shippingAddressInfo.street
    !shippingAddress.commonAddress.internalNumber
  }

  def 'update shipping address activating main address'() {
    given: 'A main shipping address is created'
    def mainShippingAddress = createShippingAddress(user: testUser, main: true)

    and: 'A non-main shipping address is also created'
    def shippingAddress = createShippingAddress(user: testUser, main: false)

    and: 'Set main flag on shipping address info properties'
    shippingAddressInfo.main = true
    def resource = "/shipping-addresses/${shippingAddress.id}.json"

    when: "PUT ${resource}"
    put(resource) {
      json shippingAddressInfo
    }
    shippingAddress.refresh()

    then:
    assertStatus 200
    assertJsonObject()
    json.response.id == shippingAddress.id
    shippingAddress.main
    assertShippingAddressResponse(json.response)
    assertMainShippingAddress(mainShippingAddress, json)
  }

  def 'update non-existent shipping address must fail'() {
    given: 'A non-existent shipping address id'
    def shippingAddressId = -1L
    def resource = "/shipping-addresses/${shippingAddressId}.json"

    when: "PUT ${resource}"
    put(resource) {
      json shippingAddressInfo
    }

    then:
    assertStatus 404
    assertEmptyJsonObject()
    assertError 'ERR002'
  }

  def 'update non-owning shipping address must fail'() {
    given: 'A non-owning shipping address is created'
    def shippingAddress = createShippingAddress(user: User.get(2L).refresh())
    def resource = "/shipping-addresses/${shippingAddress.id}.json"

    when: "PUT ${resource}"
    put(resource) {
      json shippingAddressInfo
    }

    then:
    assertStatus 400
    assertEmptyJsonObject()
    assertError 'AFER050'
  }

  def 'delete shipping address works as expected'() {
    given: 'A new shipping address is created'
    def shippingAddress = createShippingAddress(user: testUser)
    def resource = "/shipping-addresses/${shippingAddress.id}.json"

    when: "DELETE $resource"
    delete(resource)

    then:
    assertStatus 200
    assertFullJsonObject([id: shippingAddress.id])
    ShippingAddress.findAllByUser(testUser).every { it.id != json.response.id }
  }

  def 'delete non-existent shipping address must fail'() {
    given: 'A non-existent shipping address id'
    def shippingAddressId = -1L
    def resource = "/shipping-addresses/${shippingAddressId}.json"

    when: "DELETE ${resource}"
    delete(resource)

    then:
    assertStatus 404
    assertEmptyJsonObject()
    assertError 'ERR002'
  }

  def 'delete non-owning shipping address must fail'() {
    given: 'A non-owning shipping address is created'
    def shippingAddress = createShippingAddress(user: User.get(2L))
    def resource = "/shipping-addresses/${shippingAddress.id}.json"

    when: "DELETE ${resource}"
    delete(resource)

    then:
    assertStatus 400
    assertEmptyJsonObject()
    assertError 'AFER050'
  }
}
