package com.winbits.bits.rabbitmq.listener

import com.winbits.bits.TransferBitCommand

class BitsTranferListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'bitsTransfer'
  static transactional = false

  def accountService

  @Override
  void processMessage(Map message) {
    TransferBitCommand command = new TransferBitCommand()

    command.properties = message

    accountService.transferBitsByRabbit(command)
  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }

}

