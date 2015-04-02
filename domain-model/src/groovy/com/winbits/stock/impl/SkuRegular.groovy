package com.winbit.stock.impl

import com.winbits.catalog.Sku
import com.winbits.domain.catalog.Sku
import com.winbits.stock.SkuStock

class SkuRegular extends SkuStock{
  
  Boolean verifyStockAvailable (Integer quantityOnHand, Integer quantityReserved){
    quantityOnHand - quantityReserved <= 0
  }
  
  Boolean stockAvailable (Integer quantityOnHand, Integer quantityReserved, Integer quantity){
    Integer tempQuantityReserved = quantityReserved + quantity
    quantityOnHand < tempQuantityReserved
  }

  Integer obtainStockAvailable (Sku sku){
    sku.quantityOnHand - sku.quantityReserved
  }
}
