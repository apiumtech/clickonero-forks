package com.winbits.api.clients.merchants.sac

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MerchantsSacClientTestsConfig {

  @Bean
  ConfigObject apiClientsMerchantsSacConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:8080/merchants-admin/')
    configObject.put('contextSuffix', '')
    configObject
  }

}
