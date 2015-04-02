package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.ZipCodeInfoCommand
import com.winbits.domain.catalog.ZipCodeInfo

class ZipCodeInfoService {

  def listLocations(ZipCodeInfoCommand cmd) {
    ZipCodeInfo.findAllByZipCode(cmd.zipCode)
  }
}
