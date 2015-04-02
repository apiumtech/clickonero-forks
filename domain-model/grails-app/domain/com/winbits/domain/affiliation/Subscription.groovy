package com.winbits.domain.affiliation

class Subscription {
  Vertical vertical

  static hasMany = [profiles: Profile]
  static belongsTo = [Profile]

  static constraints = {
  }
}
