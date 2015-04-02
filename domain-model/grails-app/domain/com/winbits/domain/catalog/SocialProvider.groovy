package com.winbits.domain.catalog

class SocialProvider {
  String name
  String logo
  String providerId
  Boolean active

  static constraints = {
    name blank: false, maxSize: 75
    logo blank: false
    providerId blank: false
  }

  static mapping = {
    providerId column: 'providerId'
  }

  static transients = ['active']

}
