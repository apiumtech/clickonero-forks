package com.winbits.bits.rabbitmq.listener

import com.winbits.bits.RewardRegisterCommand

class BitsRewardRegisterListenerService extends AbstractBaseQueueListener {
  static rabbitQueue = 'bitsRewardRegister'
  static transactional = false

  def accountService

  @Override
  void processMessage(Map message) {
    RewardRegisterCommand command = new RewardRegisterCommand()

    command.properties = message

    accountService.registerRewardByRabbit(command)
  }

  @Override
  String rabbitQueue() {
    return rabbitQueue
  }
}
