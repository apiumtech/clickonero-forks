package com.winbits.domain.affiliation

import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.orders.OrderDetail

class WaitingListItem implements Serializable {

  User user
  SkuProfile skuProfile
  OrderDetail orderDetail
  Boolean deleted = false
  Date dateCreated
  WaitingListItemStatus status

  static belongsTo = [User]

  static constraints = {
    orderDetail nullable: true
    status nullable: true
  }

  static mapping = {
    id(composite: ['user', 'skuProfile'])
    version(false)
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

  def beforeValidate() {
    if (!status) {
      status = WaitingListItemStatusEnum.SOLD_OUT.domain
    }
  }
}
