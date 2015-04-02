package com.winbits.api.affiliation.controllers

import functionaltestplugin.FunctionalTestCase

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/26/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
class BitsControllerFunctionalTests extends FunctionalTestCase {
  def testBalance() {
    get('/bits/balance') {
      headers['Content-Type'] = 'application/json'
      headers['WB-API-TOKEN'] = 'W1nb1tsT3st'
      redirectEnabled = false
    }
    assertStatus 200
  }

  def testTransactions() {
    get('/bits/transactions') {
      headers['Content-Type'] = 'application/json'
      headers['WB-API-TOKEN'] = 'W1nb1tsT3st'
      redirectEnabled = false
    }
    assertStatus 200
  }
}
