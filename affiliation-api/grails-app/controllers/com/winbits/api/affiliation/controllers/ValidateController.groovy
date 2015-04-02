package com.winbits.api.affiliation.controllers

import grails.converters.JSON

import java.util.regex.Pattern
import com.winbits.domain.catalog.ZipCodeInfo

class ValidateController {

  final static Pattern ZIP_CODE_REGEXP = ~/\d{5}/

  def zipCode() {
    def zipCode = params.postalCode ?: params.zipCode
    def zipCodeCount = 0
    if (ZIP_CODE_REGEXP.matcher(zipCode ?: '')) {
      zipCodeCount = ZipCodeInfo.countByZipCode(zipCode)
    }

    render (zipCodeCount > 0) as JSON
  }
}
