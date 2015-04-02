package com.winbits.api.dummyclients

import com.winbits.api.clients.nomicka.NomickaClient
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: pinky
 * Date: 8/19/14
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Component('nomickaClient')
class DummyNomickaClient implements  NomickaClient{
    @Override
    Map stockNomicka(List skus) {

      List mapas = []
        Map json = [:]

        json["id"]=123
        json["stock"]=1
        json["status"]= "ACTIVE"
        mapas << json

      [response:mapas]
    }
}
