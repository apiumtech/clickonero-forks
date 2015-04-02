package changelogs

databaseChangeLog = {

  changeSet(author: "winbits (generated)", id: "6538605522075-1") {
		addColumn(tableName: "vertical") {
			column(name: "active", type: "bit", defaultValueBoolean: "true") {
				constraints(nullable: "false")
			}
		}
	}

  changeSet(author: "winbits (generated)", id: "6538605522075-2") {
    addColumn(tableName: "vertical") {
      column(name: "order", type: "integer", defaultValueNumeric: "0") {
        constraints(nullable: "false")
      }
    }
  }

  changeSet(author: "winbits (generated)", id: "6538605522075-3") {
    addDefaultValue(tableName: "item_group", columnName: "item_group_type_id", defaultValueNumeric: "1")
  }
}
