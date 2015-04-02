package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 27/11/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public enum WmsStatusEnum implements PersistentEnum<WmsStatus> {
  PENDING(1L),
  SUCCESS(2L),
  ERROR(3L)

  final Serializable id

  WmsStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  WmsStatus getDomain() {
    WmsStatus.load(id)
  }

  @Override
  boolean equals(WmsStatus domainInstance) {
    id == domainInstance?.getId()
  }
}

