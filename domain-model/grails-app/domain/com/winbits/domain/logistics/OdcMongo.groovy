package com.winbits.domain.logistics

import org.joda.time.LocalDateTime

class OdcMongo {

  String inputId
  String outputId
  String requestId
  Odc odc
  WmsStatus wmsStatus
  LocalDateTime dateCreated
  LocalDateTime lastUpdated

  static hasMany = [wmsErrors: WmsError]

  static constraints = {
    outputId nullable: true
  }
}
