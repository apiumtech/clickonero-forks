package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: emmanuel
 * Date: 28/11/13
 * Time: 04:36 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SkuIncomeExtraTypeEnum implements PersistentEnum<SkuIncomeExtraType> {
  DEVOLUCIONES(1L),
  PROMO_PROVEEDOR(2L),
  MUESTRAS(3L),
  OTRA(4L)


  final Serializable id

  SkuIncomeExtraTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  SkuIncomeExtraType getDomain() {
    WarehouseType.load(id)
  }

  @Override
  boolean equals(SkuIncomeExtraType domainInstance) {
    id == domainInstance?.getId()
  }
}
