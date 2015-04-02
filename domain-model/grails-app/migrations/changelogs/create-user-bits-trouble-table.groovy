package changelogs

databaseChangeLog = {

	changeSet(author: "Jhonson (generated)", id: "01238352901-1") {
    sql('''
     CREATE TABLE `user_bits_trouble` (
      `id` bigint (20) NOT NULL AUTO_INCREMENT,
      `version` bigint (20) NOT NULL,
      `concept` varchar (255) DEFAULT NULL,
      `date_created` datetime NOT NULL,
      `deposit` bigint (20) NOT NULL,
      `error_description` varchar (255) DEFAULT NULL,
      `user_id` bigint (20) NOT NULL,
      PRIMARY KEY  (`id`),
      KEY `FK5AAED3B26F4B0AA9` (`user_id`),
      CONSTRAINT `FK5AAED3B26F4B0AA9` FOREIGN KEY  (`user_id`) REFERENCES `user` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
    '''
    )
	}

  changeSet(author: "winbits ", id: "132153133548733-2") {
    sql('''
      ALTER TABLE profile ADD clickonero_id bigint(20);
    ''')
  }
}
