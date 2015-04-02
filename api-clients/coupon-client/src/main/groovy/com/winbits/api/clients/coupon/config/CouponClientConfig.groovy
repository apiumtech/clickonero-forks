package com.winbits.api.clients.coupon.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import wslite.http.HTTPClient
import wslite.rest.RESTClient

@Configuration
class CouponClientConfig {
  private static final Logger log = LoggerFactory.getLogger(CouponClientConfig)

  @Autowired
  private ConfigObject apiClientsCouponConfig

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
  RESTClient couponRestClient() {
    def restUrl = "${apiClientsCouponConfig.baseUrl}"
    log.info('Coupon Rest Client URL: {}', restUrl)
    def restClient = new RESTClient()
    restClient.url = restUrl
    restClient .httpClient = httpClient()
    restClient.defaultAcceptHeader = 'application/json'
    restClient
  }
}
