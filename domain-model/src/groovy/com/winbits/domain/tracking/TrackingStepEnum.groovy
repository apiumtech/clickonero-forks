package com.winbits.domain.tracking

import com.winbits.domain.PersistentEnum

public enum TrackingStepEnum implements PersistentEnum<TrackingStep> {
  REGISTER(1L), 
  LOGIN(2L), 
  PAYMENT(3L)
  
  final Serializable id

  TrackingStepEnum(Serializable id) {
    this.id = id
  }

  @Override
  TrackingStep getDomain() {
    TrackingStep.load(id)
  }

  @Override
  boolean equals(TrackingStep domainInstance) {
    id == domainInstance?.getId()
  }
}
