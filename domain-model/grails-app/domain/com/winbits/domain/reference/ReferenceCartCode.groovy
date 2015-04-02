package com.winbits.domain.reference

class ReferenceCartCode {
  String code

  static constraints = {
    code minSize: 1, maxSize: 250
  }
}
