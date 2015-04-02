package com.winbits.domain.admin

import com.winbits.domain.common.Department
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

class AdminUser {

  transient springSecurityService

  String username
  String password
  String name
  String lastName
  boolean enabled
  boolean accountExpired
  boolean accountLocked
  boolean passwordExpired
  LocalDateTime lastLoginDate
  LocalDateTime dateCreated
  LocalDateTime lastUpdated
  Department department

  static transients = ['email']

  static constraints = {
    username blank: false, email: true
    password blank: false
    lastLoginDate nullable: true
    department nullable: true
    name nullable: true
    lastName nullable: true
  }

  static mapping = {
    password column: '`password`'
  }

  Set<AdminRole> getAuthorities() {
    AdminUserAdminRole.findAllByAdminUser(this).collect { it.adminRole } as Set
  }

  def beforeInsert() {
    encodePassword()
  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  String getEmail() {
    username
  }

  protected void encodePassword() {
    password = springSecurityService.encodePassword(password)
  }
}
