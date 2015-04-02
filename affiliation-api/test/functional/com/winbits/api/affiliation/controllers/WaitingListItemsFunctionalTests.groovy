package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.WaitingListItem
import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.*
import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON

class WaitingListItemsFunctionalTests extends FunctionalTestCase {


  void testAddWaitingListItemErrorIfUserIsInvalid() {
    post('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: 1] as JSON
      }
    }
    assertStatus 401
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER011'
    assert json.meta.message == 'Usuario inválido'
  }

  void testAddWaitingListItemErrorIfSkuProfileDoesNotExist() {
    post('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: -5] as JSON
      }
    }
    assertStatus 404
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER010'
    assert json.meta.message == 'El articulo no existe'
  }

  void testAddWaitingListItemErrorInStock() {
    post('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: 1] as JSON
      }
    }
    assertStatus 409
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER012'
    assert json.meta.message == 'El articulo que deseas agregar aún existe en inventario'
  }

  //Depende de agregar en base de datos un sku_profile con quantity_on_hand != -1 y un quantity_reserved < quantity_on_hand
  void testAddWaitingListItem() {
    post('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: 21] as JSON
      }
    }
    assertStatus 200
  }

  //Depende de que ya exista en base de datos un waiting_list_item para el usuario en sesion y sku_profile_id = 21
  void testAddWaitingListItemErrorAlreadyIsInList() {

    def sku = Sku.build(sku: "sku22", ean: "ean22", weight: 0, width: 0, virtualCost: 0, item: Item.load(2), status: ItemStatus.load(1),
        height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false)
        .save(failOnError: true)
    2.times {
      Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku, type: AttributeType.load(1)).save(failOnError: true)
    }
    def itemGroupProfile = ItemGroupProfile.load(1)
    def skuProfile = new SkuProfile(fullPrice: 10, price: 10, sku: sku, vertical: Vertical.load(1), quantityReserved: 1, quantityOnHand: 1, status: ItemStatusEnum.ACTIVE.domain, itemGroupProfile: itemGroupProfile).save(failOnError: true)
    def odc = Odc.build(provider: Provider.load(1), type: OdcType.load(1), status: OdcStatus.load(1), paymentStatus: OdcPaymentStatus.load(1), wmsStatus: WmsStatus.load(1), incomeType: IncomeType.load(1))
    def odcDetail = OdcDetail.build(odc: odc, sku: sku, status: OdcDetailStatus.load(1))
    def history = InOutSkuHistory.build(sku: sku, type: InOutSkuHistoryType.load(1))
    new SkuIncome(cost: 200, stock: 20, available: false, sku: sku, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save(failOnError: true)
    new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile, deleted: false, status: WaitingListItemStatusEnum.SOLD_OUT.domain).save(flush: true)

    post('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: skuProfile.id] as JSON
      }
    }
    assertStatus 409
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER013'
    assert json.meta.message == 'El articulo ya existe en tu lista de espera'
  }

  void testShowWaitingListItemErrorIfUserIsInvalid() {
    get('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 401
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER011'
    assert json.meta.message == 'Usuario inválido'
  }

  void testShowWaitingListItem() {
    get('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 200
  }

  void testDeleteWaitingListErrorIfUserIsInvalid() {
    delete('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 401
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER011'
    assert json.meta.message == 'Usuario inválido'
  }

  void testDeleteWaitingList() {
    delete('/waiting-list-items') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 200
  }

}
