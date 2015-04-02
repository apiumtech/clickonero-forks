package com.winbits.api.affiliation.services

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CodeGeneratorService)
class CodeGeneratorServiceSpec extends Specification {

  def setup() {
  }

  def cleanup() {
  }

  void "test generation code"() {
    when:
    def result = service.generateReferredCode()

    then:
    result
    result.length() == 10

  }
}
