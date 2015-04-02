package changelogs

databaseChangeLog = {
  changeSet(author: "winbits ", id: "starbucksv1-1") {
    sql("alter table item_group add column require_data bit(1) DEFAULT NULL;")
  }
  
  changeSet(author: "winbits ", id: "starbucksv1-2") {
    sql("alter table item_group_profile add column hours_generate_file smallint(6) DEFAULT NULL;")
  }
  
  changeSet(author: "winbits ", id: "starbucksv1-3") {
    sql('''
    CREATE TABLE `reference_code` (
      `id` bigint (20) NOT NULL AUTO_INCREMENT,
      `version` bigint (20) NOT NULL,
      `code` varchar (250) NOT NULL,
      PRIMARY KEY  (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
    '''
    )
  }
  
  changeSet(author: "winbits ", id: "starbucksv1-4") {
    sql('''
    CREATE TABLE `order_detail_reference_code` (
      `order_detail_reference_codes_id` bigint(20) DEFAULT NULL,
      `reference_code_id` bigint(20) DEFAULT NULL,
      KEY `FKD38EC1DE298E9F0C` (`order_detail_reference_codes_id`),
      KEY `FKD38EC1DEE5F9387D` (`reference_code_id`),
      CONSTRAINT `FKD38EC1DEE5F9387D` FOREIGN KEY (`reference_code_id`) REFERENCES `reference_code` (`id`),
      CONSTRAINT `FKD38EC1DE298E9F0C` FOREIGN KEY (`order_detail_reference_codes_id`) REFERENCES `order_detail` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    ''')
  }

  changeSet(author: "winbits", id: "starbucksv1-5") {
     sql('''
       create table reference_code_status (id bigint not null auto_increment, version bigint not null, description varchar(100), name varchar(25) not null unique, primary key (id)) ENGINE=InnoDB;  
     ''')
  }

  changeSet(author: "winbits", id: "starbucksv1-6") {
     sql('''
        alter table reference_code add column status_id bigint not null;
     ''')
  }

  changeSet(author: "winbits", id: "starbucksv1-7") {
     sql('''
        alter table reference_code add index FK8EB8FE61D85FCA5E (status_id), add constraint FK8EB8FE61D85FCA5E foreign key (status_id) references reference_code_status (id);
     ''')
  }

}
