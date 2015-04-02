package com.winbits.domain.auth.api

import com.winbits.domain.affiliation.Vertical

import java.security.SecureRandom

class ApiUser {

	transient springSecurityService

	String email
	String password
  Vertical vertical
  String salt
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		email blank: false, unique: true, email: true
		password blank: false
    vertical nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<ApiRole> getAuthorities() {
		ApiUserApiRole.findAllByApiUser(this).collect { it.apiRole } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

  String getSalt() {
    if (!this.salt) {
      def rnd = new byte[48];
      new SecureRandom().nextBytes(rnd)
      this.salt = rnd.encodeBase64()
      this.salt = this.salt.replaceAll(/\+/, '_')
      this.salt = this.salt.replaceAll(/\//, '_')
    }
    this.salt
  }
}
