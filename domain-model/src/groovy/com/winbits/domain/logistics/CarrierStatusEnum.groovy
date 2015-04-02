package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 30/09/13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */

public enum CarrierStatusEnum implements PersistentEnum<CarrierStatus> {
  COMFIRMED(1L),
  EMBARKED(2L),
  COMMITED(3L),



  final Serializable id

  CarrierStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  CarrierStatus getDomain() {
    CarrierStatus.load(id)
  }

  @Override
  boolean equals(CarrierStatus domainInstance) {
    id == domainInstance?.getId()
  }
}

