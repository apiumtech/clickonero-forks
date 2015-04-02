package com.winbits.domain.catalog

class Country {
  String name
  String isoCode
  String currency
  Locale defaultLocale
  String flag

  static constraints = {
    name blank: false, maxSize: 100
    isoCode blank: false, size: 2..5
    currency blank: false, size: 2..5
    flag nullable: true, blank: false
  }
}
