package com.winbits.domain.logistics

import com.winbits.domain.catalog.Provider
import org.joda.time.LocalDateTime

class Odc {

  Provider provider
  OdcType type
  OdcStatus status
  Warehouse warehouse
  OdcPaymentStatus paymentStatus
  WmsStatus wmsStatus

  LocalDateTime deliveryDate
  String paymentConditions
  BigDecimal totalRequired   //Monto original de la ODC
  BigDecimal totalReceived   //Monto real de la Odc con base en lo recibido
  Integer paymentMaxDays
  IncomeType incomeType
  LocalDateTime dateCreated
  LocalDateTime lastUpdated

  String userSupplyOpen
  String userSupplyClose

  static hasMany = [odcDetails: OdcDetail, wmsErrors: WmsError]

  static constraints = {
    warehouse nullable: true
    deliveryDate nullable: true
    paymentConditions nullable: true
    totalReceived nullable: true
    paymentMaxDays nullable: true
    wmsStatus nullable: true
    userSupplyOpen  nullable: true
    userSupplyClose  nullable: true
  }
}
