package com.winbits.api.clients.sac

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SacClientTestsConfig {

  @Bean
  ConfigObject apiClientsCambioTallaConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://api_servicio.dream-it.com.mx/api/')
    configObject.put('contextSuffix', '')
    configObject
  }

  @Bean
  ConfigObject apiUserConfig (){
    def configObject = new ConfigObject ()
    configObject.put ('user', 'marco@dream-it.com.mx')
    configObject.put ('password','P@ssword')
    configObject
  }
  
}
