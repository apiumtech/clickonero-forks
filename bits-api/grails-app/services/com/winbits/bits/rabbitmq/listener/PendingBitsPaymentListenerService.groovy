package com.winbits.bits.rabbitmq.listener

class PendingBitsPaymentListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'ordersPaymentBitsPending'
  static transactional = false

  def bitsPaymentPendingService

  @Override
  void processMessage(Map message) {
    log.info "Procesing rabbit's message to check orders pending"
    message.ordersNumber?.each {
      bitsPaymentPendingService.processBitsPaymentPending(it)
    }

  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }
}
