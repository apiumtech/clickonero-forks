package com.winbits

import com.winbits.orders.utils.OrderDataUtils
import grails.plugin.spock.IntegrationSpec

/**
 * Created by manuel on 2/10/14.
 */
class StockServiceIntegrationSpec extends IntegrationSpec {

  def stockService

  void "spend stock when somebody did an extra output"() {
    setup:
    def sku = OrderDataUtils.createSkuProfile().sku
    sku.quantityOnHand = quantityOnHand
    sku.quantityReserved = quantityReserved
    sku.save(flush: true)

    when:
    def skuTmp = stockService.spendStockForExtraOutput(sku.id, quantityExtraOutput)

    then:
    skuTmp
    skuTmp.quantityReserved == quantityExtraOutput + quantityReserved

    where:
    quantityExtraOutput | quantityOnHand | quantityReserved
            5           |       10       |         0
            5           |       10       |         5
            4           |       10       |         1
           10           |       10       |        10
            1           |        9       |         9
  }

  void "testing with infinite stock should assign" (){
    setup:
    def sku = OrderDataUtils.createSkuProfile ().sku
    sku.quantityOnHand = -1
    sku.quantityReserved = 0
    sku.save (flush:true)
    when:
    Map response = stockService.reserveStockForSkuAndQuantity (sku.id, 1)
    
    then:
    assert response
    assert response.message == stockService.REPLY_STOCK_ASIGNED

    
  }
  
  void "testing with infinite stock and a big amount of quantity reserved" (){
    setup:
    def sku = OrderDataUtils.createSkuProfile ().sku
    sku.quantityOnHand = -1
    sku.quantityReserved = 3000
    sku.save (flush:true)
    when:
    Map response = stockService.reserveStockForSkuAndQuantity (sku.id, 4)
    
    then:
    assert response
    assert response.message == stockService.REPLY_STOCK_ASIGNED

    
  }


}
