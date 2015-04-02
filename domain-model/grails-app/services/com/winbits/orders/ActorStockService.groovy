package com.winbits.orders
 
import com.winbits.domain.catalog.Sku
 
import grails.converters.*
 
import groovyx.gpars.actor.Actors

class ActorStockService {
    
    def stockService

    final def reservedStock4Sku = Actors.actor{
        loop {
            react { message ->
                try{
                    Map response = stockService.reserveStockForSkuAndQuantity(
                        message.skuId, message.quantity)
                reply (response: response)
                }catch(Exception e){
                    e.printStackTrace()
                    log.error("Paso algo feo",e) 
                    reply ([message: stockService.REPLY_ERROR_TO_RESERVED_STOCK])
                }
           }
        }
    }

    final def reservedStock4SkuProfile = Actors.actor{
        loop {
            react { message ->
                try{
                    stockService.reservingSkuProfileStockForQuantity(
                        message.skuProfileId, message.quantity)
                }catch(Exception e){
                    log.error("Paso algo feo",e) 
                }
                reply "Finish"
            }
        }
    }

    final def releaseStock4Sku = Actors.actor{
        loop{
            react{message ->
                try{
                    stockService.releaseStockForSkuWithQuantity(
                        message.skuId, message.quantity)
                }catch(Exception e){
                    log.error("Pasa algo feo",e)
                }
                reply "Finish"
            }
        }
    }


    final def releaseStock4SkuProfile = Actors.actor{
        loop{
            react{message ->
                try{
                    stockService.releaseStockForSkuProfileWithQuantity(
                        message.skuProfileId, message.quantity)
                }catch(Exception e){
                    log.error("Pasa algo feo",e)
                }
                reply "Finish"
            }
        }
    }
}
