package changelogs

databaseChangeLog = {


  changeSet(author: "winbits", id: "cashbacktrans-1") {
    sql(""" ALTER TABLE `orders` ADD COLUMN `cashback_total` decimal(19,2) DEFAULT NULL AFTER `vertical_id`;""")
  }


  changeSet(author: "winbits", id: "cashbacktrans-2") {
        sql(""" ALTER TABLE `orders` ADD COLUMN `cashback_transaction_id` bigint(20) DEFAULT NULL AFTER `cashback_total`;""")
  }

}
