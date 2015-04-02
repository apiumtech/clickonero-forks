package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

public enum OdcTypeEnum implements PersistentEnum<OdcType> {
  PRE_ALERTA(1L),
  ODC(2L)
//  FIRME(3L), // es ODC
//  VIRTUAL(4L), // es ODC
//  CONSIGNA(5L) // es pre-alerta


  final Serializable id

  OdcTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  OdcType getDomain() {
    OdcType.load(id)
  }

  @Override
  boolean equals(OdcType domainInstance) {
    id == domainInstance?.getId()
  }
}
