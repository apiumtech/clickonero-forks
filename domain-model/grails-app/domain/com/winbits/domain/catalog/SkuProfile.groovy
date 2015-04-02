package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import org.joda.time.DateTime

class SkuProfile {

  BigDecimal fullPrice
  BigDecimal price

  Integer quantityOnHand = -1
  Integer quantityReserved = 0

  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false

  Vertical vertical

  ItemStatus status

  ItemGroupProfile itemGroupProfile

  static belongsTo = [sku: Sku]

  static transients = ['avaliable', 'available']

  static constraints = {
    quantityOnHand min: -1
    quantityReserved min: 0
    fullPrice validator: { val, obj -> if(!(val >= obj.price)) return ['maxThanPrice'] }
    price min: 0.0
  }

  static mapping = {
    status defaultValue: ItemStatusEnum.ACTIVE.id
    vertical indexColumn:[name: 'vertical_sku', unique:true]
    sku indexColumn:[name: 'vertical_sku', unique:true]
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

  def beforeValidate() {
    if (!status) {
      status = ItemStatusEnum.ACTIVE.domain
    }
  }

  def getAvaliable() {
    if (sku.item.itemGroup.avaliable) {
      if (isActive()) {
        if (isNotSoldOut()) {
          WaitingListItemStatusEnum.AVAILABLE.domain
        } else {
          WaitingListItemStatusEnum.SOLD_OUT.domain
        }
      } else {
        WaitingListItemStatusEnum.NOT_AVAILABLE.domain
      }
    } else {
      WaitingListItemStatusEnum.NOT_AVAILABLE.domain
    }
  }

  def isActive() {
    ItemStatusEnum.ACTIVE == status.enum
  }

  def isNotSoldOut() {
    quantityOnHand == -1 || quantityReserved < quantityOnHand
  }

  def getStock() {
    if( hasUnlimitedStock() ) {
      -1
    } else {
      quantityOnHand - quantityReserved
    }
  }

  boolean isAvailable() {
    sku.isAvailable()
  }

  boolean isSoldOut() {
    if (hasUnlimitedStock()) {
      sku.isSoldOut()
    }
  }

  boolean hasUnlimitedStock() {
    quantityOnHand == -1
  }
}
