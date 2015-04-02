package com.winbits.stock.impl

import com.winbits.domain.catalog.Sku
import com.winbits.stock.SkuStock

class SkuInfinite extends SkuStock{
  Integer calculateStock (Sku sku, Integer quantity){
    sku.quantityReserved + quantity
  }
}
