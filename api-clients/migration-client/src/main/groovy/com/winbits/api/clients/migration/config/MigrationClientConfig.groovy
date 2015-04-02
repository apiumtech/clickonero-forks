package com.winbits.api.clients.migration.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient
import wslite.http.auth.HTTPBasicAuthorization

@Configuration
class MigrationClientConfig {
  private static final Logger log = LoggerFactory.getLogger(MigrationClientConfig)

  @Autowired
  ConfigObject apiClientsMigrationConfig

  @Bean
  HTTPClient httpClient() {
    new HTTPClient().with {
      connectTimeout = 5000
      readTimeout = 10000
      useCaches = false
      followRedirects = false
      sslTrustAllCerts = true
      it
    }
  }

  @Bean
  RESTClient migrationRestClient() {
    def restUrl = "${apiClientsMigrationConfig.baseUrl}"
    log.info('Coupon Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient.httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    //restClient.authorization = new HTTPBasicAuthorization(apiClientsMigrationConfig.user, apiClientsMigrationConfig.password)
    restClient
  }
}
