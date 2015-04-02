package com.winbits.domain.orders
 
import com.winbits.domain.catalog.Bin
import com.winbits.domain.catalog.PaymentMethod
 
import grails.converters.JSON
 
import org.joda.time.DateTime

class OrderPayment {

  BigDecimal amount
  DateTime paidDate
  OrderPaymentStatus status
  String reference
  String paymentCapture

  DateTime dateCreated
  DateTime lastUpdated

  PaymentMethod paymentMethod

  Map paymentCaptureData
  static transients = ["paymentCaptureData"]

  static hasMany = [bins: Bin]
  static belongsTo = [order: Order]

  static constraints = {
    amount min: 0.0, max: 10000000000.0, scale: 2, nullable: true
    reference nullable: true
    paymentCapture nullable: true
    paidDate nullable: true
  }

  static mapping = {
    paymentCapture type:"text"
  }

  def beforeUpdate() {
    if (!paidDate && status.enum == OrderPaymentStatusEnum.PAID) {
      paidDate = DateTime.now()
    }

    if (paymentCaptureData) {
      paymentCapture = (paymentCaptureData as JSON).toString()
    }
  }

  String transactionReference() {
    dateCreated.toString('yyMMddHHmm--') + id.toString()
  }
}
