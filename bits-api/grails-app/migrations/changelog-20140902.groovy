databaseChangeLog = {

	changeSet(author: "winbits (generated)", id: "1409778730031-1") {
		addColumn(tableName: "deposit") {
			column(name: "opening_balance", type: "DECIMAL(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1409778730031-2") {
		addColumn(tableName: "withdrawal") {
			column(name: "refunded", type: "BIT") {
				constraints(nullable: "false")
			}
		}
	}

}
