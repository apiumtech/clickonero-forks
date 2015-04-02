package com.winbits.api.clients.travel

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.auth.HTTPBasicAuthorization

/**
 * service-api-test's config
 */
@Configuration
class TravelClientTestsConfig {

  @Bean
  ConfigObject apiClientsTravelConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:5060')
    configObject
  }
}
