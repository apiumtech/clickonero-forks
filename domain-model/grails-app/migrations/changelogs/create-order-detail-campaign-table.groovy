package changelogs

databaseChangeLog = {

	changeSet(author: "Jr (generated)", id: "1258795071409-3") {
    sql("CREATE TABLE `order_detail_campaign` (\n" +
        "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
        "  `version` bigint(20) NOT NULL,\n" +
        "  `campaign_id` bigint(20) NOT NULL,\n" +
        "  `campaign_type` bigint(20) NOT NULL,\n" +
        "  `order_detail_id` bigint(20) NOT NULL,\n" +
        "  PRIMARY KEY (`id`),\n" +
        "  UNIQUE KEY `order_detail_id` (`order_detail_id`),\n" +
        "  KEY `FK7302664D5F83A7DF` (`order_detail_id`),\n" +
        "  CONSTRAINT `FK7302664D5F83A7DF` FOREIGN KEY (`order_detail_id`) REFERENCES `order_detail` (`id`)\n" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;")
	}
}
