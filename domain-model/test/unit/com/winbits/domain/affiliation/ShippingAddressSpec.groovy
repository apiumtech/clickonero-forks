package com.winbits.domain.affiliation

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock(ShippingAddress)
class ShippingAddressSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}
    
    @Unroll
    void "testing lastName and #lastName2"(){
    setup:
    def shippingAddress = new ShippingAddress(firstName: name, 
      lastName: lastName, lastName2: lastName2)
    when:
    String lastNames = shippingAddress.obtainLastNames()
    then:
    assert lastNames
    assert lastNames == lastNamesTesting
    where:
    name        |       lastName        |       lastName2       |       lastNamesTesting
    'name'      |       'lastName'      |       null            |       'lastName'
    'name'      |       'lastName'      |       'lastName2'     |       'lastName lastName2'
    }
    
    @Unroll
    void "testing fullName"(){
    setup:
    def shippingAddress = new ShippingAddress(firstName: name, 
      lastName: lastName, lastName2: lastName2)
    when:
    String fullName = shippingAddress.fullName
    then:
    assert fullName
    assert fullName == lastNamesTesting
    where:
    name        |       lastName        |       lastName2       |       lastNamesTesting
    'name'      |       'lastName'      |       null            |       'name lastName'
    'name'      |       'lastName'      |       'lastName2'     |       'name lastName lastName2'
    }
}
