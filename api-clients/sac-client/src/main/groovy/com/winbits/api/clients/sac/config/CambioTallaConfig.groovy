package com.winbits.api.clients.sac.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class CambioTallaConfig {
  private static final Logger log = LoggerFactory.getLogger(CambioTallaConfig)

  @Autowired
  private ConfigObject apiClientsCambioTallaConfig

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
  RESTClient cambioTallaRestClient() {
    def restUrl = "${apiClientsCambioTallaConfig.baseUrl}"
    log.info('Cambio de talla Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient.httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
