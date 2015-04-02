package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 19/11/13
 * Time: 04:30 PM
 * To change this template use File | Settings | File Templates.
 */
public enum WarehouseTypeEnum implements PersistentEnum<WarehouseType> {
  PROVIDER(1L),
  HUB(2L),
  VIRTUAL(3L)

  final Serializable id

  WarehouseTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  WarehouseType getDomain() {
    WarehouseType.load(id)
  }

  @Override
  boolean equals(WarehouseType domainInstance) {
    id == domainInstance?.getId()
  }

}
