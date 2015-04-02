package com.winbits.api.clients.migration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MigrationClientTestsConfig {

  @Bean
  ConfigObject apiClientsMigrationConfig() {
    def configObject = new ConfigObject()
    configObject.put('baseUrl', 'http://localhost:5020/')
    configObject.put('user', "migration@winbits.com")
    configObject.put('password', "w1nb1tsM1gr4t10n")

    configObject
  }
}
