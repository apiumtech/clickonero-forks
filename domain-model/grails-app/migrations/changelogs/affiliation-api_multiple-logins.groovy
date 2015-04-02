package changelogs

databaseChangeLog = {

  changeSet(author: "manuel", id: "20140814-1830") {
    sqlFile(path: "sql/affiliation-api_multiple-logins.sql")
  }

  changeSet(author: "manuel", id: "20140820-1235") {
    sql("ALTER TABLE `vertical_partner` ADD COLUMN `mongo_history_id` VARCHAR(255);")
  }
}