package changelogs

databaseChangeLog = {

  def id = '7864589278543'

  changeSet(author: "winbits (generated)", id: "${id}-1") {
    sql("ALTER TABLE `user` CHANGE COLUMN `api_token` `api_token` VARCHAR(128) NULL ;")
  }
}
