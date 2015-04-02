package com.winbits.domain.affiliation

import com.winbits.domain.admin.AdminUser
import com.winbits.domain.common.Department

class SalesAgent {

  Integer salesCharge
  String apiToken
  Boolean enabled

  static belongsTo = [adminUser : AdminUser]
  static mapping = {
    version false
  }

  static constraints = {
    apiToken nullable: true
   salesCharge nullable: true
  }
}
