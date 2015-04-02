package changelogs

databaseChangeLog = {

  changeSet(author: "winbits (generated)", id: "4513081994090-1") {
    sqlFile(path: "sql/7-new-shipping-address.sql")
  }

  changeSet(author: "winbits (generated)", id: "4513081994090-2") {
    sql("ALTER TABLE `shipping_order` CHANGE COLUMN `between_streets` `between_streets` VARCHAR(75) NULL;")
  }
}
