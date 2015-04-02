package com.winbits.domain.affiliation

import com.winbits.domain.Enumable
import com.winbits.domain.annotations.Enumability

@Enumability
class MigrationStatus implements Enumable<MigrationStatusEnum> {

  String name
  String description

  static transients = ['enum']

  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  MigrationStatusEnum getEnum() {
    def theId = getId()
    if (theId) {
      MigrationStatusEnum.find { it.id == theId }
    }
  }

  @Override
  boolean equals(MigrationStatusEnum enumConstant) {
    getId() == enumConstant.id
  }
}
