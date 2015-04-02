package com.winbits.domain.logistics

import org.joda.time.LocalDateTime

class WmsError {

  String description
  LocalDateTime dateCreated

  static mapping = {
    version false
  }

  static constraints = {
  }
}
