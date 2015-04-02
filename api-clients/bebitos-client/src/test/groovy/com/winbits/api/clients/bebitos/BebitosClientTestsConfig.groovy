package com.winbits.api.clients.bebitos

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.auth.HTTPBasicAuthorization

/**
 * bebitos-api-test's config
 */
@Configuration
class BebitosClientTestsConfig {

  @Bean
  ConfigObject apiBebitosServiceConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'https://admin.bebitos.mx')
    configObject.put('token', '81f4adec925c3ee0bb041f12614bd4e4ec45210c7684e23c')
    configObject
  }
}
