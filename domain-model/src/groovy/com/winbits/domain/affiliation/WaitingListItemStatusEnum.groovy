package com.winbits.domain.affiliation

import com.winbits.domain.PersistentEnum

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 7/9/13
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public enum WaitingListItemStatusEnum implements PersistentEnum<WaitingListItemStatus> {

  SOLD_OUT(1L),
  AVAILABLE(2L),
  NOT_AVAILABLE(3L)

  final Serializable id

  WaitingListItemStatusEnum(Serializable id) {
    this.id = id
  }

  @Override
  WaitingListItemStatus getDomain() {
    WaitingListItemStatus.load(id)
  }

  @Override
  boolean equals(WaitingListItemStatus domainInstance) {
    this.id == domainInstance?.id
  }
}
