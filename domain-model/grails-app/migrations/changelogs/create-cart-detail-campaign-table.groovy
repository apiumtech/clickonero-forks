package changelogs

databaseChangeLog = {

	changeSet(author: "Jr (generated)", id: "1258795058264-3") {
    sql("CREATE TABLE `cart_detail_campaign` (\n" +
        "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
        "  `version` bigint(20) NOT NULL,\n" +
        "  `campaign_id` bigint(20) NOT NULL,\n" +
        "  `campaign_type` bigint(20) NOT NULL,\n" +
        "  `cart_detail_id` bigint(20) NOT NULL,\n" +
        "  PRIMARY KEY (`id`),\n" +
        "  UNIQUE KEY `cart_detail_id` (`cart_detail_id`),\n" +
        "  KEY `FK9DF1761F8B698573` (`cart_detail_id`),\n" +
        "  CONSTRAINT `FK9DF1761F8B698573` FOREIGN KEY (`cart_detail_id`) REFERENCES `cart_detail` (`id`)\n" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci")
	}
}
