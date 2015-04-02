package com.winbits.domain.migration

import com.winbits.domain.affiliation.User
import org.joda.time.DateTime

class UserBitsTrouble {
  User user
  Long deposit
  String errorDescription
  String concept
  DateTime dateCreated

  static constraints = {
    errorDescription nullable:true
    concept nullable:true
  }
}

