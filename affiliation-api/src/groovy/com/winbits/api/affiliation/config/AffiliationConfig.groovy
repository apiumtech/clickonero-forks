package com.winbits.api.affiliation.config

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created with IntelliJ IDEA.
 * User: arcesino
 * Date: 4/30/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
class AffiliationConfig {
  @Autowired
  GrailsApplication grailsApplication

  @Bean
  ConfigObject apiClientsConfig() {
    grailsApplication.config.api.clients.bits
  }
  
  @Bean
  ConfigObject balanceApiClientsConfig() {
    grailsApplication.config.api.clients.balance
  }

  @Bean
  ConfigObject apiClientsCouponConfig() {
    grailsApplication.config.api.clients.coupon
  }

  @Bean
  ConfigObject apiClientsServiceConfig() {
    grailsApplication.config.api.clients.service
  }

  @Bean
  ConfigObject apiClientsBebitosConfig() {
    grailsApplication.config.api.clients.bebitos
  }

  @Bean
  ConfigObject apiClientsMigrationConfig() {
    grailsApplication.config.api.clients.migration
  }
  
  @Bean
  ConfigObject apiClientsTravelConfig() {
    grailsApplication.config.api.clients.travel
  }

  @Bean
  ConfigObject apiSmsClientConfig() {
    grailsApplication.config.api.clients.sms
  }

}
