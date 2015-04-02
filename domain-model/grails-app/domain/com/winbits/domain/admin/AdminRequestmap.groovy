package com.winbits.domain.admin

class AdminRequestmap {

  String url
  String configAttribute
  String description
  static constraints = {
    url blank: false, unique: true
    configAttribute blank: false
  }
}
