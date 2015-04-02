package com.winbits.api.clients.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.auth.HTTPBasicAuthorization

/**
 * service-api-test's config
 */
@Configuration
class ServiceClientTestsConfig {

  @Bean
  ConfigObject apiClientsServiceConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:5010')
    configObject
  }
}
