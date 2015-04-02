package com.winbits.api.clients.sms.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient


@Configuration
class SmsClientConfig {
  private static final Logger log = LoggerFactory.getLogger(SmsClientConfig)

  @Autowired
  ConfigObject apiSmsClientConfig

  @Bean
  HTTPClient httpClient(){
    new HTTPClient().with {
      connectTimeout = 15000
      readTimeout = 10000
      useCaches = false
      followRedirects = false
      sslTrustAllCerts = true
      it
    }
  }

  @Bean
  RESTClient smsRestClient(){
    def restUrl = "${apiSmsClientConfig.baseUrl}"
    log.info('Sms Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient.httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
