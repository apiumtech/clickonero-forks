package com.winbits.domain.logistics

import org.joda.time.LocalDateTime

abstract class OutcomeRequestParent {

  LocalDateTime dateCreated
  LocalDateTime lastUpdated
  WmsStatus wmsStatus
  OutcomeType type

  static mapping = {
  }

  static hasMany = [wmsErrors: WmsError]
}
