package com.winbits.domain.affiliation

class Role {

	String authority

	static mapping = {
		cache true
	}


	static constraints = {
		authority blank: false, unique: true
	}
}
