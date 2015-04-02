package com.winbits.api.affiliation.controllers

import com.winbits.controllers.command.PaginateCommand
import grails.plugins.springsecurity.Secured
import org.joda.time.LocalDate

@Secured(['IS_AUTHENTICATED_FULLY'])
class BitsController {
  static allowedMethods = [balance: 'GET', transactions: 'GET']

  def bitsService

  def balance() {
    restpond([balance: bitsService.forUser(getAuthenticatedUser())])
  }

  def transactions(TransactionsCommand command) {
    def respuesta = bitsService.bitsAccountHistory(getAuthenticatedUser(), command)
    restpond( meta:respuesta.meta, data:respuesta.response)
  }
}

class TransactionsCommand extends PaginateCommand {
  LocalDate from
  LocalDate to = LocalDate.now()

  Map filters() {
    [to: to, from: from]
  }
}
