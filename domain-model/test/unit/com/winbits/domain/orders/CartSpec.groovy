package com.winbits.domain.orders

import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ItemGroupProfile
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.utils.DomainModelUtils
import grails.buildtestdata.TestDataConfigurationHolder
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Cart)
@Build([Cart, CartDetail, SkuProfile, ItemGroupProfile])
//@Mock([CartDetail])
class CartSpec extends Specification {

  @Shared Cart cart

  def setup() {
    cart = initializePaidCart()
  }

  private Cart initializePaidCart() {
    User.metaClass.encodePassword = {}
    def cart = Cart.build()
    CartDetail.build(cart: cart)
    CartDetail.build(cart: cart)
    CartDetail.build(cart: cart)
    cart
  }

  @Unroll
  void "cart cashback is correctly"() {
    setup:
    cart.cartDetails.eachWithIndex {CartDetail entry, int i ->
      entry.quantity = quatities[i]
      entry.save(validate:false)
      entry.skuProfile.itemGroupProfile.cashback = cashbacks[i]
      entry.skuProfile.itemGroupProfile.save(validate:false)
    }

    when:
    def cashbackCart = cart.cashback

    then:
    cashbackCart == expectedCashBack

    where:
    cashbacks          | quatities || expectedCashBack
    [10.0, 15.0, 20.0] | [2, 3, 4] || 145.00
    [12.0, 0, 13.0]    | [4, 5, 2] || 74.00
    [10.0, 20.0, 5.0]  | [1, 3, 4] || 90.00

  }
}
