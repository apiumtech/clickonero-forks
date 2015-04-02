databaseChangeLog = {

	changeSet(author: "winbits (generated)", id: "1410395010592-1") {
		createTable(tableName: "bag") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1410395010592-2") {
		dropForeignKeyConstraint(baseTableName: "refund_transactions", baseTableSchemaName: "bits", constraintName: "FK2CC9F6BCA2312003")
	}

	changeSet(author: "winbits (generated)", id: "1410395010592-3") {
		dropForeignKeyConstraint(baseTableName: "refund_transactions", baseTableSchemaName: "bits", constraintName: "FK2CC9F6BCEA4EC14B")
	}

	changeSet(author: "winbits (generated)", id: "1410395010592-5") {
		dropTable(tableName: "refund_transactions")
	}

	changeSet(author: "winbits (generated)", id: "1410395010592-4") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "bag", baseTableSchemaName: "bits", constraintName: "FK17C08FF3C1B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "bits", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "201409101937") {
	    sql("ALTER TABLE bag ADD UNIQUE(name);")
	}

	
	changeSet(author: "winbits (generated)", id: "201409101938") {
	    sql("ALTER TABLE bag ADD UNIQUE(account_id);")
	}
}
