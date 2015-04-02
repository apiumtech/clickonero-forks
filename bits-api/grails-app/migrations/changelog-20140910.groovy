databaseChangeLog = {

	changeSet(author: "winbits (generated)", id: "1410374332499-1") {
		createTable(tableName: "configuration") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(255)")

			column(name: "value", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-2") {
		addColumn(tableName: "deposit") {
			column(name: "account_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-3") {
		addColumn(tableName: "deposit") {
			column(name: "transaction_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-4") {
		addColumn(tableName: "withdrawal") {
			column(name: "target_id", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-5") {
		addNotNullConstraint(columnDataType: "DATETIME", columnName: "expiration_date", tableName: "deposit")
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-6") {
		dropForeignKeyConstraint(baseTableName: "transaction", baseTableSchemaName: "bits", constraintName: "FK7FA0D2DEEA33F9CB")
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-10") {
		createIndex(indexName: "code", tableName: "configuration", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-11") {
		dropColumn(columnName: "deposit_id", tableName: "transaction")
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-7") {
		addForeignKeyConstraint(baseColumnNames: "account_id", baseTableName: "deposit", baseTableSchemaName: "bits", constraintName: "FK5CA7169EFF3C1B6B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "account", referencedTableSchemaName: "bits", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-8") {
		addForeignKeyConstraint(baseColumnNames: "transaction_id", baseTableName: "deposit", baseTableSchemaName: "bits", constraintName: "FK5CA7169EEA4EC14B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "transaction", referencedTableSchemaName: "bits", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1410374332499-9") {
		addForeignKeyConstraint(baseColumnNames: "target_id", baseTableName: "withdrawal", baseTableSchemaName: "bits", constraintName: "FK9EDE7CB5A154D5F8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "deposit", referencedTableSchemaName: "bits", referencesUniqueColumn: "false")
	}
}
