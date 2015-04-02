package com.winbits.api.clients.affiliation.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class AffiliationClientConfig{
  private static final Logger log = LoggerFactory.getLogger(AffiliationClientConfig)

  @Autowired
  ConfigObject apiAffiliationClientConfig
  
  @Bean
  HTTPClient httpClient(){
    new HTTPClient().with {
      connectTimeout = 0
      readTimeout = 0
      useCaches = false
      followRedirects = false
      sslTrustAllCerts = true
      it
    }
  }

  @Bean
  RESTClient affiliationRestClient(){
    def restUrl = "${apiAffiliationClientConfig.baseUrl}"
    log.info('Affiliation Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient .httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }

}
