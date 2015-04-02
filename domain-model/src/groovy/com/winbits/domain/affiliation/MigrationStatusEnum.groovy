package com.winbits.domain.affiliation

import com.winbits.domain.PersistentEnum

/**
 * Created by winbits on 8/13/14.
 */
public enum MigrationStatusEnum implements PersistentEnum<MigrationStatus> {

  PENDING(1L),
  COMPLETE(2L),
  TRIED(3L)

  final Serializable id

  MigrationStatusEnum(Serializable id){
    this.id = id
  }

  @Override
  MigrationStatus getDomain() {
    MigrationStatus.load(id)
  }

  @Override
  boolean equals(MigrationStatus domainInstance) {
    id == domainInstance?.id
  }
}
