package com.winbits.domain.logistics



import com.winbits.domain.Enumable

class CarrierStatus implements Enumable<CarrierStatusEnum> {

  String name
  String description
  Carrier carrier

  static hasMany = [skuOutcomeStatus : SkuOutcomeStatus]

  static transients = ['enum']

  static constraints = {
    name blank: false
    description nullable: true
    carrier nullable: true
  }

  @Override
  CarrierStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      CarrierStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(CarrierStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
