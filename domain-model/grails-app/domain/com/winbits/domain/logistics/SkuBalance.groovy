package com.winbits.domain.logistics

import com.winbits.domain.catalog.Sku
import org.apache.commons.lang.builder.HashCodeBuilder

class SkuBalance implements  Serializable{

  Integer balance = 0

  static belongsTo = [sku: Sku, warehouse: Warehouse]

  static constraints = {
  }

  static  mapping = {
    id composite: ['sku', 'warehouse']
  }

  @Override
  boolean equals(other) {
    if (!(other instanceof SkuBalance)) {
      return false
    }

    other.sku == sku && other.warehouse == warehouse
  }

  @Override
  int hashCode() {
    def builder = new HashCodeBuilder()
    builder.append sku
    builder.append warehouse
    builder.toHashCode()
  }
}
