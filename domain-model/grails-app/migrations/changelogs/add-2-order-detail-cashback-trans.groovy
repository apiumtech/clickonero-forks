package changelogs

databaseChangeLog = {

   changeSet(author: "winbits", id: "cashbackdetailtrans-1") {
        sql(""" ALTER TABLE `order_detail` ADD COLUMN `cashback` decimal(13,2) NOT NULL DEFAULT 0 AFTER `status_id`; """)
   }


}
