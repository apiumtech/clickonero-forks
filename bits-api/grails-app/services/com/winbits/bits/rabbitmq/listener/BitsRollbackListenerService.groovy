package com.winbits.bits.rabbitmq.listener

import com.winbits.bits.RefundBitsCommand

class BitsRollbackListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'bitsRollback'
  static transactional = false

  def accountService

  @Override
  void processMessage(Map message) {
    RefundBitsCommand command = new RefundBitsCommand()

    command.properties = message

    accountService.rollbackByRabbit(command)
  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }
}
