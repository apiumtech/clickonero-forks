package com.winbits.domain.logistics

import com.winbits.domain.admin.AdminUser
import org.joda.time.LocalDateTime

class OutcomeRequestExtra extends OutcomeRequestParent {

  String receiver
  AdminUser approver

  static hasMany = [skuOutcomeExtras: SkuOutcomeExtra]

  static constraints = {
  }
  static mapping = {
    skuOutcomeExtras column: 'extra_outcome_request_id'
  }
}
