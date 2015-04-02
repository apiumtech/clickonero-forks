package com.winbits.domain.messaging

import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderPayment
import grails.converters.JSON

class MessagingService {

  def publishPostProcessPaymentMessage(OrderPayment payment) {
    def message = [id: payment.id]
    publishMessage('postprocess.payments', payment.paymentMethod.identifier, message)
  }

  def publishOrderHasBeenPaidMessage(Order order) {
    def message = [id: order.id]
    publishMessage('paid.orders', '', message)
  }

  void publishMessage(String routingKey, Map message) {
    publishMessage('', routingKey, message)
  }

  void publishMessage(String exchange, String routingKey, Map message) {
    def jsonMessage = (message as JSON).toString()
    try {
      rabbitSend(exchange, routingKey, jsonMessage)
    } catch (e) {
      log.error("Failed to publish message ${jsonMessage}", e)
    }
  }
}
