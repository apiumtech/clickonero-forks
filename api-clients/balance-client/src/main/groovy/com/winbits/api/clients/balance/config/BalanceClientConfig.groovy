package com.winbits.api.clients.balance.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

/**
 * Spring config class for the BitsClient
 */
@Configuration
class BalanceClientConfig {

  private static final Logger log = LoggerFactory.getLogger(BalanceClientConfig)

  @Autowired
  private ConfigObject balanceApiClientsConfig

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
  RESTClient balanceRestClient() {
    def restUrl = "${balanceApiClientsConfig.baseUrl}/${balanceApiClientsConfig.contextSuffix}"
    log.info('Bits Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient.httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
