package changelogs

databaseChangeLog = {

	changeSet(author: "fernando (generated)", id: "1407195071409-1") {
    sql("ALTER TABLE `attribute` CHANGE COLUMN `label` `label` VARCHAR(255) NOT NULL;")
	}

	changeSet(author: "fernando (generated)", id: "1407195071409-2") {
    sql("ALTER TABLE `attribute` CHANGE COLUMN `name` `name` VARCHAR(255) NOT NULL;")
	}


	changeSet(author: "manuel", id: "201409252042-1") {
          sql("alter table item modify attribute_name varchar(255) not null;")
	}

	changeSet(author: "manuel", id: "201409252042-2") {
          sql("alter table item modify attribute_label varchar(255) not null;")
	}
}
