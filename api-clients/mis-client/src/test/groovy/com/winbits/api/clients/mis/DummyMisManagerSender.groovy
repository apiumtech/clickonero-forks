package com.winbits.api.clients.mis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 28/02/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Component('misManagerSender')
class DummyMisManagerSender implements MisManagerSender {

  private static Logger log = LoggerFactory.getLogger(DummyMisManagerSender)

  @Override
  boolean sendRabbitLog(String message) {
    log.info("Message sent logged instead of sent to RabbitMQ\n${message}")
    true
  }
}
