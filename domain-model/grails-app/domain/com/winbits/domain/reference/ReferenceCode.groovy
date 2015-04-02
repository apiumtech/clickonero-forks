package com.winbits.domain.reference

class ReferenceCode {
  String code
  ReferenceCodeStatus status

  static constraints = {
    code minSize: 1, maxSize: 250
  }
}
