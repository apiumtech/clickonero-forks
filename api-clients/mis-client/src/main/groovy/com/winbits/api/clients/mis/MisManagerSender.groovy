package com.winbits.api.clients.mis
/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/25/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
interface MisManagerSender {
  String SENDING_QUEUE = 'misLogEvent'

  boolean sendRabbitLog(String message)
}
