package com.winbits.api.clients.clickonero

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClickoneroClientTestConfig{

  @Bean
  ConfigObject apiClickoneroClientConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'https://qa.clients.sms.com.mx/hipstore/')
    configObject.put('contextSuffix', '')
    configObject
  }

}