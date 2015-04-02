CREATE TABLE `item_group_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE item_group ADD COLUMN `item_group_type_id` bigint(20) NOT NULL;
ALTER TABLE item_group ADD  CONSTRAINT `FK8AFCFA53105AA3D4` FOREIGN KEY (`item_group_type_id`) REFERENCES `item_group_type` (`id`);

INSERT INTO item_group_type(description, name) VALUES ('productos con logistica', 'PRODUCT');
INSERT INTO item_group_type(description, name) VALUES ('servicios', 'SERVICE');
INSERT INTO item_group_type(description, name) VALUES ('viajes', 'TRAVEL');

UPDATE item_group SET item_group_type_id =  1
ALTER TABLE item_group MODIFY income_type_id  bigint(20) null;
ALTER TABLE item_group MODIFY model           varchar(255) null;

INSERT INTO attribute_type (name, description) VALUES ('HIDDEN', 'No mostrar')

-------------------------------
----------------------------------

alter table shipping_address add column last_name2 varchar(50);
alter table admin_user change date_created date_created DATETIME NOT NULL;
alter table admin_user change last_name last_name VARCHAR(255) NULL;
alter table admin_user change last_updated last_updated DATETIME NOT NULL;
alter table admin_user change name name varchar(255) NULL;
alter table admin_user modify username varchar(255) NOT NULL;
alter table item_group change income_type_id income_type_id BIGINT NULL;
alter table item_group change model model varchar(255) NULL;
alter table shipping_address change between_streets between_streets varchar(255) NULL;
alter table shipping_order change between_streets between_streets varchar(75) NULL;
drop index email on admin_user;
create unique index username on admin_user (username);
alter table order_detail drop refund_detail_id;
alter table refund_detail drop id;
drop table outcome_request;





