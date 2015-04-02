package com.winbits.domain.sms

import com.winbits.domain.PersistentEnum

public enum UserMobileCarrierEnum implements PersistentEnum<UserMobileCarrier> {
  TELCEL(1L),
  MOVISTAR(2L),
  IUSACELL(3L),
  UNEFON(4L),
  VIRGINMOBILE(5L)

  final Serializable id

    UserMobileCarrierEnum(Serializable id) {
    this.id = id
  }

  @Override
  UserMobileCarrier getDomain() {
    UserMobileStatus.load(id)
  }

  @Override
  boolean equals(UserMobileCarrier domainInstance) {
    id == domainInstance?.getId()
  }
}