package com.winbits.api.clients.balance

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BalanceClientTestConfig{

  @Bean
  ConfigObject balanceApiClientsConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://127.0.0.1:9999')
    configObject.put('contextSuffix', '')

    configObject
  }
}
