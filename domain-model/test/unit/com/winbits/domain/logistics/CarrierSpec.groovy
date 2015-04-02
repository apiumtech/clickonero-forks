package com.winbits.domain.logistics

import grails.test.mixin.*
import org.junit.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Carrier)
class CarrierSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test something"() {
    when:
    def carrier1 = Carrier.findOrSaveWhere(name: "DHL")
    def carrier2 = Carrier.findOrSaveWhere(name: "iBoy")

    then:
    assert carrier1 ==  Carrier.get(carrier1.id)

	}
}
