package com.winbits.bits

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@Mock(Configuration)
@Build(Configuration)
@TestFor(ConfigurationService)
@TestMixin(GrailsUnitTestMixin)
class ConfigurationServiceSpec extends Specification {

  def expiredOffsetDefault = 5
  def propertyKey = "expired_offset"

  def setup() {
    def mockConfig = new ConfigObject()
    mockConfig.winbits.config.expired_offset = expiredOffsetDefault
    service.grailsApplication.config = mockConfig
  }

  def cleanup() {
  }

  void "retrieve expired_offset from properties"() {
    when:
    def result = service.getConfigValue(propertyKey, Integer)

    then:
    result
    result == expiredOffsetDefault
  }

  void "retrieve expired_offset from database"() {
    setup:
    Configuration.build(code: "winbits.config.$propertyKey", value: '10',
        description: 'dias agregados por rollback')

    when:
    def result = service.getConfigValue(propertyKey, Integer)

    then:
    result
    result == 10
  }
}
