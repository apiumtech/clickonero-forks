package com.winbits

import com.winbit.stock.impl.SkuRegular
import com.winbits.domain.catalog.Sku
import com.winbits.domain.catalog.SkuProfile
import com.winbits.stock.SkuStock

class StockService {
  static final String REPLY_STOCK_ASIGNED = "Stock asigned"
  static final String REPLY_NOT_STOCK_AVAILABLE = "Not stock available"
  static final String REPLY_STOCK_RESERVED_TEMP = "Not enough stock"
  static final String REPLY_ERROR_TO_RESERVED_STOCK = "Error"

  Map reserveStockForSkuAndQuantity(Long skuId, Integer quantity) {
    Sku sku = Sku.lock(skuId)
    def skuStock = SkuStock.createSkuStock (sku.quantityOnHand)
    if (skuStock instanceof SkuRegular){
      if (skuStock.verifyStockAvailable (sku.quantityOnHand, sku.quantityReserved)){ 
        return [message: REPLY_NOT_STOCK_AVAILABLE]  
      }

      if (skuStock.stockAvailable (sku.quantityOnHand, sku.quantityReserved, quantity)){
        Integer quantityAvailable = skuStock.obtainStockAvailable (sku)      
        sku.quantityReserved += quantityAvailable
        sku.save ()
        return [message: REPLY_STOCK_RESERVED_TEMP, quantityAvailable: quantityAvailable]
      }
    }  
    sku.quantityReserved = skuStock.calculateStock (sku, quantity)
    sku.save ()

    [message:REPLY_STOCK_ASIGNED]
  }

  SkuProfile reservingSkuProfileStockForQuantity(Long skuProfileId, Integer quantity) {
    SkuProfile skuProfile = SkuProfile.lock(skuProfileId)
    skuProfile.quantityReserved += quantity
    
    skuProfile.save()
  }

  Sku releaseStockForSkuWithQuantity(Long skuId, Integer quantity) {
    Sku sku = Sku.lock(skuId)
    if( quantity > sku.quantityReserved ) {
      sku.quantityReserved = 0
    } else {
      sku.quantityReserved -= quantity
    }
    sku.save()
  }

  SkuProfile releaseStockForSkuProfileWithQuantity(Long skuProfileId, Integer releaseQuantity){
    SkuProfile skuProfile = SkuProfile.lock(skuProfileId)
    if(releaseQuantity > skuProfile.quantityReserved ) {
      skuProfile.quantityReserved = 0
    } else {
      skuProfile.quantityReserved -= releaseQuantity
    }
    skuProfile.save()
  }

  Sku spendStockForExtraOutput(Long skuId, Integer quantity) {
    Sku sku = Sku.lock(skuId)
    sku.quantityReserved = sku.quantityReserved + quantity
    sku.save()
  }

}
