package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ZipCodeInfo
import com.winbits.grest.test.spock.ApiSpecification
import org.apache.commons.lang.RandomStringUtils
import wslite.json.JSONObject

class ShippingAddressesControllerFunctionalSpec extends ApiSpecification {
  User testUser
  String apiToken
  final String resource = '/shipping-addresses.json'
  Map shippingAddressInfo

  def setup() {
    testUser = User.get(1L)
    apiToken = testUser.apiToken
    shippingAddressInfo = newShippingAddressInfo()
  }

  static Map newShippingAddressInfo() {
    ["firstName": "Miguel", "lastName": "Huerta", "lastname2":"Uchis","street": "De la esperanza", "location": "Nostalgia",
        "phone": "5544998877", "zipCodeInfo": ["id": 1], "betweenStreets": "ghvgvvgh ghghgh 1",
        "indications": "ggfgffghhgfhhgf4", "externalNumber": "5", "internalNumber": "6"]
  }

  def 'create shipping address works as expected'() {
    given: 'A map with the required info to create a shipping address'
    shippingAddressInfo.firstName = RandomStringUtils.randomAlphabetic(10)

    when: "POST $resource"
    post {
      json shippingAddressInfo
    }
    def expectedMain = testUser.shippingAddresses.size() == 1

    then:
    assertCreateShippingAddressSpecs()
    json.response.main == expectedMain
  }

  private boolean assertCreateShippingAddressSpecs() {
    assertStatus 201
    assertShippingAddressResponse(json.response)
    assert json.response.firstName == shippingAddressInfo.firstName
    true
  }

  def 'create shipping address sets main address as specified'() {
    given: 'A main shipping address is created'
    def mainShippingAddress = createShippingAddress(user: testUser, main: true, zipCodeInfo: ZipCodeInfo.get(1))

    and: 'Prepare the info for a new main shipping address'
    shippingAddressInfo.firstName = RandomStringUtils.randomAlphabetic(10)
    shippingAddressInfo.main = true

    when: "POST $resource"
    post {
      json shippingAddressInfo
    }

    then: 'The last shipping address must be the main one and no other main shipping address must exists'
    assertCreateShippingAddressSpecs()
    assertMainShippingAddress(mainShippingAddress, json)
  }

  static ShippingAddress createShippingAddress(Map properties) {
    properties.commonAddress = properties.commonAddress ?: new CommonAddress(street: 'Calle 7', externalNumber: '456')
    ShippingAddress.build(properties).save()
  }

  static assertMainShippingAddress(ShippingAddress oldMainShippingAddress, JSONObject json) {
    oldMainShippingAddress.refresh()
    def mainShippingAddresses = ShippingAddress.findAllByUserAndMain(oldMainShippingAddress.user, true)
    assert mainShippingAddresses.size() == 1
    assert json.response.id == mainShippingAddresses.first().id
    assert !oldMainShippingAddress.main
    assert json.response.main
    true
  }

  def 'get shipping-addresses works as expected'() {
    given: 'A new shipping address is created'
    def shippingAddress = createShippingAddress(user: testUser)

    when: "GET ${resource}"
    get()

    then:
    assertStatus 200
    assertListShippingAddressesResponse(json.response)
    json.response.last().id == shippingAddress.id
  }

  private void assertListShippingAddressesResponse(def shippingAddresses) {
    assertJsonArray(shippingAddresses)
    assert shippingAddresses.size() == ShippingAddress.countByUser(testUser)
    shippingAddresses.each {
      assertShippingAddressResponse(it)
    }
  }

  static void assertShippingAddressResponse(JSONObject shippingAddress) {
    def fields = ['id', 'firstName', 'lastName', 'lastName2','phone', 'street', 'externalNumber', 'internalNumber',
        'betweenStreets', 'indications', 'zipCodeInfo', 'location', 'main', 'zipCode', 'county', 'state']
    assertJsonObject shippingAddress, fields
  }
}
