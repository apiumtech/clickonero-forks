package changelogs

/**
 * Move fraudulent and complete_frofile_reminders columns from profile to user. Thus preventing
 * profile's last_updated from update when logged in.
 */

databaseChangeLog = {

  def id = '9284759187063'

  changeSet(author: "winbits (generated)", id: "${id}-1") {
    sql("ALTER TABLE `profile` DROP COLUMN `fraudulent`;")
  }

  changeSet(author: "winbits (generated)", id: "${id}-2") {
    sql("ALTER TABLE `user` ADD COLUMN `fraudulent` BIT(1) NOT NULL DEFAULT 0 AFTER `last_login`;")
  }

  changeSet(author: "winbits (generated)", id: "${id}-3") {
    sql("ALTER TABLE `profile` DROP COLUMN `complete_profile_remainders`;")
  }

  changeSet(author: "winbits (generated)", id: "${id}-4") {
    sql("ALTER TABLE `user` ADD COLUMN `complete_profile_reminders` TINYINT NOT NULL DEFAULT 0 AFTER `fraudulent`;")
  }

  changeSet(author: "winbits (generated)", id: "${id}-5") {
    sql("ALTER TABLE `profile` CHANGE COLUMN `complete_register` `completed` BIT(1) NOT NULL ;")
  }
}
