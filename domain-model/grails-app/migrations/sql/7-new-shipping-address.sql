ALTER TABLE `shipping_address`
ADD COLUMN `last_name2` VARCHAR(255) NULL;

ALTER TABLE `shipping_address`
CHANGE COLUMN `between_streets` `between_streets` VARCHAR(255) NULL ;