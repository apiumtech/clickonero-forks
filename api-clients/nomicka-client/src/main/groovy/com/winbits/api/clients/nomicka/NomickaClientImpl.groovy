package com.winbits.api.clients.nomicka

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wslite.http.auth.HTTPAuthorization
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

/**
 * Created with IntelliJ IDEA.
 * User: pinky
 * Date: 8/19/14
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("nomickaClient")
class NomickaClientImpl implements NomickaClient{
    private static Logger log = LoggerFactory.getLogger(NomickaClientImpl)

    @Autowired
    RESTClient nomickaRestClient

    @Autowired
    ConfigObject nomickaAuth


    @Override
    Map stockNomicka(List skus) {
     def response

      //TODO crear config para setear usuario y password
      nomickaRestClient.authorization = new HTTPBasicAuthorization( nomickaAuth.user, nomickaAuth.password )
     log.info("user:  $nomickaAuth.user, pass:$nomickaAuth.password")

    try {

        Map skuMap = [skus: skus]
        response = nomickaRestClient.post(path:"skus/stock") {
            type ContentType.JSON
            json skuMap
        }
    }  catch (e) {
        log.error("Error:",e)
        response = new ResponseBuilder().build(e.request, e.response)

    }
        if (response.statusCode >= 400) {

            throw new RuntimeException(response.json.meta.message)
        }


//      [response:response]
       response.json
    }
}
