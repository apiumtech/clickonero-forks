package com.winbits.api.clients.orders.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class OrdersClientConfig {
  private static final Logger log = LoggerFactory.getLogger(OrdersClientConfig)

  @Autowired
  ConfigObject apiOrdersClientConfig

  @Bean
  HTTPClient httpClient(){
    new HTTPClient().with {
      connectTimeout = 30000
      readTimeout = 65000
      useCaches = false
      followRedirects = false
      sslTrustAllCerts = true
      it
    }
  }

  @Bean
  RESTClient ordersRestClient(){
    def restUrl = "${apiOrdersClientConfig.baseUrl}"
    log.info('Orders Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient .httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
