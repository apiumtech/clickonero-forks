package changelogs

/**
 * Move last_login column from profile to user
 */

databaseChangeLog = {

  def id = '6548389283748'

  changeSet(author: "winbits (generated)", id: "${id}-1") {
    sql("ALTER TABLE `profile` DROP COLUMN `last_login`;")
  }

  changeSet(author: "winbits (generated)", id: "${id}-2") {
    sql("ALTER TABLE `user` ADD COLUMN `last_login` DATETIME NULL DEFAULT NULL AFTER `vertical_id`;")
  }
}
