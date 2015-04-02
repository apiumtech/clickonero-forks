package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum SkuOutcomeStatusEnum implements PersistentEnum<SkuOutcomeStatus> {
  IN_WAREHOUSE(1L),
  PACKAGED(2L),
  IN_ROUTE(3L),
  BACKLOG(4L),
  CANCEL(5L),
  PICKING(6L),
  PACKING(7L),
  IN_CARRIER(8L),
  OUTPUT_EXTRA(9L),
  COLLECTED(10L)

  final Serializable id

  SkuOutcomeStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  SkuOutcomeStatus getDomain() {
    SkuOutcomeStatus.load(id)
  }

  @Override
  boolean equals(SkuOutcomeStatus domainInstance) {
    id == domainInstance?.getId()
  }
}
