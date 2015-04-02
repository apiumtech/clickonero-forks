package changelogs

databaseChangeLog = {
  
  def id = '1407364150556'

	changeSet(author: "arcesino (generated)", id: "${id}-1") {
		addColumn(tableName: "tracking") {
			column(name: "user_id", type: "bigint", defaultValue: "1") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "arcesino (generated)", id: "${id}-2") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "content", tableName: "tracking")
	}

	changeSet(author: "arcesino (generated)", id: "${id}-3") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "source", tableName: "tracking")
	}

	changeSet(author: "arcesino (generated)", id: "${id}-4") {
		addNotNullConstraint(columnDataType: "bigint", columnName: "step_id", tableName: "tracking")
    sql("UPDATE `tracking` SET `step_id` = 1 WHERE ISNULL(`step_id`);")
	}

	changeSet(author: "arcesino (generated)", id: "${id}-5") {
		createIndex(indexName: "FK4BBA1EB76F4B0AA9", tableName: "tracking") {
			column(name: "user_id")
		}
	}

	changeSet(author: "arcesino (generated)", id: "${id}-6") {
		dropColumn(columnName: "date", tableName: "tracking")
	}

	changeSet(author: "arcesino (generated)", id: "${id}-7") {
		dropColumn(columnName: "email", tableName: "tracking")
	}

	changeSet(author: "arcesino (generated)", id: "${id}-8") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "tracking", constraintName: "FK4BBA1EB76F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
