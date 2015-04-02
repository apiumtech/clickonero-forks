package com.winbits.api.clients.bits

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.auth.HTTPBasicAuthorization

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 4/29/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
class BitsClientTestsConfig {

  @Bean
  HTTPBasicAuthorization bitsClientAuthorization() {
    new HTTPBasicAuthorization('admin', 'admin')
  }

  @Bean
  ConfigObject apiClientsConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:8001')
    configObject.put('contextSuffix', '-api')
    configObject
  }
}
