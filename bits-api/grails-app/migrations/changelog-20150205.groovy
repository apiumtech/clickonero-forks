databaseChangeLog = {
  changeSet(author: "winbits (generated)", id: "201502052110-1") {
    addColumn(tableName: "bag") {
      column(name: "partial_deposit_amount", type: "DECIMAL(19,2)")
    }
  }

  changeSet(author: "winbits (generated)", id: "201502052110-2") {
    addColumn(tableName: "bag") {
      column(name: "min_balance", type: "DECIMAL(19,2)")
    }
  }

  changeSet(author: "winbits (generated)", id: "201502052110-3") {
    addColumn(tableName: "bag") {
      column(name: "duration", type: "INT")
    }
  }
}