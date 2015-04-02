package com.winbits.domain.tracking

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class TrackingStep implements Enumable<TrackingStepEnum> {
  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  TrackingStepEnum getEnum() {
    def theId = getId()
    if (theId) {
      TrackingStepEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(TrackingStepEnum enumConstant) {
    getId() == enumConstant.id
  }

}

