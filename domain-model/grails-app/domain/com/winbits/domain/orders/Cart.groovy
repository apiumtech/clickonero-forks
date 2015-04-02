package com.winbits.domain.orders

import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.PaymentMethod
import org.joda.time.DateTime

class Cart {

  BigDecimal itemsTotal
  BigDecimal shippingTotal
  BigDecimal bitsTotal = 0.0
  Integer itemsCount

  User user

  DateTime dateCreated
  DateTime lastUpdated

  List paymentMethods

  List<CartDetail> cartDetails
  List<CartDetail> failedCartDetails
  static hasMany = [cartDetails: CartDetail]
  static transients = ['paymentMethods', 'cashback', 'failedCartDetails']

  static constraints = {
    itemsTotal(min: 0.0)
    shippingTotal(min: 0.0)
    bitsTotal(min: 0.0)
    itemsCount(min: 0)
  }

  static mapping = {
    bitsTotal sqlType: 'MEDIUMINT'
    itemsCount sqlType: 'SMALLINT'
    itemsTotal precision: 13
    shippingTotal precision: 13
    cartDetails cascade: 'all-delete-orphan'
  }

  BigDecimal getCashback() {
    cartDetails?.sum(0) {
      (it.skuProfile?.itemGroupProfile?.cashback ?: 0) * it.quantity
    } ?: 0
  }

  String toVirtualCartHeader() {
    toVirtualCartContents(cartDetails)
  }

  static String toVirtualCartContents(Collection<CartDetail> cartDetails) {
    def sb = new StringBuilder('[')
    sb.append cartDetails.collect { "{'${it.skuProfile.id}':${it.quantity}" }.join(',')
    sb.append(']').toString()
  }

  void addToFailedCartDetails(CartDetail cartDetail) {
    failedCartDetails = failedCartDetails ?: []
    failedCartDetails << cartDetail
  }
}
