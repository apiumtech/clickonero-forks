package com.winbits.api.clients.merchants.sac.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class MerchantsSacClientConfig {
  private static final Logger log = LoggerFactory.getLogger(MerchantsSacClientConfig)

  @Autowired
  private ConfigObject apiClientsMerchantsSacConfig

  @Bean
  HTTPClient httpClient() {
    new HTTPClient().with {
      connectTimeout = 120000
      readTimeout = 180000
      useCaches = false
      followRedirects = false
      sslTrustAllCerts = true
      it
    }
  }

  @Bean
  RESTClient merchantsSacRestClient() {
    def restUrl = "${apiClientsMerchantsSacConfig.baseUrl}"
    log.info('Merchants SAC Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient .httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
