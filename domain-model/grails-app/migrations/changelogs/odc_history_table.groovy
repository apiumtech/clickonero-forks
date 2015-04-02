package changelogs

databaseChangeLog = {

  changeSet(author: "Rodolfo (generated)", id: "991250081014-1") {
    sql("CREATE TABLE `odc_history` (\n" +
        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
        "  `odc_id` bigint(20) DEFAULT NULL,\n" +
        "  `odc_detail_id` bigint(20) DEFAULT NULL,\n" +
        "  `sku_id` bigint(20) DEFAULT NULL,\n" +
        "  `date_created` datetime DEFAULT NULL,\n" +
        "  `quantity_received` int(11) DEFAULT NULL,\n" +
        "  `cost` decimal(10,3) DEFAULT NULL,\n" +
        "  `quantity_sold` int(11) DEFAULT NULL,\n" +
        "  PRIMARY KEY (`id`),\n" +
        "  KEY `odc_detail_id_idx` (`odc_id`),\n" +
        "  KEY `sku_id_idx` (`sku_id`),\n" +
        "  CONSTRAINT `odc_detail_id` FOREIGN KEY (`odc_id`) REFERENCES `odc_detail` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
        "  CONSTRAINT `odc_id` FOREIGN KEY (`odc_id`) REFERENCES `odc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
        "  CONSTRAINT `sku_id` FOREIGN KEY (`sku_id`) REFERENCES `sku` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;")
  }

  changeSet(author: "Rodolfo (generated)", id: "991250081014-2") {
    sql("CREATE TABLE `order_history` (\n" +
        "  `order_detail_id` bigint(20) DEFAULT NULL,\n" +
        "  `odc_history_id` int(11) DEFAULT NULL,\n" +
        "  `quantity` int(11) DEFAULT NULL,\n" +
        "  KEY `odc_history_id_idx` (`odc_history_id`),\n" +
        "  KEY `order_detail_id_idx` (`order_detail_id`),\n" +
        "  CONSTRAINT `odc_history_id` FOREIGN KEY (`odc_history_id`) REFERENCES `odc_history` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
        "  CONSTRAINT `order_detail_id` FOREIGN KEY (`order_detail_id`) REFERENCES `order_detail` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;")
  }
}