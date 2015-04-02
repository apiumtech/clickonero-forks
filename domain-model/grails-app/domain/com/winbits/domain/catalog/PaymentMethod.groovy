package com.winbits.domain.catalog

import org.joda.time.DateTime

class PaymentMethod {

  String identifier
  Boolean active = true
  BigDecimal maxAmount
  BigDecimal minAmount
  Integer displayOrder
  String logo

  Integer expireMinutes
  Boolean offline

  DateTime dateCreated
  DateTime lastUpdated

  def paymentMethodService
  static transients = ["paymentMethodService"]

  static constraints = {
    maxAmount min : 0.0, max:10000000000.0, scale: 2
    minAmount min : 0.0, max:10000000000.0, scale: 2
    logo nullable: true, url: true
  }

  static mapping = {
    displayOrder sqlType:'smallint', min: 0
  }

  static hibernateFilters = {
    enabledFilter(condition: 'active=1', default: true)
  }

}
