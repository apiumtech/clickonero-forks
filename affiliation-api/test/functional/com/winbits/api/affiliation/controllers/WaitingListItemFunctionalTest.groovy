package com.winbits.api.affiliation.controllers

import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/14/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
class WaitingListItemFunctionalTest extends FunctionalTestCase {

  void testDeleteWaitingListErrorIfUserIsInvalid() {
    delete('/waiting-list-item/1'){
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 401
    def json = grails.converters.JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER011'
    assert json.meta.message == 'Usuario inválido'
  }

  void testDeleteWaitingListErrorIfSkuProfileIsNotFound() {
    delete('/waiting-list-item/-5'){
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 404
    def json = grails.converters.JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER010'
    assert json.meta.message == 'El articulo no existe'
  }

  void testDeleteWaitingListErrorIfItemIsNotFound() {
    delete('/waiting-list-item/20'){
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 404
    def json = grails.converters.JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER014'
    assert json.meta.message == 'El articulo no se encuentra en tu lista de espera'
  }

  void testUpdateStatusWaitingListErrorIfSkuProfileIsNotFound() {
    post('/waiting-list-item/-5'){
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'

    }
    assertStatus 404
    def json = grails.converters.JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER010'
    assert json.meta.message == 'El articulo no existe'
  }

  void testUpdateStatusWaitingList() {
    post('/waiting-list-item/22'){
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 200
  }

  void testDeleteWaitingList() {
    delete('/waiting-list-item/22'){
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 200
  }


}
