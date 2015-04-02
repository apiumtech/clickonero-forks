package com.winbits.api.clients.nomicka

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created with IntelliJ IDEA.
 * User: pinky
 * Date: 8/19/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
class NomickaClientTestConfig {

    @Bean
    ConfigObject apiClientsNomickaConfig() {
        def configObject = new ConfigObject()
        configObject.put('baseUrl', 'http://localhost:8010/nomicka-api')

        configObject
    }

    @Bean
    ConfigObject nomickaAuth (){
        def configObject = new ConfigObject()
        configObject.put("user" , "nomicka@clients.sms.com")
        configObject.put ("password", "s3cr3t0")
        configObject
    }

}
