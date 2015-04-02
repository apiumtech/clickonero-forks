package changelogs

databaseChangeLog = {

  changeSet(author: "winbits", id: "odcopenclose-01") {
    sql("""ALTER TABLE `odc`
           ADD COLUMN `user_supply_open` VARCHAR(255) NULL AFTER `wms_status_id`,
           ADD COLUMN `user_supply_close` VARCHAR(255) NULL AFTER `user_supply_open`;""")
  }
}
