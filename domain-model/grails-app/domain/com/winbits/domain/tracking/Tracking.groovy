package com.winbits.domain.tracking

import com.winbits.domain.affiliation.User
import org.joda.time.DateTime

class Tracking {

  // UTMs fields
  String campaign
  String source
  String medium
  String content
  String term

  // System fields
  User user
  String code
  TrackingStep step

  DateTime dateCreated

  static constraints = {
    campaign blank: false
    medium   blank: false
    content  nullable: true
    source   nullable: true
    code     nullable: true
    term     nullable: true
  }

  /* Mappings */
  static mapping = {
    version false
  }

  static Tracking fromUTMs(Map utms) {
    def finalUTMs = utms.subMap(['campaign', 'source', 'medium', 'content', 'term'])
    new Tracking(finalUTMs)
  }
}
