package com.winbits.domain.sms

import com.winbits.domain.PersistentEnum
import com.winbits.domain.sms.UserMobileStatus


public enum UserMobileStatusEnum implements PersistentEnum<UserMobileStatus> {
  WAIT(1L),
  CANCELLED(2L),
  ACTIVE(3L),
  CHANGE(4L)

  final Serializable id

  UserMobileStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  UserMobileStatus getDomain() {
    UserMobileStatus.load(id)
  }

  @Override
  boolean equals(UserMobileStatus domainInstance) {
    id == domainInstance?.getId()
  }
}