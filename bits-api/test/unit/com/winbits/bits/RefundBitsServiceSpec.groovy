package com.winbits.bits

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(RefundBitsService)
class RefundBitsServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test refund bits"() {
            when:
                def mapaBits = service.refundBits(1L,1L)
            then:
                assert mapaBits
                assert mapaBits.balance == 50.0
                assert mapaBits.expirationDate
                assert mapaBits.transactionId == 1L
	}
}
