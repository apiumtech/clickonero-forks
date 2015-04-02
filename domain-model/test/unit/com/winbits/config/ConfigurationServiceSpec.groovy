package com.winbits.config

import com.winbits.domain.config.ConfigurationService
import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification
import com.winbits.domain.Configuration
import spock.lang.Unroll

@TestFor(ConfigurationService)
@Build(Configuration)
@Mock(Configuration)
class ConfigurationServiceSpec extends Specification {

  def setup() {
    config.winbits.config.promo.completeRegisterCashbackAmount = 100
    config.winbits.config.other = 'FYI'
  }

	void "Getting configuration data"() {
    setup :
      new Configuration(code:code, value:expected).save()

    when :
      String value = service.getConfig(code)

    then :
      assert value == expected

    where :
      code          || expected
//      null          || ""
      ""            || ""
      "shipping"    || "1000.00"
      "maxWinBits"  || "10000.00"
	}

  @Unroll
  void 'Getting Configuration Values from DB -> #code: #value'() {
    setup:
    Configuration.build(code: "winbits.config.$code", value: value)

    expect:
    service.getConfigValue(code, clazz) == value

    where:
    code                                   | value  | clazz
    'promo.completeRegisterCashbackAmount' | '50'   | String
    'other'                                | 'XXX'  | String
    'promo.completeRegisterCashbackAmount' | 50.0   | BigDecimal
  }

  @Unroll
  void 'Failing converting configuration value returns config default'() {
    setup:
    config.winbits.config.promo.test = 666.0
    Configuration.build(code: "winbits.config.promo.test", value: 'xxx')

    expect:
    666.0 == service.getConfigValue('promo.test', BigDecimal)
  }

  @Unroll
  void 'Configuration gets values from Config if not in DB -> #code: #value'() {
    setup:
    Configuration.build(code: "winbits.config.x.$code", value: value)

    expect:
    service.getConfigValue(code) == value

    where:
    code                                   | value
    'promo.completeRegisterCashbackAmount' | '100'
    'other'                                | 'FYI'
  }
}
