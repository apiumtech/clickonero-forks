--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: changelog.groovy
--  Ran at: 17/07/13 03:09 PM
--  Against: root@localhost@jdbc:mysql://127.0.0.1:3306/winbits_qa?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8
--  Liquibase version: 2.0.5
--  *********************************************************************

--  Lock Database
--  Changeset qa-diff.groovy::1374089559856-1::arcesino (generated)::(Checksum: 3:fadfe5ecc041b8a5c21ebee1a8dd5028)
CREATE TABLE `wish_list_item` (`user_id` BIGINT NOT NULL, `brand_id` BIGINT NOT NULL, `date_created` DATETIME NOT NULL, `deleted` BIT NOT NULL) ENGINE=InnoDB;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Table', 'EXECUTED', 'qa-diff.groovy', '1374089559856-1', '2.0.5', '3:fadfe5ecc041b8a5c21ebee1a8dd5028', 458);

--  Changeset qa-diff.groovy::1374089559856-2::arcesino (generated)::(Checksum: 3:91bbfab1f3617b7af28dd70d7422eb09)
ALTER TABLE `item_group_profile` ADD `deleted` BIT NOT NULL DEFAULT 0;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-2', '2.0.5', '3:91bbfab1f3617b7af28dd70d7422eb09', 459);

--  Changeset qa-diff.groovy::1374089559856-3::arcesino (generated)::(Checksum: 3:e55e38570b560f3ef882a8e8b7382605)
ALTER TABLE `sku_profile` ADD `deleted` BIT NOT NULL DEFAULT 0;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-3', '2.0.5', '3:e55e38570b560f3ef882a8e8b7382605', 460);

--  Changeset qa-diff.groovy::1374089559856-4::arcesino (generated)::(Checksum: 3:7b97e085f8546d970022517e8301c811)
ALTER TABLE `attribute` MODIFY `label` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-4', '2.0.5', '3:7b97e085f8546d970022517e8301c811', 461);

--  Changeset qa-diff.groovy::1374089559856-5::arcesino (generated)::(Checksum: 3:47d04be45de6d08ec83189e5d7a5d032)
ALTER TABLE `attribute` MODIFY `name` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-5', '2.0.5', '3:47d04be45de6d08ec83189e5d7a5d032', 462);

--  Changeset qa-diff.groovy::1374089559856-6::arcesino (generated)::(Checksum: 3:a629da7da65f3cc69046fd43fa90e1d7)
ALTER TABLE `bank` MODIFY `name` VARCHAR(50) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-6', '2.0.5', '3:a629da7da65f3cc69046fd43fa90e1d7', 463);

--  Changeset qa-diff.groovy::1374089559856-7::arcesino (generated)::(Checksum: 3:3f52c575bbc3fc9ecab6b32d0b8e09a9)
ALTER TABLE `bank_account` MODIFY `account_number` VARCHAR(20) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-7', '2.0.5', '3:3f52c575bbc3fc9ecab6b32d0b8e09a9', 464);

--  Changeset qa-diff.groovy::1374089559856-8::arcesino (generated)::(Checksum: 3:95f929cfd745d85a41de9d4fae413f77)
ALTER TABLE `bank_account` MODIFY `name` VARCHAR(100) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-8', '2.0.5', '3:95f929cfd745d85a41de9d4fae413f77', 465);

--  Changeset qa-diff.groovy::1374089559856-9::arcesino (generated)::(Checksum: 3:087ad25473bd0c9f137eabb6f8c73470)
ALTER TABLE `brand` MODIFY `name` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-9', '2.0.5', '3:087ad25473bd0c9f137eabb6f8c73470', 466);

--  Changeset qa-diff.groovy::1374089559856-10::arcesino (generated)::(Checksum: 3:b10acaa36bf03fbbda35c2d92724e18e)
ALTER TABLE `item` MODIFY `attribute_label` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-10', '2.0.5', '3:b10acaa36bf03fbbda35c2d92724e18e', 467);

--  Changeset qa-diff.groovy::1374089559856-11::arcesino (generated)::(Checksum: 3:1eff7a096259493145344a0901117b4c)
ALTER TABLE `item` MODIFY `attribute_name` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-11', '2.0.5', '3:1eff7a096259493145344a0901117b4c', 468);

--  Changeset qa-diff.groovy::1374089559856-12::arcesino (generated)::(Checksum: 3:ae8fa49c9ccd06d4ff7dd9c04ed17c79)
ALTER TABLE `item_group_profile` MODIFY `max_per_order` MEDIUMINT NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-12', '2.0.5', '3:ae8fa49c9ccd06d4ff7dd9c04ed17c79', 469);

--  Changeset qa-diff.groovy::1374089559856-13::arcesino (generated)::(Checksum: 3:204fd38b8478bfc339dc99c5a9ee0221)
ALTER TABLE `item_group_profile` MODIFY `max_per_user` MEDIUMINT NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-13', '2.0.5', '3:204fd38b8478bfc339dc99c5a9ee0221', 470);

--  Changeset qa-diff.groovy::1374089559856-14::arcesino (generated)::(Checksum: 3:b4a163e13507bba085e7519d8fc8a9c6)
ALTER TABLE `item_group_profile` MODIFY `min_per_order` MEDIUMINT NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-14', '2.0.5', '3:b4a163e13507bba085e7519d8fc8a9c6', 471);

--  Changeset qa-diff.groovy::1374089559856-15::arcesino (generated)::(Checksum: 3:8c724bde4b8318f9014f0a8692b0a01d)
ALTER TABLE `order_detail` MODIFY `quantity` SMALLINT NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-15', '2.0.5', '3:8c724bde4b8318f9014f0a8692b0a01d', 472);

--  Changeset qa-diff.groovy::1374089559856-16::arcesino (generated)::(Checksum: 3:8f8b10aa9cfab0df2677d270d3088af6)
ALTER TABLE `orders` MODIFY `items_total` DECIMAL(13,2) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-16', '2.0.5', '3:8f8b10aa9cfab0df2677d270d3088af6', 473);

--  Changeset qa-diff.groovy::1374089559856-17::arcesino (generated)::(Checksum: 3:d3e72c64876c9bcf3b792d772e2bed6d)
ALTER TABLE `orders` MODIFY `shipping_total` DECIMAL(13,2) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-17', '2.0.5', '3:d3e72c64876c9bcf3b792d772e2bed6d', 474);

--  Changeset qa-diff.groovy::1374089559856-18::arcesino (generated)::(Checksum: 3:0e09311f1ed6831587ae396407016a8c)
ALTER TABLE `orders` MODIFY `total` DECIMAL(13,2) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-18', '2.0.5', '3:0e09311f1ed6831587ae396407016a8c', 475);

--  Changeset qa-diff.groovy::1374089559856-19::arcesino (generated)::(Checksum: 3:1e095e9dd53a73065496d555ec08816f)
ALTER TABLE `payment_method` MODIFY `display_order` SMALLINT NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-19', '2.0.5', '3:1e095e9dd53a73065496d555ec08816f', 476);

--  Changeset qa-diff.groovy::1374089559856-20::arcesino (generated)::(Checksum: 3:465d7ff36cdd616dffc04051e1afeb7a)
ALTER TABLE `payment_method` MODIFY `max_amount` DECIMAL(13,2) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-20', '2.0.5', '3:465d7ff36cdd616dffc04051e1afeb7a', 477);

--  Changeset qa-diff.groovy::1374089559856-21::arcesino (generated)::(Checksum: 3:3bbde980a6fd3427f453c0b3be060889)
ALTER TABLE `payment_method` MODIFY `min_amount` DECIMAL(13,2) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-21', '2.0.5', '3:3bbde980a6fd3427f453c0b3be060889', 478);

--  Changeset qa-diff.groovy::1374089559856-22::arcesino (generated)::(Checksum: 3:5a67658639c06fdbcaa227caec397e68)
ALTER TABLE `provider` MODIFY `name` VARCHAR(100) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-22', '2.0.5', '3:5a67658639c06fdbcaa227caec397e68', 479);

--  Changeset qa-diff.groovy::1374089559856-23::arcesino (generated)::(Checksum: 3:9f63ee3427120b29956b5b3d0a26cf33)
ALTER TABLE `provider_contact` MODIFY `email` VARCHAR(150) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-23', '2.0.5', '3:9f63ee3427120b29956b5b3d0a26cf33', 480);

--  Changeset qa-diff.groovy::1374089559856-24::arcesino (generated)::(Checksum: 3:43a91cbcbf2b81282e37bbbb148e8ab5)
ALTER TABLE `provider_contact` MODIFY `first_name` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-24', '2.0.5', '3:43a91cbcbf2b81282e37bbbb148e8ab5', 481);

--  Changeset qa-diff.groovy::1374089559856-25::arcesino (generated)::(Checksum: 3:7e7cb8657dd061f110a26a6ed084a5a5)
ALTER TABLE `provider_contact` MODIFY `last_name` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-25', '2.0.5', '3:7e7cb8657dd061f110a26a6ed084a5a5', 482);

--  Changeset qa-diff.groovy::1374089559856-26::arcesino (generated)::(Checksum: 3:d9b7bf63c2b9e44ead5367f0e5c20b38)
ALTER TABLE `provider_contact` MODIFY `main_telephone` VARCHAR(15) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-26', '2.0.5', '3:d9b7bf63c2b9e44ead5367f0e5c20b38', 483);

--  Changeset qa-diff.groovy::1374089559856-27::arcesino (generated)::(Checksum: 3:793bf225de721284c446f88a4b58492b)
ALTER TABLE `provider_fiscal_data` MODIFY `accounting_id` VARCHAR(32) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-27', '2.0.5', '3:793bf225de721284c446f88a4b58492b', 484);

--  Changeset qa-diff.groovy::1374089559856-28::arcesino (generated)::(Checksum: 3:acdf05f5f09bc24beb6c97e96a042d70)
ALTER TABLE `provider_fiscal_data` MODIFY `city` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-28', '2.0.5', '3:acdf05f5f09bc24beb6c97e96a042d70', 485);

--  Changeset qa-diff.groovy::1374089559856-29::arcesino (generated)::(Checksum: 3:2fec846446a338a5c3f405d8f63b3cf7)
ALTER TABLE `provider_fiscal_data` MODIFY `external_number` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-29', '2.0.5', '3:2fec846446a338a5c3f405d8f63b3cf7', 486);

--  Changeset qa-diff.groovy::1374089559856-30::arcesino (generated)::(Checksum: 3:6444b0b41c7bf5865435d7044254741e)
ALTER TABLE `provider_fiscal_data` MODIFY `location` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-30', '2.0.5', '3:6444b0b41c7bf5865435d7044254741e', 487);

--  Changeset qa-diff.groovy::1374089559856-31::arcesino (generated)::(Checksum: 3:cbfec4fc44ed446c7836195fd08c2771)
ALTER TABLE `provider_fiscal_data` MODIFY `name` VARCHAR(100) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-31', '2.0.5', '3:cbfec4fc44ed446c7836195fd08c2771', 488);

--  Changeset qa-diff.groovy::1374089559856-32::arcesino (generated)::(Checksum: 3:0115c27b3ecbc1133de0328cdbeee19c)
ALTER TABLE `provider_fiscal_data` MODIFY `state` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-32', '2.0.5', '3:0115c27b3ecbc1133de0328cdbeee19c', 489);

--  Changeset qa-diff.groovy::1374089559856-33::arcesino (generated)::(Checksum: 3:a8aa4cbb7003e61ed2c1966806de659f)
ALTER TABLE `provider_fiscal_data` MODIFY `street` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-33', '2.0.5', '3:a8aa4cbb7003e61ed2c1966806de659f', 490);

--  Changeset qa-diff.groovy::1374089559856-34::arcesino (generated)::(Checksum: 3:e152c7fd93a945c1c309f844c05829c7)
ALTER TABLE `provider_fiscal_data` MODIFY `zip_code` VARCHAR(10) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-34', '2.0.5', '3:e152c7fd93a945c1c309f844c05829c7', 491);

--  Changeset qa-diff.groovy::1374089559856-35::arcesino (generated)::(Checksum: 3:3d8f36e1d381859f820728bfd4037990)
ALTER TABLE `shipping_order` MODIFY `between_streets` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-35', '2.0.5', '3:3d8f36e1d381859f820728bfd4037990', 492);

--  Changeset qa-diff.groovy::1374089559856-36::arcesino (generated)::(Checksum: 3:d1ea90c09a5c1cc1c28f6fbbcf8dc993)
ALTER TABLE `shipping_order` MODIFY `contact_name` VARCHAR(100) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-36', '2.0.5', '3:d1ea90c09a5c1cc1c28f6fbbcf8dc993', 493);

--  Changeset qa-diff.groovy::1374089559856-37::arcesino (generated)::(Checksum: 3:888ed2013908ecaa0fdd484f6e3bfed7)
ALTER TABLE `shipping_order` MODIFY `contact_phone` VARCHAR(15) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-37', '2.0.5', '3:888ed2013908ecaa0fdd484f6e3bfed7', 494);

--  Changeset qa-diff.groovy::1374089559856-38::arcesino (generated)::(Checksum: 3:4469de0652cd500bb4c8dad43c6955c1)
ALTER TABLE `shipping_order` MODIFY `external_number` VARCHAR(25) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-38', '2.0.5', '3:4469de0652cd500bb4c8dad43c6955c1', 495);

--  Changeset qa-diff.groovy::1374089559856-39::arcesino (generated)::(Checksum: 3:6ccfd5cef841678d33db0e7cfed233d0)
ALTER TABLE `shipping_order` MODIFY `street` VARCHAR(50) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-39', '2.0.5', '3:6ccfd5cef841678d33db0e7cfed233d0', 496);

--  Changeset qa-diff.groovy::1374089559856-40::arcesino (generated)::(Checksum: 3:4b39c68cfc86aa60a0a998a9c7f3897a)
ALTER TABLE `venue` MODIFY `city` VARCHAR(75) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-40', '2.0.5', '3:4b39c68cfc86aa60a0a998a9c7f3897a', 497);

--  Changeset qa-diff.groovy::1374089559856-41::arcesino (generated)::(Checksum: 3:808119a05cc3fed686923768aae65252)
ALTER TABLE `venue` MODIFY `internal_number` VARCHAR(50) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-41', '2.0.5', '3:808119a05cc3fed686923768aae65252', 498);

--  Changeset qa-diff.groovy::1374089559856-42::arcesino (generated)::(Checksum: 3:bd858a9ee43bbe95fa49240568e8042f)
ALTER TABLE `venue` MODIFY `name` VARCHAR(100) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-42', '2.0.5', '3:bd858a9ee43bbe95fa49240568e8042f', 499);

--  Changeset qa-diff.groovy::1374089559856-43::arcesino (generated)::(Checksum: 3:ae698dcea8a170e6c93e28de6e92c09f)
ALTER TABLE `venue` MODIFY `state` VARCHAR(50) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-43', '2.0.5', '3:ae698dcea8a170e6c93e28de6e92c09f', 500);

--  Changeset qa-diff.groovy::1374089559856-44::arcesino (generated)::(Checksum: 3:44f1609689f0ebeff7d488840232a578)
ALTER TABLE `venue` MODIFY `street` VARCHAR(50) NOT NULL;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Not-Null Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-44', '2.0.5', '3:44f1609689f0ebeff7d488840232a578', 501);

--  Changeset qa-diff.groovy::1374089559856-45::arcesino (generated)::(Checksum: 3:10ea5e23c0a7ef613ecc7b94d739dee7)
ALTER TABLE `wish_list_item` ADD PRIMARY KEY (`user_id`, `brand_id`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Primary Key', 'EXECUTED', 'qa-diff.groovy', '1374089559856-45', '2.0.5', '3:10ea5e23c0a7ef613ecc7b94d739dee7', 502);

--  Changeset qa-diff.groovy::1374089559856-46::arcesino (generated)::(Checksum: 3:fd3c6fa6ae03bd1b83150842e30658a0)
ALTER TABLE `winbits_qa`.`item_group_sku_profile` DROP FOREIGN KEY `FK4F90F49BA210CA8`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-46', '2.0.5', '3:fd3c6fa6ae03bd1b83150842e30658a0', 503);

--  Changeset qa-diff.groovy::1374089559856-47::arcesino (generated)::(Checksum: 3:aa14cc36f3155e5d0ab44b47b60ea52d)
ALTER TABLE `winbits_qa`.`item_group_sku_profile` DROP FOREIGN KEY `FK4F90F49BAA7A42E5`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-47', '2.0.5', '3:aa14cc36f3155e5d0ab44b47b60ea52d', 504);

--  Changeset qa-diff.groovy::1374089559856-48::arcesino (generated)::(Checksum: 3:76721f696df82bae9c968d93fcf0f025)
ALTER TABLE `winbits_qa`.`order_payment` DROP FOREIGN KEY `FKC660321597139040`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-48', '2.0.5', '3:76721f696df82bae9c968d93fcf0f025', 505);

--  Changeset qa-diff.groovy::1374089559856-49::arcesino (generated)::(Checksum: 3:0a2d8731f466df5b8f4d3430925c8fa7)
ALTER TABLE `winbits_qa`.`social_account` DROP FOREIGN KEY `FK50078B5BF3235F1B`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-49', '2.0.5', '3:0a2d8731f466df5b8f4d3430925c8fa7', 506);

--  Changeset qa-diff.groovy::1374089559856-50::arcesino (generated)::(Checksum: 3:d04e621494098d885f626f3ec91c92c8)
ALTER TABLE `winbits_qa`.`social_account` DROP FOREIGN KEY `FK50078B5B6F4B0AA9`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-50', '2.0.5', '3:d04e621494098d885f626f3ec91c92c8', 507);

--  Changeset qa-diff.groovy::1374089559856-57::arcesino (generated)::(Checksum: 3:93a1c1a7a581835d00c6371dfe24ba0f)
DROP INDEX `name` ON `attribute_type`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-57', '2.0.5', '3:93a1c1a7a581835d00c6371dfe24ba0f', 508);

--  Changeset qa-diff.groovy::1374089559856-58::arcesino (generated)::(Checksum: 3:0b5992e901582aa3254bb9d2e5e40a24)
DROP INDEX `name` ON `billing_type`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-58', '2.0.5', '3:0b5992e901582aa3254bb9d2e5e40a24', 509);

--  Changeset qa-diff.groovy::1374089559856-59::arcesino (generated)::(Checksum: 3:0bc49c13003b2ac7b54bc4bac2e22fc2)
DROP INDEX `code` ON `configuration`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-59', '2.0.5', '3:0bc49c13003b2ac7b54bc4bac2e22fc2', 510);

--  Changeset qa-diff.groovy::1374089559856-60::arcesino (generated)::(Checksum: 3:463a71f753a5dd9eba82d5cff0145061)
DROP INDEX `name` ON `image_type`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-60', '2.0.5', '3:463a71f753a5dd9eba82d5cff0145061', 511);

--  Changeset qa-diff.groovy::1374089559856-61::arcesino (generated)::(Checksum: 3:2b3e7f0aa10145597ccd5018245ce7c1)
DROP INDEX `name` ON `item_status`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-61', '2.0.5', '3:2b3e7f0aa10145597ccd5018245ce7c1', 512);

--  Changeset qa-diff.groovy::1374089559856-62::arcesino (generated)::(Checksum: 3:5f5e315d51d0cf0ae6142cc4ca05ae80)
DROP INDEX `name` ON `order_detail_status`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-62', '2.0.5', '3:5f5e315d51d0cf0ae6142cc4ca05ae80', 513);

--  Changeset qa-diff.groovy::1374089559856-63::arcesino (generated)::(Checksum: 3:ed000fa751f25ed9ebf229e698658009)
DROP INDEX `name` ON `order_payment_status`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-63', '2.0.5', '3:ed000fa751f25ed9ebf229e698658009', 514);

--  Changeset qa-diff.groovy::1374089559856-64::arcesino (generated)::(Checksum: 3:0bf35fa1326093465a411afa59b10606)
DROP INDEX `name` ON `order_status`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-64', '2.0.5', '3:0bf35fa1326093465a411afa59b10606', 515);

--  Changeset qa-diff.groovy::1374089559856-65::arcesino (generated)::(Checksum: 3:859d2afdbf12c0fab64620a4cef1d71e)
DROP INDEX `name` ON `provider_contact_type`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-65', '2.0.5', '3:859d2afdbf12c0fab64620a4cef1d71e', 516);

--  Changeset qa-diff.groovy::1374089559856-66::arcesino (generated)::(Checksum: 3:cf7afb97d108a87567319bfc9f3ed8fc)
DROP INDEX `authority` ON `role`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-66', '2.0.5', '3:cf7afb97d108a87567319bfc9f3ed8fc', 517);

--  Changeset qa-diff.groovy::1374089559856-67::arcesino (generated)::(Checksum: 3:2b5c9e403d0df46a98f941c5ac2692f4)
DROP INDEX `name` ON `telephone_type`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-67', '2.0.5', '3:2b5c9e403d0df46a98f941c5ac2692f4', 518);

--  Changeset qa-diff.groovy::1374089559856-68::arcesino (generated)::(Checksum: 3:9f5c55c75063f0aff0686c41301fc516)
DROP INDEX `email` ON `user`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-68', '2.0.5', '3:9f5c55c75063f0aff0686c41301fc516', 519);

--  Changeset qa-diff.groovy::1374089559856-69::arcesino (generated)::(Checksum: 3:2b5c6a17502e9dee9af02c5982ad520f)
DROP INDEX `base_url` ON `vertical`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-69', '2.0.5', '3:2b5c6a17502e9dee9af02c5982ad520f', 520);

--  Changeset qa-diff.groovy::1374089559856-70::arcesino (generated)::(Checksum: 3:4ff7d13d83e7eb1e5615f59a185a347a)
DROP INDEX `name` ON `vertical`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-70', '2.0.5', '3:4ff7d13d83e7eb1e5615f59a185a347a', 521);

--  Changeset qa-diff.groovy::1374089559856-71::arcesino (generated)::(Checksum: 3:7a4910cf5b66b52b819b20ac49e9dad6)
DROP INDEX `name` ON `waiting_list_item_status`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-71', '2.0.5', '3:7a4910cf5b66b52b819b20ac49e9dad6', 522);

--  Changeset qa-diff.groovy::1374089559856-72::arcesino (generated)::(Checksum: 3:13da82086d29d506a13e27754af567e7)
CREATE UNIQUE INDEX `name_uniq_1374087479801` ON `attribute_type`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-72', '2.0.5', '3:13da82086d29d506a13e27754af567e7', 523);

--  Changeset qa-diff.groovy::1374089559856-73::arcesino (generated)::(Checksum: 3:1cb97eace7511ca193d4f3d80a1304e2)
CREATE UNIQUE INDEX `name_uniq_1374087479807` ON `billing_type`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-73', '2.0.5', '3:1cb97eace7511ca193d4f3d80a1304e2', 524);

--  Changeset qa-diff.groovy::1374089559856-74::arcesino (generated)::(Checksum: 3:95efe4e1a900232742e926d3b6b6a84b)
CREATE UNIQUE INDEX `code_uniq_1374087479817` ON `configuration`(`code`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-74', '2.0.5', '3:95efe4e1a900232742e926d3b6b6a84b', 525);

--  Changeset qa-diff.groovy::1374089559856-75::arcesino (generated)::(Checksum: 3:fc313a527336e077fe78492031e22098)
CREATE UNIQUE INDEX `name_uniq_1374087479830` ON `image_type`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-75', '2.0.5', '3:fc313a527336e077fe78492031e22098', 526);

--  Changeset qa-diff.groovy::1374089559856-76::arcesino (generated)::(Checksum: 3:15727700c9d04dd8e7e28a2c9ea53ace)
CREATE UNIQUE INDEX `name_uniq_1374087479848` ON `item_status`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-76', '2.0.5', '3:15727700c9d04dd8e7e28a2c9ea53ace', 527);

--  Changeset qa-diff.groovy::1374089559856-77::arcesino (generated)::(Checksum: 3:66adbb07ad37a1c1b9d69f44f402085e)
CREATE UNIQUE INDEX `name_uniq_1374087479853` ON `order_detail_status`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-77', '2.0.5', '3:66adbb07ad37a1c1b9d69f44f402085e', 528);

--  Changeset qa-diff.groovy::1374089559856-78::arcesino (generated)::(Checksum: 3:f4b840201a7c6ab22e2375224bf546ee)
CREATE UNIQUE INDEX `name_uniq_1374087479857` ON `order_payment_status`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-78', '2.0.5', '3:f4b840201a7c6ab22e2375224bf546ee', 529);

--  Changeset qa-diff.groovy::1374089559856-79::arcesino (generated)::(Checksum: 3:e9fdca9d8e1bb345b349250244af6b1b)
CREATE UNIQUE INDEX `name_uniq_1374087479858` ON `order_status`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-79', '2.0.5', '3:e9fdca9d8e1bb345b349250244af6b1b', 530);

--  Changeset qa-diff.groovy::1374089559856-80::arcesino (generated)::(Checksum: 3:15cefd7ee77458abc4c94dfb8ab33e4e)
CREATE UNIQUE INDEX `name_uniq_1374087479875` ON `provider_contact_type`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-80', '2.0.5', '3:15cefd7ee77458abc4c94dfb8ab33e4e', 531);

--  Changeset qa-diff.groovy::1374089559856-81::arcesino (generated)::(Checksum: 3:54f3e7c3c56ef3ad9cff171ae957153f)
CREATE UNIQUE INDEX `authority_uniq_1374087479881` ON `role`(`authority`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-81', '2.0.5', '3:54f3e7c3c56ef3ad9cff171ae957153f', 532);

--  Changeset qa-diff.groovy::1374089559856-82::arcesino (generated)::(Checksum: 3:e532a4a504dd06df58dca5801b9ed980)
CREATE UNIQUE INDEX `name_uniq_1374087479895` ON `telephone_type`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-82', '2.0.5', '3:e532a4a504dd06df58dca5801b9ed980', 533);

--  Changeset qa-diff.groovy::1374089559856-83::arcesino (generated)::(Checksum: 3:14bb9ddbaa33c696a612e14ea3dcb6a0)
CREATE UNIQUE INDEX `email_uniq_1374087479896` ON `user`(`email`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-83', '2.0.5', '3:14bb9ddbaa33c696a612e14ea3dcb6a0', 534);

--  Changeset qa-diff.groovy::1374089559856-84::arcesino (generated)::(Checksum: 3:c5e6980f5db7629f3d86062cb65d5cb5)
CREATE UNIQUE INDEX `base_url_uniq_1374087479899` ON `vertical`(`base_url`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-84', '2.0.5', '3:c5e6980f5db7629f3d86062cb65d5cb5', 535);

--  Changeset qa-diff.groovy::1374089559856-85::arcesino (generated)::(Checksum: 3:9be8f9175515c43156712714429ccb25)
CREATE UNIQUE INDEX `name_uniq_1374087479901` ON `vertical`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-85', '2.0.5', '3:9be8f9175515c43156712714429ccb25', 536);

--  Changeset qa-diff.groovy::1374089559856-86::arcesino (generated)::(Checksum: 3:6c8a650d224343851f23fd5405c5ed16)
CREATE UNIQUE INDEX `name_uniq_1374087479902` ON `waiting_list_item_status`(`name`);

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Create Index', 'EXECUTED', 'qa-diff.groovy', '1374089559856-86', '2.0.5', '3:6c8a650d224343851f23fd5405c5ed16', 537);

--  Changeset qa-diff.groovy::1374089559856-87::arcesino (generated)::(Checksum: 3:e3a566d97ab70b1b2f3ff1c730dd2f5e)
ALTER TABLE `item_group` DROP COLUMN `name`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-87', '2.0.5', '3:e3a566d97ab70b1b2f3ff1c730dd2f5e', 538);

--  Changeset qa-diff.groovy::1374089559856-88::arcesino (generated)::(Checksum: 3:b1e3dfa98a5eb7e3e81572b4ee0b9571)
ALTER TABLE `order_payment` DROP COLUMN `order_payment_status_id`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-88', '2.0.5', '3:b1e3dfa98a5eb7e3e81572b4ee0b9571', 539);

--  Changeset qa-diff.groovy::1374089559856-89::arcesino (generated)::(Checksum: 3:729a1116cfbc50b3cf3da21871098219)
ALTER TABLE `sku` DROP COLUMN `item_status_id`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-89', '2.0.5', '3:729a1116cfbc50b3cf3da21871098219', 540);

--  Changeset qa-diff.groovy::1374089559856-90::arcesino (generated)::(Checksum: 3:d95d5b904acebca7523814e87eea0588)
ALTER TABLE `social_provider` DROP COLUMN `oauth_url`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-90', '2.0.5', '3:d95d5b904acebca7523814e87eea0588', 541);

--  Changeset qa-diff.groovy::1374089559856-91::arcesino (generated)::(Checksum: 3:ae6fa3a8bbe0183fc714b935d256d2be)
ALTER TABLE `social_provider` DROP COLUMN `revoke_url`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Column', 'EXECUTED', 'qa-diff.groovy', '1374089559856-91', '2.0.5', '3:ae6fa3a8bbe0183fc714b935d256d2be', 542);

--  Changeset qa-diff.groovy::1374089559856-92::arcesino (generated)::(Checksum: 3:1ad7cfb4576a5e975d2bfe12a5829b7b)
DROP TABLE `item_group_sku_profile`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Table', 'EXECUTED', 'qa-diff.groovy', '1374089559856-92', '2.0.5', '3:1ad7cfb4576a5e975d2bfe12a5829b7b', 543);

--  Changeset qa-diff.groovy::1374089559856-93::arcesino (generated)::(Checksum: 3:b01f361632e2b59a82ed64afa11853ee)
DROP TABLE `social_account`;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Drop Table', 'EXECUTED', 'qa-diff.groovy', '1374089559856-93', '2.0.5', '3:b01f361632e2b59a82ed64afa11853ee', 544);

--  Changeset qa-diff.groovy::1374089559855-50::arcesino (generated)::(Checksum: 3:c31ab3aa91c017799bb9b50f4bef36d2)
INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Grails Change', 'EXECUTED', 'qa-diff.groovy', '1374089559855-50', '2.0.5', '3:c31ab3aa91c017799bb9b50f4bef36d2', 545);

update item_group set income_type_id = 1;
update item_group_profile set status_id = 1;
update sku set status_id = 1;
update sku_profile set item_group_profile_id = 1;

--  Changeset qa-diff.groovy::1374089559856-51::arcesino (generated)::(Checksum: 3:6e8e0f1cf480470a88068cf5d1f7524e)
ALTER TABLE `winbits_qa`.`item_group` ADD CONSTRAINT `FK8AFCFA53648E4EF9` FOREIGN KEY (`income_type_id`) REFERENCES `winbits_qa`.`income_type` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-51', '2.0.5', '3:6e8e0f1cf480470a88068cf5d1f7524e', 546);

--  Changeset qa-diff.groovy::1374089559856-52::arcesino (generated)::(Checksum: 3:c4bd472bd8daf91023fa69a8ceb54342)
ALTER TABLE `winbits_qa`.`item_group_profile` ADD CONSTRAINT `FK2FB127BD7B43BAB3` FOREIGN KEY (`status_id`) REFERENCES `winbits_qa`.`item_status` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-52', '2.0.5', '3:c4bd472bd8daf91023fa69a8ceb54342', 547);

--  Changeset qa-diff.groovy::1374089559856-53::arcesino (generated)::(Checksum: 3:7f14ed3252b4d97a0d7a5dd9a0119471)
ALTER TABLE `winbits_qa`.`sku` ADD CONSTRAINT `FK1BD1D7B43BAB3` FOREIGN KEY (`status_id`) REFERENCES `winbits_qa`.`item_status` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-53', '2.0.5', '3:7f14ed3252b4d97a0d7a5dd9a0119471', 548);

--  Changeset qa-diff.groovy::1374089559856-54::arcesino (generated)::(Checksum: 3:e43770acf8b838af4ffb636ee363a48f)
ALTER TABLE `winbits_qa`.`sku_profile` ADD CONSTRAINT `FK3AD380874384DA80` FOREIGN KEY (`item_group_profile_id`) REFERENCES `winbits_qa`.`item_group_profile` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-54', '2.0.5', '3:e43770acf8b838af4ffb636ee363a48f', 549);

--  Changeset qa-diff.groovy::1374089559856-55::arcesino (generated)::(Checksum: 3:a367a98686c635be2e86ade6b505ee78)
ALTER TABLE `winbits_qa`.`wish_list_item` ADD CONSTRAINT `FK21D27C7CF85BA354` FOREIGN KEY (`brand_id`) REFERENCES `winbits_qa`.`brand` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-55', '2.0.5', '3:a367a98686c635be2e86ade6b505ee78', 550);

--  Changeset qa-diff.groovy::1374089559856-56::arcesino (generated)::(Checksum: 3:f0d5b9347d1cceff85d1bad01973acaa)
ALTER TABLE `winbits_qa`.`wish_list_item` ADD CONSTRAINT `FK21D27C7C6F4B0AA9` FOREIGN KEY (`user_id`) REFERENCES `winbits_qa`.`user` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO `database_changelog` (`AUTHOR`, `COMMENTS`, `DATEEXECUTED`, `DESCRIPTION`, `EXECTYPE`, `FILENAME`, `ID`, `LIQUIBASE`, `MD5SUM`, `ORDEREXECUTED`) VALUES ('arcesino (generated)', '', NOW(), 'Add Foreign Key Constraint', 'EXECUTED', 'qa-diff.groovy', '1374089559856-56', '2.0.5', '3:f0d5b9347d1cceff85d1bad01973acaa', 551);

--  Release Database Lock
