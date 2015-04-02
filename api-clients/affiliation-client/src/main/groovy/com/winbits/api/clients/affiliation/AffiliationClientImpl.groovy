package com.winbits.api.clients.affiliation

import com.winbits.api.dummyclients.affiliation.DummyAffiliationClient
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import wslite.rest.ResponseBuilder
import wslite.rest.ContentType
/**
 * Client to consume Winbits' affiliation API.
 */
@Component('affiliationClient')
class AffiliationClientImpl implements AffiliationClient {
  private static final Logger log = LoggerFactory.getLogger(AffiliationClientImpl)

  @Autowired
  private RESTClient affiliationRestClient

  @Override
  Map register(String email, String password) {
    [:]
  }
  
  @Override
  Map login(String email, String password) {
    log.info "EMAIL: {}  --  PASSWORD: {}", email, password

    def response
    try{
      response = affiliationRestClient.post (path: '/login'){
          type ContentType.JSON
          json email: email, password: password
      }
    }catch (e){
      log.error ("Error al logearse con winbits: ", e)
       response = new ResponseBuilder().build(e.request, e.response)
    }
    [response:response.json]
  }

  @Override
  Map couponList(Long orderDetailId, String apiToken) {
        log.info "orderDetail", orderDetailId
        def response
        try{    log.info("Find coupons for order detail with id ${orderDetailId}")
            response = affiliationRestClient.get(path: "coupons/${orderDetailId}",  headers:['wb-api-token':apiToken] )
            [coupons: response.json]
        }catch (e){
            log.error ("Error al logearse con winbits: ", e)
            response = new ResponseBuilder().build(e.request, e.response)
        }
      [response:response.json]
  }

  @Override
  Map couponId(Long couponId,Long orderDetailId,String format, String apiToken) {
     log.info "orderDetail", orderDetailId
        def response
        try{    log.info("Find coupons with id ${orderDetailId}")
            response = affiliationRestClient.get(path: "coupon/${couponId}.json?format=${format}&orderDetailId=${orderDetailId}",  headers:['wb-api-token':apiToken] )
            [coupons: response.json]
        }catch (e){
            log.error ("Error al logearse con winbits: ", e)
            response = new ResponseBuilder().build(e.request, e.response)
       }
      [response:response.json]
  }

}
