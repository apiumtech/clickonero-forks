package com.winbits.api.clients.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('serviceClient')
class ServiceClientImpl implements ServiceClient {

  private static Logger log = LoggerFactory.getLogger(ServiceClientImpl)

  @Autowired
  RESTClient serviceRestClient

  @Override
  Map findServiceByItemGroupId(Long id) {
    log.info("Find service with item group id ${id}")
    def response    
    try { 

      response = serviceRestClient.get(path: "service/${id}")

    } catch(e) {
        log.error("Error when getting a coupon", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response]
  }

}
