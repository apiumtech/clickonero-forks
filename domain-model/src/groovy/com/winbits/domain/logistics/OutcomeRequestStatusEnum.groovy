package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 11/8/13
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public enum OutcomeRequestStatusEnum implements PersistentEnum<OutcomeRequestStatus> {
  IN_WAREHOUSE(1L),
  PARTIALLY_STOCKED(2L),
  FULLY_STOCKED(3L),
  CANCELLED(4L),
  LOGISTIC_INCIDENCE(5L)

  final Serializable id

  OutcomeRequestStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  OutcomeRequestStatus getDomain() {
    OutcomeRequestStatus.load(id)
  }

  @Override
  boolean equals(OutcomeRequestStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
