package com.winbits.domain.sms

import com.winbits.domain.affiliation.User

class UserMobile {

  String mobilePhone
  String activationCode
  UserMobileStatus userMobileStatus
  UserMobileCarrier carrier

  Date dateCreated
  Date lastUpdated

  static belongsTo = [user : User]

  static constraints = {
    mobilePhone nullable: false, maxSize: 10
    user nullable: false
    activationCode nullable: true , maxSize: 5
    userMobileStatus nullable: false
    carrier nullable: false
  }

}
