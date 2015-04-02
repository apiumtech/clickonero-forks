package com.winbits.domain.orders

import com.winbits.domain.admin.AdminUser
import org.joda.time.DateTime

class RefundCommentHistory implements Serializable{

  DateTime dateCreated
  String comment
  AdminUser user

  static belongsTo = [refundDetail : RefundDetail]

  static constraints = {
    comment maxSize:250

  }
}
