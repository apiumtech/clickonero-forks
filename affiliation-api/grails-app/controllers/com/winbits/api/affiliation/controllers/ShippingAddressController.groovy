package com.winbits.api.affiliation.controllers

import com.winbits.api.clients.mis.affiliation.MisRemoveShippingAddress
import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ShippingAddressController {

  def shippingAddressService
  def misClient
  def authenticationService

  def update(UpdateShippingAddressCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    def user = getAuthenticatedUser()
    user.salesAgentId = authenticationService.getCurrentSalesAgentId(request)
    restpond shippingAddressService.updateShippingAddress(command, user, params)
  }

  def delete(Long id) {
    def user = getAuthenticatedUser()
    def resultado = shippingAddressService.deleteShippingAddress(id, user)

    if (resultado) {
      def eventData = user.toEventBase() + [shippingAddressId: id]
      misClient.logMessage(new MisRemoveShippingAddress(eventData))
    }

    restpond data: [id: resultado.id]
  }
}

class UpdateShippingAddressCommand extends ShippingAddressCommand {
  Long id

  static constraints = {
    importFrom ShippingAddressCommand
    id nullable: false
  }
}
