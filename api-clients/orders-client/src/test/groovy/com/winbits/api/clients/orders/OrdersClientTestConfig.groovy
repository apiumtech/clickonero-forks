package com.winbits.api.clients.orders

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrdersClientTestConfig{

  @Bean
  ConfigObject apiOrdersClientConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:8003/orders-api')
    configObject.put('contextSuffix', '')

    configObject
  }

  @Bean
  ConfigObject apiClientsAffiliationConfig(){
      def configObject = new ConfigObject()
      configObject.put('baseUrl', 'http://localhost:8000/affiliation-api')
      configObject.put('contextSuffix', '')

      configObject
  }

}