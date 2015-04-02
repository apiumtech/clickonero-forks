package com.winbits.domain.orders

import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.reference.ReferenceCartCode

class CartDetail {

  Integer quantity

  SkuProfile skuProfile
  List<Map> warnings

  static belongsTo = [cart: Cart]
  static hasMany = [referenceCartCodes: ReferenceCartCode]

  static constraints = {
    quantity min: 0
  }

  static mapping = {
    sort id: "desc"
  }
  
  static transients = ['warnings']
}
