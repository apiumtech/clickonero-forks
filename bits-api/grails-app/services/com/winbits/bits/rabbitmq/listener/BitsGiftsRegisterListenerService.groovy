package com.winbits.bits.rabbitmq.listener

import com.winbits.bits.GiftRegisterCommand

class BitsGiftsRegisterListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'bitsGiftRegister'
  static transactional = false

  def accountService

  @Override
  void processMessage(Map message) {

    message.items?.each {
      GiftRegisterCommand command = new GiftRegisterCommand()
      command.properties = it
      accountService.registerGiftByRabbit(command)
    }
  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }
}
