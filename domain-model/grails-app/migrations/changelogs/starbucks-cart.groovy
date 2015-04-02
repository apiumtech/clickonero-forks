package changelogs

databaseChangeLog = {

  changeSet(author: "winbits ", id: "starbucks-cartv1-1") {
    sql('''
    CREATE TABLE `reference_cart_code` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `version` bigint(20) NOT NULL,
      `code` varchar(250) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
    ''')
  }

  changeSet(author: "winbits ", id: "starbucks-cartv1-2") {
    sql('''
    CREATE TABLE `cart_detail_reference_cart_code` (
      `cart_detail_reference_cart_codes_id` bigint(20) DEFAULT NULL,
      `reference_cart_code_id` bigint(20) DEFAULT NULL,
      KEY `FKEB9D34995828786` (`reference_cart_code_id`),
      KEY `FKEB9D349686F0B39` (`cart_detail_reference_cart_codes_id`),
      CONSTRAINT `FKEB9D349686F0B39` FOREIGN KEY (`cart_detail_reference_cart_codes_id`) REFERENCES `cart_detail` (`id`),
      CONSTRAINT `FKEB9D34995828786` FOREIGN KEY (`reference_cart_code_id`) REFERENCES `reference_cart_code` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    ''')
  }

}
