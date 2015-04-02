package com.winbits

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(LocaleService)
class LocaleServiceSpec extends Specification {

  def setup() {
  }

  def cleanup() {
  }

  void "Obtiene default Locale"() {
    when:
    Locale locale = service.getLocale()
    then:
      assert locale
  }
}
