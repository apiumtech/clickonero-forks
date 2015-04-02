package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.WishListItem
import com.winbits.domain.catalog.Brand
import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 7/16/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
class WishListItemFunctionalTests  extends FunctionalTestCase {

  void testDeleteWishListItemErrorBrandNotFound() {

    delete("/wish-list-items/-5") {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }

    assertStatus 404
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER017'
    assert json.meta.message == 'La marca no se encuentra'
  }

  void testDeleteWishListItem() {
    def vertical = Vertical.get(1)
    def brand1 = new Brand(name: 'NikeTest01', logo: 'http://s3.amazon.com/media.winbits.com/brands/nike.jpg',
        vertical: vertical).save()
    def brand2 = new Brand(name: 'NikeTest02', logo: 'http://s3.amazon.com/media.winbits.com/brands/nike.jpg',
        vertical: vertical).save()
    def user = User.findByApiToken('W1nb1tsT3st')
    new WishListItem(user: user, brand: brand1).save(flush: true)
    new WishListItem(user: user, brand: brand2).save(flush: true)

    delete("/wish-list-items/${brand1.id}") {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
    }

    assertStatus 200
    def json = JSON.parse(response.getContentAsString())
    assert json
    def response = json.response
    assert response
    def brandSaved1 = response.find { it.id == brand1.id}
    assert !brandSaved1
    def brandSaved2 = response.find { it.id == brand2.id}
    assert brandSaved2
    assert brandSaved2.name == brand2.name

  }

}
