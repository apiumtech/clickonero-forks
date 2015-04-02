package com.winbits.domain.logistics

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 12/18/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public enum OutcomeTypeEnum implements PersistentEnum<OutcomeType> {

  VENTA_EMPLEADO(1L),
  DEVOLUCION_CONSIGNA(2L),
  PRODUCTO_DANIADO(3L),
  SALIDA_POR_MUESTRA(4L),
  VENTA_CLIENTE(5L)

  final Serializable id

  OutcomeTypeEnum(Serializable id) {
    this.id = id
  }

  @Override
  OutcomeType getDomain() {
    OutcomeType.load(id)
  }

  @Override
  boolean equals(OutcomeType domainInstance) {
    id == domainInstance?.getId()
  }
}
