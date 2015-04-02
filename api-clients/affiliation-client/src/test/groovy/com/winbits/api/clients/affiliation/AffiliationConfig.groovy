package com.winbits.api.clients.affiliation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AffiliationClientTestsConfig {

    @Bean
    ConfigObject apiAffiliationClientConfig() {
        def configObject = new ConfigObject()
        configObject.put('baseUrl', 'http://localhost:8000/affiliation-api')

        configObject
    }
}
