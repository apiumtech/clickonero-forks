package com.winbits.domain.orders

import com.winbits.domain.catalog.PaymentMethod
import org.apache.commons.lang.builder.HashCodeBuilder
import org.joda.time.DateTime

class RefundDetail implements Serializable {

  DateTime dateCreated
  DateTime lastUpdated

  String userAtc
  String userFinancial

  RefundStatus status

  DateTime paymentDate
  BigDecimal amount
  String bankName
  String accountUser
  String clabe
  String accountNumber
  String reference
  String cause
  String urlPaymentProof
  boolean deleted

  static hasOne = [paymentMethod: PaymentMethod]
  static belongsTo = [orderDetail : OrderDetail]
  static hasMany= [commentHistory : RefundCommentHistory]

  static constraints = {
    amount min: 0.0, max: 10000000000.0, scale: 2, nullable: true
    userAtc maxSize: 100
    userFinancial nullable: true, maxSize: 100
    paymentDate nullable: true
    bankName nullable: true
    accountNumber nullable: true
    clabe nullable: true
    accountUser nullable: true
    reference nullable:true
    deleted nullable: false, defaultValue: true
    cause nullable: false, blank: false, maxSize : 250
    urlPaymentProof nullable: true
  }

  static mapping = {
    id composite: ['paymentMethod', 'orderDetail']
  }

  @Override
  boolean equals(other) {
    if (!(other instanceof RefundDetail)) {
      return false
    }

    other.orderDetail == orderDetail && other.paymentMethod == paymentMethod
  }

  @Override
  int hashCode() {
    def builder = new HashCodeBuilder()
    builder.append orderDetail
    builder.append paymentMethod
    builder.toHashCode()
  }
}
