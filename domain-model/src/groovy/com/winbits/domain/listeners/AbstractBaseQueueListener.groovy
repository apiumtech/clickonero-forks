package com.winbits.domain.listeners

import grails.converters.JSON
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractBaseQueueListener {

  private static Logger log = LoggerFactory.getLogger(AbstractBaseQueueListener)

  abstract void processMessage(Map message)

  abstract String rabbitQueue()

  void handleMessage(String message) {
    try {
      convertAndProcessMessage(message)
    } catch (e) {
      log.error('Error in message {}', message, e)
      sendToDeadLetter(message, e)
    }
  }

  private void convertAndProcessMessage(String message) {
    def json = JSON.parse(message)
    if(doValidateMessage(json)){
      processMessage(json)
    } else {
      log.warn('Discarding invalid message! {}', message)
    }
  }

  private void sendToDeadLetter(String message, Throwable cause) {
    def deadMessage = ([queue: rabbitQueue(),message: message, when: DateTime.now(), cause: cause.message] as JSON).toString()
    try {
      log.info("Sending message to DeadLetter ${cause.message}")
      rabbitSend(deadLetterQueue(), deadMessage)
    } catch (e) {
      log.error('Failed to send message to dead letter {}', deadMessage, e)
    }
  }

  boolean doValidateMessage(Map json){
    try {
      validateMessage(json)
    } catch(e) {
      log.warn("Exception ocurred while validating message, message marked as invalid! ${json}", e)
      false
    }
  }

  boolean validateMessage(Map message) {
    true
  }

  String deadLetterQueue() {
    "${rabbitQueue()}DeadLetter"
  }
}
