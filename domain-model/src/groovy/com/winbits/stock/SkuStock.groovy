package com.winbits.stock

import com.winbit.stock.impl.SkuRegular
import com.winbits.domain.catalog.Sku
import com.winbits.stock.impl.SkuInfinite

abstract class SkuStock {
  Integer calculateStock (Sku sku, Integer quantity){
    sku.quantityReserved + quantity
  }

  final static SkuStock createSkuStock (Integer quantityOnHand){
    if (quantityOnHand == -1){ 
      return new SkuInfinite ()
    }

    new SkuRegular ()  
  }
}
