package changelogs

databaseChangeLog = {

  def id = '895986987578451'

  changeSet(author: "winbits (generated)", id: "${id}-1") {
    sqlFile(path: "sql/113-add-last-updated-card-subscription.sql")
  }

  changeSet(author: "winbits (generated)", id: "${id}-2") {
    sql("UPDATE `card_subscription` SET `last_updated` = NOW() WHERE `last_updated` = 0;")
  }
}
