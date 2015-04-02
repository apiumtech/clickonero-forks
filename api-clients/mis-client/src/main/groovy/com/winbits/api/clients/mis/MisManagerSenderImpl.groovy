package com.winbits.api.clients.mis

import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 28/02/14
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
class MisManagerSenderImpl implements MisManagerSender {
  private static Logger log = LoggerFactory.getLogger(MisManagerSenderImpl)
  @Autowired
  RabbitTemplate rabbitTemplate

  @Autowired
  Gson misGson

  @Override
  boolean sendRabbitLog(String message)
  {
    boolean  result = false
    try {

      rabbitTemplate.convertAndSend(SENDING_QUEUE, message)
      result = true

    }
    catch (e)
    {
      log.warn e.message


      //sendToErrorMessage(SENDING_QUEUE, message, e)
    }
    result
  }


  private void sendToErrorMessage(String queue, String message, Throwable cause)
  {
    def deadMessageQueue = getDeadMessage(queue)
    def deadMessage = misGson.toJson([message:message, cause:cause.message, wneh: new Date()])

    try {
      rabbitTemplate.convertAndSend(deadMessageQueue, deadMessage)
    }
    catch (e)
    {
      log.warn e.message

    }
  }

  private String getDeadMessage(String queue )
  {
    "${queue}DeadMessage"
  }
}
