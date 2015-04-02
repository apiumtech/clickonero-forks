package com.winbits.api.affiliation.services

import org.apache.commons.lang.RandomStringUtils

class CodeGeneratorService {

  def generateReferredCode() {
    RandomStringUtils.randomAlphanumeric(10).toUpperCase()
  }
}
