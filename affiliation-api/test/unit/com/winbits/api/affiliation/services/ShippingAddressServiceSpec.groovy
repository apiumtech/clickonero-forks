package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.ZipCodeInfo

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Build([ShippingAddress, Vertical, User])
@Mock([User, ShippingAddress, ZipCodeInfo])
@TestFor(ShippingAddressService)
class ShippingAddressServiceSpec extends Specification {

  def setup(){
    User.metaClass.encodePassword = {}
  }

  void "test delete"() {
    setup:
    def vertical = Vertical.build()
    def commonAddress = new CommonAddress(street: 'Reforma', externalNumber: 's/n', location: 'Lomas')
    def user = User.build(vertical: vertical)
    def address = ShippingAddress.build(user: user, commonAddress: commonAddress)

    when:
    def result = service.deleteShippingAddress(address.id, user)

    then:
    result
    !ShippingAddress.get(address.id)
  }

  void "test delete main shipping"() {
    setup:
    def vertical = Vertical.build()
    def commonAddress = new CommonAddress(street: 'Reforma', externalNumber: 's/n', location: 'Lomas')
    def user = User.build(vertical: vertical)
    def address = ShippingAddress.build(user: user, commonAddress: commonAddress, main: true)
    def addressAux = ShippingAddress.build(user: user, commonAddress: commonAddress, main: false)

    when:
    def result = service.deleteShippingAddress(address.id, user)

    then:
    result
    !ShippingAddress.get(address.id)
    addressAux.main

  }

  void "create migration common address"(){
    setup:
    def map = [firstName:"maria",lastName:"rodriguez",lastName2:"medina",phone:phone,
        location:"Jamay Centro",zipCode:"47900",externalNumber:"# 76",internalNumber:null, street:street,
        indications:""
    ]
    when:
    def resp =service.createCommonAddressMigration(map)
    then:
    assert resp
    assert  resp.phone == phone
    assert  resp.street == street
    where:
    phone       | street
    '1234568741'| null
    null        | null
    '9879879878'| 'asdasd asdads'
  }

  void "create migration shipping address"(){
    setup:
    def user = User.build(email: 'test@winbits.com')
    def map = [firstName:firstName,lastName:"rodriguez",lastName2:"medina",phone:phone,
        location:"Jamay Centro",zipCode:"47900",externalNumber:"# 76",internalNumber:null, street:street,
        indications:""
    ]
    and:
    new ZipCodeInfo(zipCode: '47900', county:'county1').save(validate:false)
    when:
    def resp =service.createShippingAddressMigration(map, user, true)
    then:
    assert resp
    assert  resp.commonAddress.phone == phone
    assert  resp.commonAddress.street == street
    assert  resp.firstName == firstName
    assert resp.county
    where:
    phone       | street          | firstName
    '1234568741'| null            | null
    null        | null            | 'mario'
    '9879879878'| 'asdasd asdads' | 'jimena'
  }

  void "obtain zip code info"() {
  setup:
    ZipCodeInfo zipCodeInfoMock = new ZipCodeInfo(zipCode: '38210', county: 'county1').save(validate: false)
    ZipCodeInfo zipCodeInfoMock2 = new ZipCodeInfo(zipCode: '38210', county: 'county2').save(validate: false)
    ZipCodeInfo zipCodeInfoMock3 = new ZipCodeInfo(zipCode: '38210', county: 'county3').save(validate: false)
  when:
    def zipCodeInfo = service.obtainCountyFromZipCode(zipCode)
  then:
    assert zipCodeInfo == result
  where:
    zipCode     |       result
    '38210'     |       'county1'
    '38211'     |       null
    null        |       null
  }
}
