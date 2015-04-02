package com.winbits.domain.admin

import org.apache.commons.lang.builder.HashCodeBuilder

class AdminRoleAdminRequestmap implements Serializable{

  AdminRequestmap adminRequestmap
  AdminRole adminRole

  boolean equals(other) {
    if (!(other instanceof AdminRoleAdminRequestmap)) {
      return false
    }

    other.adminRequestmap?.id == adminRequestmap?.id &&
        other.adminRole?.id == adminRole?.id
  }

  int hashCode() {
    def builder = new HashCodeBuilder()
    if (adminRequestmap) builder.append(adminRequestmap.id)
    if (adminRole) builder.append(adminRole.id)
    builder.toHashCode()
  }

  static AdminRoleAdminRequestmap get(long adminRequestmapId, long adminRoleId) {
    find 'from AdminRoleAdminRequestmap where adminRequestmap.id=:adminRequestmapId and adminRole.id=:adminRoleId',
        [adminRequestmapId: adminRequestmapId, adminRoleId: adminRoleId]
  }

  static AdminRoleAdminRequestmap create(AdminRequestmap adminRequestmap, AdminRole adminRole, boolean flush = false) {
    new AdminRoleAdminRequestmap(adminRequestmap: adminRequestmap, adminRole: adminRole).save(flush: flush, insert: true)
  }

  static boolean remove(AdminRequestmap adminRequestmap, AdminRole adminRole, boolean flush = false) {
    AdminRoleAdminRequestmap instance = AdminRoleAdminRequestmap.findByAdminRequestmapAndAdminRole(adminRequestmap, adminRole)
    if (!instance) {
      return false
    }

    instance.delete(flush: flush)
    true
  }

  static void removeAll(AdminRequestmap adminRequestmap) {
    executeUpdate 'DELETE FROM AdminRoleAdminRequestmap WHERE adminRequestmap=:adminRequestmap', [adminRequestmap: adminRequestmap]
  }

  static void removeAll(AdminRole adminRole) {
    executeUpdate 'DELETE FROM AdminRoleAdminRequestmap WHERE adminRole=:adminRole', [adminRole: adminRole]
  }

  static mapping = {
    id composite: ['adminRole', 'adminRequestmap']
    version false
  }
}