package changelogs

databaseChangeLog = {

  changeSet(author: "arcesino (generated)", id: "1405979714134-1") {
    createTable(tableName: "odc_mongo") {
      column(autoIncrement: "true", name: "id", type: "bigint") {
        constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_mongoPK")
      }

      column(name: "version", type: "bigint") {
        constraints(nullable: "false")
      }

      column(name: "date_created", type: "datetime") {
        constraints(nullable: "false")
      }

      column(name: "input_id", type: "varchar(255)") {
        constraints(nullable: "false")
      }

      column(name: "last_updated", type: "datetime") {
        constraints(nullable: "false")
      }

      column(name: "odc_id", type: "bigint") {
        constraints(nullable: "false")
      }

      column(name: "output_id", type: "varchar(255)")

      column(name: "request_id", type: "varchar(255)") {
        constraints(nullable: "false")
      }

      column(name: "wms_status_id", type: "bigint") {
        constraints(nullable: "false")
      }
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-2") {
    createTable(tableName: "odc_mongo_wms_error") {
      column(name: "odc_mongo_wms_errors_id", type: "bigint")

      column(name: "wms_error_id", type: "bigint")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-3") {
    createTable(tableName: "tags_item_group_profile") {
      column(autoIncrement: "true", name: "id", type: "bigint") {
        constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tags_item_groPK")
      }

      column(name: "version", type: "bigint") {
        constraints(nullable: "false")
      }

      column(name: "cat_tag_id", type: "integer") {
        constraints(nullable: "false")
      }

      column(name: "item_group_profile_id", type: "bigint") {
        constraints(nullable: "false")
      }
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-4") {
    dropNotNullConstraint(columnDataType: "bigint", columnName: "income_type_id", tableName: "item_group")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-5") {
    dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "model", tableName: "item_group")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-6") {
    dropForeignKeyConstraint(baseTableName: "sku", constraintName: "FK1BD1D7DE85A54")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-7") {
    dropForeignKeyConstraint(baseTableName: "tags_item_group", constraintName: "FK83D5C239C0BC17AD")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-8") {
    dropForeignKeyConstraint(baseTableName: "venue", constraintName: "FK6AE6A6F6CC92E0")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-14") {
    dropIndex(indexName: "username_uniq_1396151817256", tableName: "admin_user")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-15") {
    createIndex(indexName: "FKFAFB16A33FBFC989", tableName: "odc_mongo") {
      column(name: "wms_status_id")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-16") {
    createIndex(indexName: "FKFAFB16A37A0ED1AE", tableName: "odc_mongo") {
      column(name: "odc_id")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-17") {
    createIndex(indexName: "FK6B73E90A7982C1CB", tableName: "odc_mongo_wms_error") {
      column(name: "wms_error_id")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-18") {
    createIndex(indexName: "FK6B73E90A830F16A3", tableName: "odc_mongo_wms_error") {
      column(name: "odc_mongo_wms_errors_id")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-19") {
    createIndex(indexName: "FK384EA9A34384DA80", tableName: "tags_item_group_profile") {
      column(name: "item_group_profile_id")
    }
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-20") {
    dropColumn(columnName: "venue_id", tableName: "sku")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-21") {
    dropTable(tableName: "tags_item_group")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-22") {
    dropTable(tableName: "venue")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-9") {
    addForeignKeyConstraint(baseColumnNames: "odc_id", baseTableName: "odc_mongo", constraintName: "FKFAFB16A37A0ED1AE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc", referencesUniqueColumn: "false")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-10") {
    addForeignKeyConstraint(baseColumnNames: "wms_status_id", baseTableName: "odc_mongo", constraintName: "FKFAFB16A33FBFC989", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_status", referencesUniqueColumn: "false")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-11") {
    addForeignKeyConstraint(baseColumnNames: "odc_mongo_wms_errors_id", baseTableName: "odc_mongo_wms_error", constraintName: "FK6B73E90A830F16A3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_mongo", referencesUniqueColumn: "false")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-12") {
    addForeignKeyConstraint(baseColumnNames: "wms_error_id", baseTableName: "odc_mongo_wms_error", constraintName: "FK6B73E90A7982C1CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_error", referencesUniqueColumn: "false")
  }

  changeSet(author: "arcesino (generated)", id: "1405979714134-13") {
    addForeignKeyConstraint(baseColumnNames: "item_group_profile_id", baseTableName: "tags_item_group_profile", constraintName: "FK384EA9A34384DA80", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group_profile", referencesUniqueColumn: "false")
  }
}
