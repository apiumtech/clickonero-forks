package com.winbits.api.clients.sms

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SmsClientTestConfig {

  @Bean
  ConfigObject apiSmsClientConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://api.broadcaster.mx')
    configObject.put('contextSuffix', '')
    configObject
  }

}