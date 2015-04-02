package com.winbits.api.clients.migration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder
import wslite.rest.ContentType

@Component('migrationClient')
class MigrationClientImpl implements MigrationClient {
  private static Logger log = LoggerFactory.getLogger(MigrationClientImpl)
  
  @Autowired
  RESTClient migrationRestClient

  Map obtainPersonByEmail (String email){
    log.info "EMAIL: {}", email
    def response
    try{
      response = migrationRestClient.post (path: '/person'){
          type ContentType.JSON
          json email: email
      }
    }catch (e){
      log.error ("Error al migrar usario de clients.sms: ", e)
       response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }

  Map getShippingAddressByEmail (String email){
    log.info "EMAIL: {}", email
    def response
    try{
      response = migrationRestClient.post (path: '/shippingAddress'){
        type ContentType.JSON
        json email: email
      }
    }catch (e){
      log.error ("Error al migrar shipping address de clients.sms: ", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }
  
  Map obtainUserInfo (String email) {
    log.info "obtaining info from user: EMAIL: {}", email
    def response
    try{
      response = migrationRestClient.post (path: '/obtainUserInfo'){
        type ContentType.JSON
        json email: email
      }
    }catch (e){
      log.error ("Error al obtener informacion del usuario: ${email} clients.sms: ", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }
}
