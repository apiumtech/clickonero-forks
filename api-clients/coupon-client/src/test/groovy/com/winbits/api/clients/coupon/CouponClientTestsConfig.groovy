package com.winbits.api.clients.bits

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.auth.HTTPBasicAuthorization

/**
 * coupon-api-test's config
 */
@Configuration
class BitsClientTestsConfig {

  @Bean
  ConfigObject apiClientsCouponConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:5000')
    configObject
  }
}
