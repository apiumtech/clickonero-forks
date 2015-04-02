package com.winbits.domain.affiliation

import org.apache.commons.lang.builder.HashCodeBuilder

class VerticalPartner implements Serializable {

  String mongoHistoryId
  MigrationStatus status

  static belongsTo = [vertical: Vertical, user: User]

  static constraints = {
    mongoHistoryId nullable: true
  }

  static mapping = {
    id composite: ['vertical', 'user']
  }

  @Override
  boolean equals(other) {
    if (!(other instanceof VerticalPartner)) {
      return false
    }

    other.vertical == vertical && other.user == user
  }

  @Override
  int hashCode() {
    def builder = new HashCodeBuilder()
    builder.append vertical
    builder.append user
    builder.toHashCode()
  }
}
