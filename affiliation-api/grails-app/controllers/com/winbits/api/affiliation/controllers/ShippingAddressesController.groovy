package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.catalog.Country
import com.winbits.domain.catalog.ZipCodeInfo
import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ShippingAddressesController {

  def shippingAddressService
  def authenticationService

  def save(CreateShippingAddressCommand command) {
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    def user = getAuthenticatedUser()
    user.salesAgentId = authenticationService.getCurrentSalesAgentId(request)
    restpond data: shippingAddressService.createShippingAddres(command, user), status: 201
  }

  def show() {
    def user = getAuthenticatedUser()
    paramsSplit(["fields"])

    def resultado = shippingAddressService.getShippingAddresses(params.fields, user)

    restpond resultado
  }
}

class ShippingAddressCommand {

  String firstName
  String lastName
  String lastName2
  String phone
  String street
  String internalNumber
  String externalNumber
  String betweenStreets
  String indications
  String zipCode
  String location
  String county
  String state
  Boolean main = false
  Country country = Country.load(1L)
  ZipCodeInfo zipCodeInfo

  CommonAddress toCommonAddress() {
    new CommonAddress(
        street: street,
        internalNumber: internalNumber,
        externalNumber: externalNumber,
        phone: phone,
        zipCode: zipCode,
        location: location,
        county: county,
        state: state
    )
  }

  static constraints = { }
}

class CreateShippingAddressCommand extends ShippingAddressCommand {

  static constraints = {
    importFrom ShippingAddress
    importFrom CommonAddress
    importFrom ShippingAddressCommand
  }

}
