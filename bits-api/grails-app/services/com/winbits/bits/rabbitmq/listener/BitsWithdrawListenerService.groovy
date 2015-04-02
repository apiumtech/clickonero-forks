package com.winbits.bits.rabbitmq.listener

import com.winbits.bits.WithdrawBitCommand

class BitsWithdrawListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'bitsWithdraw'
  static transactional = false

  def accountService

  @Override
  void processMessage(Map message) {
    WithdrawBitCommand command = new WithdrawBitCommand()

    command.properties = message

    accountService.withdrawBitsByRabbit(command)
  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }
}
