package com.winbits.bits

class BitsPaymentPendingService {

  def publishMessageService

  def processBitsPaymentPending(def orderNumber){
    def concept = "Compra de la orden ${orderNumber}"
    def withdrawal = Withdrawal.createCriteria().list {
      eq('refunded',false)
      transaction{
        eq('concept', concept)
        lt('amount',0 as BigDecimal)
      }
    }.first()

    if(withdrawal){
      BigDecimal orderNumberBigDecimal = new BigDecimal(orderNumber.split("--")[1])
      Map mapResponse = [
              balance: withdrawal.transaction.balance,
              transactionId: withdrawal.transaction.id,
              orderNumber: orderNumberBigDecimal]
      log.info "Publish message to pass to paid order ${mapResponse}"
      publishMessageService.publishResponseMessageToOrders(mapResponse)
    }
  }
}
