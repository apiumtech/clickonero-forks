-- cambio de null a la llave foranea de order_detail in sku_outcome
ALTER TABLE `sku_outcome`
DROP FOREIGN KEY `FKB50FE305F83A7DF`;
ALTER TABLE `sku_outcome`
CHANGE COLUMN `order_detail_id` `order_detail_id` BIGINT(20) NULL ;
ALTER TABLE `sku_outcome`
ADD CONSTRAINT `FKB50FE305F83A7DF`
  FOREIGN KEY (`order_detail_id`)
  REFERENCES `order_detail` (`id`);



--cambios a tabla bank y se crea tabla de bin

ALTER TABLE bank ADD UNIQUE(name);


CREATE TABLE `bin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bin` varchar(255) NOT NULL,
  `credit_card_type_id` bigint(20) DEFAULT NULL,
  `issuer_id` bigint(20) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17D07881C9060` (`credit_card_type_id`),
  KEY `FK17D077E7AE463` (`issuer_id`),
  CONSTRAINT `FK17D077E7AE463` FOREIGN KEY (`issuer_id`) REFERENCES `bank` (`id`),
  CONSTRAINT `FK17D07881C9060` FOREIGN KEY (`credit_card_type_id`) REFERENCES `credit_card_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=latin1;

 CREATE TABLE `order_payment_bins` (
  `order_payment_id` bigint(20) NOT NULL,
  `bin_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_payment_id`,`bin_id`),
  KEY `FKB12DB0367720A9D4` (`bin_id`),
  KEY `FKB12DB036A31700D5` (`order_payment_id`),
  CONSTRAINT `FKB12DB036A31700D5` FOREIGN KEY (`order_payment_id`) REFERENCES `order_payment` (`id`),
  CONSTRAINT `FKB12DB0367720A9D4` FOREIGN KEY (`bin_id`) REFERENCES `bin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

-- Actualización de tamaños en catalogos
alter table item_group_profile  modify conditions longtext not null;
alter table item_group_profile  modify long_description longtext not null;

-- Cambios a sku item y itemgrups
SET FOREIGN_KEY_CHECKS=0;
CREATE TABLE `category_type` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `description`  varchar(100) NULL DEFAULT NULL ,
    `name`  varchar(25) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
ALTER TABLE `item_group` ADD COLUMN `category_type_id`  bigint(20) NOT NULL AFTER `brand_id`;
ALTER TABLE `item_group` ADD COLUMN `model`  varchar(255) NOT NULL AFTER `last_updated`;
ALTER TABLE `item_group` ADD COLUMN `sales_department_type_id`  bigint(20) NOT NULL AFTER `requires_shipping`;
ALTER TABLE `item_group` ADD COLUMN `seller_id`  bigint(20) NOT NULL AFTER `sales_department_type_id`;
ALTER TABLE `item_group` ADD COLUMN `sub_category_type_id`  bigint(20) NOT NULL AFTER `status_id`;
ALTER TABLE `item_group` ADD COLUMN `type_id`  bigint(20) NOT NULL AFTER `sub_category_type_id`;
ALTER TABLE `item_group` ADD COLUMN `vertical_marketing_type_id`  bigint(20) NOT NULL AFTER `vertical_id`;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA5340997540` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA532BE0571D` FOREIGN KEY (`category_type_id`) REFERENCES `category_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA5341679A0` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA53641655B8` FOREIGN KEY (`sales_department_type_id`) REFERENCES `sales_department_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA539F6C4C42` FOREIGN KEY (`vertical_marketing_type_id`) REFERENCES `vertical_marketing_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group` ADD CONSTRAINT `FK8AFCFA53B7C5D850` FOREIGN KEY (`sub_category_type_id`) REFERENCES `sub_category_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `item_group_profile` ADD COLUMN `marketing_sale`  bit(1) NOT NULL AFTER `long_description`;
ALTER TABLE `item_group_profile` MODIFY COLUMN `name`  varchar(255) NOT NULL DEFAULT '' AFTER `min_per_order`;
CREATE TABLE `sales_department_type` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `description`  varchar(100) NULL DEFAULT NULL ,
    `name`  varchar(25) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
CREATE TABLE `seller` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `name`  varchar(50) NOT NULL ,
    `vertical_id`  bigint(20) NOT NULL ,
    PRIMARY KEY (`id`),
    CONSTRAINT `FKC9FF4F7F98AFE749` FOREIGN KEY (`vertical_id`) REFERENCES `vertical` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    INDEX `FKC9FF4F7F98AFE749` (`vertical_id`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
ALTER TABLE `sku` MODIFY COLUMN `ean`  varchar(255) NULL DEFAULT NULL AFTER `deleted`;
ALTER TABLE `sku` ADD COLUMN `expected_sold`  int(11) NOT NULL AFTER `ean`;
ALTER TABLE `sku` MODIFY COLUMN `height`  decimal(19,2) NULL DEFAULT NULL AFTER `expected_sold`;
ALTER TABLE `sku` MODIFY COLUMN `length`  decimal(19,2) NULL DEFAULT NULL AFTER `last_updated`;
ALTER TABLE `sku` MODIFY COLUMN `priority`  int(11) NOT NULL AFTER `length`;
ALTER TABLE `sku` MODIFY COLUMN `weight`  decimal(19,2) NULL DEFAULT NULL AFTER `virtual_cost`;
ALTER TABLE `sku` MODIFY COLUMN `width`  decimal(19,2) NULL DEFAULT NULL AFTER `weight`;
CREATE TABLE `sub_category_type` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `description`  varchar(100) NULL DEFAULT NULL ,
    `name`  varchar(25) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
CREATE TABLE `type` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `description`  varchar(100) NULL DEFAULT NULL ,
    `name`  varchar(25) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
CREATE TABLE `vertical_marketing_type` (
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `version`  bigint(20) NOT NULL ,
    `description`  varchar(100) NULL DEFAULT NULL ,
    `name`  varchar(25) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE 
)
ENGINE=InnoDB
ROW_FORMAT=Compact
;
SET FOREIGN_KEY_CHECKS=0;

-- Realiza modificación a tabla order_payment se cambia tamaño de columna a TEXT

ALTER TABLE `order_payment` CHANGE COLUMN `payment_capture` `payment_capture` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL ;
