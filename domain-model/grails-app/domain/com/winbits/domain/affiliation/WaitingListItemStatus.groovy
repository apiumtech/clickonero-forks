package com.winbits.domain.affiliation

import com.winbits.domain.Enumable

class WaitingListItemStatus implements Enumable<WaitingListItemStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  WaitingListItemStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      WaitingListItemStatusEnum.find { it.id == theId }
    }
  }
}
