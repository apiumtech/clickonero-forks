package changelogs

databaseChangeLog = {

  changeSet(author: "winbits", id: "usermobile-01") {
    sql('CREATE TABLE `user_mobile_status` ' +
        ' (  `id` bigint(20) NOT NULL AUTO_INCREMENT,' +
        '    `version` bigint(20) NOT NULL,' +
        '    `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,' +
        '    `name` varchar(25) COLLATE utf8_unicode_ci NOT NULL, PRIMARY KEY (`id`),' +
        '    UNIQUE KEY `name` (`name`)' +
        ' ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;')
  }

  changeSet(author: "winbits", id: "usermobile-02") {
    sql('CREATE TABLE `user_mobile_carrier` ' +
            '(`id` bigint(20) NOT NULL AUTO_INCREMENT,' +
            '`version` bigint(20) NOT NULL,' +
            '`description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,' +
            '`name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,' +
            ' PRIMARY KEY (`id`), UNIQUE KEY `name` (`name`)' +
       ') ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci')
  }

  changeSet(author: "winbits", id: "usermobile-03") {
    sql('CREATE TABLE `user_mobile`' +
        ' (`id` bigint(20) NOT NULL AUTO_INCREMENT,' +
        '`version` bigint(20) NOT NULL,' +
        '`activation_code` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,' +
        '`carrier_id` bigint(20) NOT NULL,' +
        '`date_created` datetime NOT NULL,' +
        '`last_updated` datetime NOT NULL,' +
        '`mobile_phone` varchar(10) COLLATE utf8_unicode_ci NOT NULL,' +
        '`user_id` bigint(20) NOT NULL,`user_mobile_status_id` bigint(20) NOT NULL, PRIMARY KEY (`id`),' +
        ' KEY `FKEC83D796CD2D7DC4` (`user_mobile_status_id`),' +
        ' KEY `FKEC83D7966F4B0AA9`(`user_id`),' +
        ' KEY `FKEC83D79663712A67` (`carrier_id`),' +
        ' CONSTRAINT `FKEC83D79663712A67` FOREIGN KEY (`carrier_id`) REFERENCES `user_mobile_carrier` (`id`),' +
        ' CONSTRAINT `FKEC83D7966F4B0AA9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),' +
        ' CONSTRAINT `FKEC83D796CD2D7DC4` FOREIGN KEY (`user_mobile_status_id`) REFERENCES `user_mobile_status` (`id`)' +
       ' ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci')
  }

  changeSet(author: "winbits", id: "usermobile-04") {
    sql("INSERT INTO user_mobile_status (id,version,name) " +
        "values (1,0,'WAIT'),(2,0,'CANCELLED'),(3,0,'ACTIVE'),(4,0,'CHANGE');")
  }

  changeSet(author: "winbits", id: "usermobile-05") {
    sql("INSERT INTO user_mobile_carrier (id,version,name) " +
        "values (1,0,'TELCEL'),(2,0,'MOVISTAR'),(3,0,'IUSACELL'),(4,0,'UNEFON'),(5,0,'VIRGINMOBILE');")
  }

  changeSet(author: "winbits", id: "usermobile-06") {
    sql("INSERT INTO configuration (code,version, description, value)" +
            "VALUES ('winbits.config.sms.apikey', 0,'Api key para sms', '7738f97e6812874bbd232a49482cdf5f');")
  }

}
