package changelogs

databaseChangeLog = {

  changeSet(author: "winbits ", id: "132153133548733-1") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `amount` decimal(13,2);")
  }
  changeSet(author: "winbits ", id: "132153133548733-2") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `payment_method_id` bigint(20) not null;")
  }
  changeSet(author: "winbits ", id: "132153133548733-3") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `account_number` varchar(255);")
  }
  changeSet(author: "winbits ", id: "132153133548733-4") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `account_user` varchar(255);")
  }
  changeSet(author: "winbits ", id: "132153133548733-5") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `bank_name` varchar(255);")
  }
  changeSet(author: "winbits ", id: "132153133548733-6") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `clabe` varchar(255);")
  }
  changeSet(author: "winbits ", id: "132153133548733-7") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `reference` varchar(255);")
  }
  changeSet(author: "winbits ", id: "132153133548733-8") {
    sql("ALTER TABLE `refund_detail` DROP PRIMARY KEY")
  }
  changeSet(author: "winbits ", id: "132153133548733-9") {
    sql("ALTER TABLE `refund_detail` ADD PRIMARY KEY(payment_method_id, order_detail_id)")
  }
  changeSet(author: "winbits ", id: "132153133548733-10") {
    sql("ALTER TABLE `refund_detail` ADD CONSTRAINT refund_detailPK UNIQUE (payment_method_id, order_detail_id)")
  }
  changeSet(author: "winbits ", id: "132153133548733-11") {
    sql("ALTER TABLE `refund_detail` DROP FOREIGN KEY `FKD3565EF8A31700D5`")
  }
  changeSet(author: "winbits ", id: "132153133548733-12") {
    sql("ALTER TABLE `refund_detail` ADD INDEX `FKD3565EF8D538B1C1` (`payment_method_id` ASC)")
  }
  changeSet(author: "winbits ", id: "132153133548733-13") {
    sql("ALTER TABLE `refund_detail` DROP COLUMN `order_payment_id`")
  }
  changeSet(author: "winbits ", id: "132153133548733-14") {
    sql("ALTER TABLE `refund_detail` ADD CONSTRAINT `FKD3565EF8D538B1C1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`) ON DELETE NO ACTION  ON UPDATE NO ACTION;")
  }

  changeSet(author: "winbits ", id: "132153133548733-15") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `deleted` bit(1) NULL DEFAULT 1;")
  }

  changeSet(author: "winbits ", id: "132153133548733-16") {
    sql("""
      CREATE TABLE `refund_comment_history` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `version` bigint(20) NOT NULL,
        `comment` varchar(250) NOT NULL,
        `date_created` datetime NOT NULL,
        `refund_detail_payment_method_id` bigint(20) NOT NULL,
        `refund_detail_order_detail_id` bigint(20) NOT NULL,
        `user_id` bigint(20) NOT NULL,
        PRIMARY KEY (`id`),
        KEY `FK377EFEDB5BB39EB` (`refund_detail_payment_method_id`,`refund_detail_order_detail_id`),
        KEY `FK377EFED7DA5EF39` (`user_id`),
        CONSTRAINT `FK377EFED7DA5EF39` FOREIGN KEY (`user_id`) REFERENCES `admin_user` (`id`),
        CONSTRAINT `FK377EFEDB5BB39EB` FOREIGN KEY (`refund_detail_payment_method_id`, `refund_detail_order_detail_id`) REFERENCES `refund_detail` (`payment_method_id`, `order_detail_id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
    """)
  }

  changeSet(author: "winbits ", id: "132153133548733-17") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `cause` varchar(255) NOT NULL;")
  }

  changeSet(author: "winbits ", id: "132153133548733-18") {
    sql("ALTER TABLE `refund_detail` ADD COLUMN `url_payment_proof` varchar(255) NULL;")
  }


}