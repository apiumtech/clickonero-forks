package com.winbits.api.clients.travel

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('travelClient')
class TravelClientImpl implements TravelClient {

  private static Logger log = LoggerFactory.getLogger(TravelClientImpl)

  @Autowired
  RESTClient travelRestClient

  @Override
  Map findTravelByItemGroupId(Long id) {
    log.info("Find travel with item group id ${id}")
    def response    
    try { 

      response = travelRestClient.get(path: "travel/${id}")

    } catch(e) {
        log.error("Error when getting a coupon", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response]
  }

}
