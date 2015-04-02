package com.winbits.domain.admin

class AdminRole {

	String authority
  String description

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
