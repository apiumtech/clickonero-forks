package com.winbits.api.clients.sac 

import com.winbits.api.clients.sac.helper.LoginHelper
import groovy.util.ConfigObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('cambioTallaClient')
class CambioTallaClientImpl implements CambioTallaClient{

  private static Logger log = LoggerFactory.getLogger(CambioTallaClientImpl)

  @Autowired
  @Qualifier ('cambioTallaRestClient')
  RESTClient cambioTallaRestClient

  @Autowired
  ConfigObject apiUserConfig 

  @Autowired
  LoginHelper loginHelper

  Map login (String user, String password){
    log.info "USER: {}  --  PASSWORD: {}", user, password

    def response
    try{
      response = cambioTallaRestClient.get (path: '/login', query:[User:user, Password:password])
    }catch (e){
      log.error ("Error al hacer login: ", e)
       response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }
  
  Map listadoCambioTalla (Map params){
    log.info("Find cambio tallas {$params}")
    def response    
    String apiToken = loginHelper.obtainApiToken(this.login(apiUserConfig.user, apiUserConfig.password)) 
    try { 
      response = cambioTallaRestClient.get(path: "/cambioTallas", 
        query:params, headers:[ApiToken: apiToken])
    } catch(e) {
        log.error("Error obteniendo listado de ordenes", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  Map cambioTallaDetalle (Long orderId){
    log.info ("Find cambio talla detalle $orderId")
    def response
    String apiToken = loginHelper.obtainApiToken(this.login(apiUserConfig.user, apiUserConfig.password)) 
    try {
      response = cambioTallaRestClient.get (path: '/cambioTalla', 
        query: [order_id:orderId], headers: [ApiToken: apiToken])
    }catch (e){
      log.error("Error obteniendo detalle de ordenes", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }

  Map listadoItems (Long idDetalle,Integer cantidad ){
    log.info "listado items idDetalle: {}, cantidad: {}",idDetalle, cantidad
    def response
    String apiToken = loginHelper.obtainApiToken(this.login(apiUserConfig.user, apiUserConfig.password)) 
    try{
        response = cambioTallaRestClient.post(path: '/cambioTalla', 
          headers: [ApiToken: apiToken]) {
          json id_Detalle: idDetalle, cantidad: cantidad
        }
    }catch (e){
      log.error ("obteniendo listado de items", e)
      response = new ResponseBuilder ().build (e.request, e.response)
    }
    [response:response.json]
  }
  
  Map cambiaTalla (Integer cantidad, Long idSkuProfile, Long idDetalle) {
    log.info ("cambio de talla, cantidad: {} SKU Profile: {}",cantidad, idSkuProfile )
    
    def response
    String apiToken = loginHelper.obtainApiToken(this.login(apiUserConfig.user, apiUserConfig.password)) 
    try{
      response = cambioTallaRestClient.put (path: '/cambioTalla', query: [idDetalle: idDetalle],
        headers:[ApiToken: apiToken]) {
        json cantidad: cantidad, idSkuProfile: idSkuProfile
      }
    }catch (e){
      log.error ("error al obtener listado de cambio de talla", e)
      response = new ResponseBuilder ().build (e.request,e.response)
    }
    [response:response.json]
  }

}
