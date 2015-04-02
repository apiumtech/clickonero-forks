package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.InvalidUserException

class OrdersController {

  def ordersService

  def show(FindCommand cmd) {
    def user = getAuthenticatedUser()
    if (user) {
      def countOrdersForUser = ordersService.countByUserAndFilter(user, cmd)
      def ordersForUser = ordersService.findAllByUserAndFilter(user, cmd)
      def ticketPayments = ordersService.findAllTicketPaymentsInOrders(ordersForUser)
      def ticketPaymentsByOrderId = ticketPayments.groupBy {it.orderId}
      restpond (data: ordersForUser.each {
        it.metaClass.ticketPayments = ticketPaymentsByOrderId[it.id] ?: []
      }, meta: [totalCount: countOrdersForUser])
    } else {
      throw new InvalidUserException()
    }
  }

  def pendingItems(){
    def user = getAuthenticatedUser()
    if(user){
      def pendingSkuOutcome = ordersService.countOutcomePending(user)
      restpond data: [pendingItems:pendingSkuOutcome]
    }else{
      throw new InvalidUserException()
    }
  }
}

class FindCommand {
  Long status
  String sort
  Integer offset
  Integer max

  static constraints = {
    status nullable: true
    sort nullable: true
    offset nullable: true
    max nullable: true
  }
}
