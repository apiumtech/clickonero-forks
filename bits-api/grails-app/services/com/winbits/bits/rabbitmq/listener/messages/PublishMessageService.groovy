package com.winbits.bits.rabbitmq.listener.messages

import grails.converters.JSON

class PublishMessageService {

    def publishResponseMessageToOrders(Map message) {
      def jsonMessage = (message as JSON).toString()
      try {
          rabbitSend('', 'responseChangeStatusOrder', jsonMessage)
      } catch (e) {
          log.error("Failed to publish message ${jsonMessage}", e)
      }
    }
}
