package com.winbits.api.affiliation.controllers

import com.winbits.exceptions.api.client.EntityValidationException

class LocationsController {
  def zipCodeInfoService
  /**
   * Obtiene las localidades de un código postal
   */
  def show(ZipCodeInfoCommand cmd) {
    if (!cmd.validate()) {
      throw new EntityValidationException(cmd.errors)
    }
    def res = zipCodeInfoService.listLocations(cmd)
    restpond res
  }
}
class ZipCodeInfoCommand {
  String zipCode
  static constraints = {
    zipCode nullable: false, blank: false, matches: "[0-9]{5}"
  }
}
