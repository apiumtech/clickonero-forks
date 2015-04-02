package com.winbits.domain.admin

import org.apache.commons.lang.builder.HashCodeBuilder

class AdminUserAdminRole implements Serializable {

	AdminUser adminUser
	AdminRole adminRole

	boolean equals(other) {
		if (!(other instanceof AdminUserAdminRole)) {
			return false
		}

		other.adminUser?.id == adminUser?.id &&
			other.adminRole?.id == adminRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (adminUser) builder.append(adminUser.id)
		if (adminRole) builder.append(adminRole.id)
		builder.toHashCode()
	}

	static AdminUserAdminRole get(long adminUserId, long adminRoleId) {
		find 'from AdminUserAdminRole where adminUser.id=:adminUserId and adminRole.id=:adminRoleId',
			[adminUserId: adminUserId, adminRoleId: adminRoleId]
	}

	static AdminUserAdminRole create(AdminUser adminUser, AdminRole adminRole, boolean flush = false) {
		new AdminUserAdminRole(adminUser: adminUser, adminRole: adminRole).save(flush: flush, insert: true)
	}

	static boolean remove(AdminUser adminUser, AdminRole adminRole, boolean flush = false) {
		AdminUserAdminRole instance = AdminUserAdminRole.findByAdminUserAndAdminRole(adminUser, adminRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(AdminUser adminUser) {
		executeUpdate 'DELETE FROM AdminUserAdminRole WHERE adminUser=:adminUser', [adminUser: adminUser]
	}

	static void removeAll(AdminRole adminRole) {
		executeUpdate 'DELETE FROM AdminUserAdminRole WHERE adminRole=:adminRole', [adminRole: adminRole]
	}

	static mapping = {
		id composite: ['adminRole', 'adminUser']
		version false
	}
}
