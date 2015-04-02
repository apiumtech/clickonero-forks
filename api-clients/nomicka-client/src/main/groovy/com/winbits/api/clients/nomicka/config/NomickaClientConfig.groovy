package com.winbits.api.clients.nomicka.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class NomickaClientConfig {
  private static final Logger log = LoggerFactory.getLogger(NomickaClientConfig)

  @Autowired
  private ConfigObject apiClientsNomickaConfig

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
  RESTClient nomickaRestClient() {
    def restUrl = "${apiClientsNomickaConfig.baseUrl}"
    log.info('Nomicka Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient .httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }



}
