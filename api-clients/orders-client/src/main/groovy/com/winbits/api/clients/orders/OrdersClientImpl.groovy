package com.winbits.api.clients.orders

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder
import wslite.rest.ContentType


@Component("ordersClient")
class OrdersClientImpl implements OrdersClient{
  private static final Logger log = LoggerFactory.getLogger(OrdersClientImpl)

  @Autowired
  RESTClient ordersRestClient

  @Override
  Map showForAdmin(Long id, String apiToken){
    def response

    try{
      response = ordersRestClient.post(path:"/card-subscription/user-admin/$id", headers:['wb-api-token':apiToken]){
        type ContentType.JSON
      }
    }
    catch (e){
      log.error ("Error: ", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    response.json
  }

    @Override
    Map cartItemsFromNomicka(List cartCommands , String apiToken){
        log.info "cart Details From nomicka: {}",cartCommands
        def response

        try{
            Map cartItems = [cartItems: cartCommands]
            response = ordersRestClient.post(path:"/nomicka-checkout", headers:['wb-api-token':apiToken]){
                type ContentType.JSON
                json cartItems
            }
        }
        catch (e){
            log.error ("Error: ", e)
            response = new ResponseBuilder().build(e.request, e.response)
        }
        response.json
    }

    @Override
    Map paymentOrder(Map request, String apiToken){
        log.info "request from api: {}",request
        def response

        try{

            response = ordersRestClient.post(path:"/payment", headers:['wb-api-token':apiToken]){
                type ContentType.JSON
                json request
            }
        }
        catch (e){
            log.error ("Error: ", e)
            response = new ResponseBuilder().build(e.request, e.response)
        }
        response.json
    }





}
