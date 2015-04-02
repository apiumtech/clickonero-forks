package com.winbits.api.clients.clickonero

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder
import wslite.rest.ContentType


@Component("clickoneroClient")
class ClickoneroClientImpl implements ClickoneroClient{
  private static final Logger log = LoggerFactory.getLogger(ClickoneroClientImpl)

  @Autowired
  RESTClient clickoneroRestClient

  @Override
  Map ordersClickoneroHistory(Long personId){
    def response

    try{
      response = clickoneroRestClient.get(path:"accountApi.js?id=${personId}")
    }
    catch (e){
      log.error ("Error: ", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    response.json
  }

}
