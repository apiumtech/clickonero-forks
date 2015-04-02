package com.winbits.domain.auth.api

import org.apache.commons.lang.builder.HashCodeBuilder

class ApiUserApiRole implements Serializable {

	ApiUser apiUser
	ApiRole apiRole

	boolean equals(other) {
		if (!(other instanceof ApiUserApiRole)) {
			return false
		}

		other.apiUser?.id == apiUser?.id &&
			other.apiRole?.id == apiRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (apiUser) builder.append(apiUser.id)
		if (apiRole) builder.append(apiRole.id)
		builder.toHashCode()
	}

	static ApiUserApiRole get(long apiUserId, long apiRoleId) {
		find 'from ApiUserApiRole where apiUser.id=:apiUserId and apiRole.id=:apiRoleId',
			[apiUserId: apiUserId, apiRoleId: apiRoleId]
	}

	static ApiUserApiRole create(ApiUser apiUser, ApiRole apiRole, boolean flush = false) {
		new ApiUserApiRole(apiUser: apiUser, apiRole: apiRole).save(flush: flush, insert: true)
	}

	static boolean remove(ApiUser apiUser, ApiRole apiRole, boolean flush = false) {
		ApiUserApiRole instance = ApiUserApiRole.findByApiUserAndApiRole(apiUser, apiRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(ApiUser apiUser) {
		executeUpdate 'DELETE FROM ApiUserApiRole WHERE apiUser=:apiUser', [apiUser: apiUser]
	}

	static void removeAll(ApiRole apiRole) {
		executeUpdate 'DELETE FROM ApiUserApiRole WHERE apiRole=:apiRole', [apiRole: apiRole]
	}

	static mapping = {
		id composite: ['apiRole', 'apiUser']
		version false
	}
}
