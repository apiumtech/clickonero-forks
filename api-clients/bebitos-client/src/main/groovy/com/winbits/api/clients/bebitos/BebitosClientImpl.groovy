package com.winbits.api.clients.bebitos

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('bebitosClient')
class BebitosClientImpl implements BebitosClient {

  private static Logger log = LoggerFactory.getLogger(BebitosClientImpl)


  @Autowired
  private ConfigObject apiClientsBebitosConfig

  @Autowired
  RESTClient bebitosRestClient

  @Override
  Map findUserByCredentials(String email, String password) {
    log.info("Find user by email ${email}")
    def response    
    try { 
      def token = apiClientsBebitosConfig.token 
      response = bebitosRestClient.get(path: "api/user?email=${email}&password=${password}&token=${token}")

    } catch(e) {
        log.error("Error when getting a user", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response]
  }

  @Override
  Map userDetail(String email) {
    log.info("retrieve user details by email ${email}")
    def response    
    try { 
      def token = apiClientsBebitosConfig.token 
      response = bebitosRestClient.get(path: "api/user_detail?email=${email}&token=${token}")

    } catch(e) {
        log.error("Error when getting a user detail", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response]
  }
}
