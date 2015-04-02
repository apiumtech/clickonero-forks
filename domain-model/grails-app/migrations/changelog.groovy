databaseChangeLog = {

	changeSet(author: "winbits (generated)", id: "1396151817471-1") {
		createTable(tableName: "admin_requestmap") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "admin_requestPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "config_attribute", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-2") {
		createTable(tableName: "admin_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "admin_rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-3") {
		createTable(tableName: "admin_role_admin_requestmap") {
			column(name: "admin_role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "admin_requestmap_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-4") {
		createTable(tableName: "admin_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "admin_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "department_id", type: "bigint")

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_login_date", type: "datetime")

			column(name: "last_name", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)")

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-5") {
		createTable(tableName: "admin_user_admin_role") {
			column(name: "admin_role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "admin_user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-6") {
		createTable(tableName: "api_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "api_rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-7") {
		createTable(tableName: "api_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "api_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "salt", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-8") {
		createTable(tableName: "api_user_api_role") {
			column(name: "api_role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "api_user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-9") {
		createTable(tableName: "attribute") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "attributePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "label", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-10") {
		createTable(tableName: "attribute_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "attribute_typPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-11") {
		createTable(tableName: "bank") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bankPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "installment", type: "bit")

			column(name: "logo", type: "varchar(255)")

			column(name: "name", type: "varchar(50)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-12") {
		createTable(tableName: "bank_account") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bank_accountPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_number", type: "varchar(20)") {
				constraints(nullable: "false")
			}

			column(name: "bank_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "interbank_account_number", type: "varchar(18)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "main", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "provider_fiscal_data_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-13") {
		createTable(tableName: "billing_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "billing_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-14") {
		createTable(tableName: "bin") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "binPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bin", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "credit_card_type_id", type: "bigint")

			column(name: "issuer_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "logo", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-15") {
		createTable(tableName: "bom") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bomPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "income_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-16") {
		createTable(tableName: "brand") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "brandPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "logo", type: "varchar(255)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-17") {
		createTable(tableName: "card_subscription") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "card_subscripPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bin_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "principal", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "subscription_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-18") {
		createTable(tableName: "carrier") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "carrierPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-19") {
		createTable(tableName: "carrier_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "carrier_statuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "carrier_id", type: "bigint")

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-20") {
		createTable(tableName: "carrier_status_sku_outcome_status") {
			column(name: "carrier_status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sku_outcome_status_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-21") {
		createTable(tableName: "cart") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cartPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bits_total", type: "MEDIUMINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "items_count", type: "SMALLINT") {
				constraints(nullable: "false")
			}

			column(name: "items_total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "shipping_total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-22") {
		createTable(tableName: "cart_detail") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cart_detailPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cart_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_profile_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cart_details_idx", type: "integer")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-23") {
		createTable(tableName: "category_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "category_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-24") {
		createTable(tableName: "configuration") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "configurationPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "value", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-25") {
		createTable(tableName: "country") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "countryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "currency", type: "varchar(5)") {
				constraints(nullable: "false")
			}

			column(name: "default_locale", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "flag", type: "varchar(255)")

			column(name: "iso_code", type: "varchar(5)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-26") {
		createTable(tableName: "credit_card_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "credit_card_tPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(50)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-27") {
		createTable(tableName: "delivery_date_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "delivery_datePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-28") {
		createTable(tableName: "department") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "departmentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-29") {
		createTable(tableName: "guide") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "guidePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "carrier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "carrier_status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "guide", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-30") {
		createTable(tableName: "guide_carrier_history_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "guide_carrierPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "carrier_status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "guide_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "transaction_id", type: "varchar(255)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-31") {
		createTable(tableName: "hsbc_payment_control") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "hsbc_payment_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(250)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-32") {
		createTable(tableName: "image") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "imagePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "image_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "ordinal", type: "integer")

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-33") {
		createTable(tableName: "image_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "image_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-34") {
		createTable(tableName: "in_out_sku_history") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "in_out_sku_hiPK")
			}

			column(name: "balance", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-35") {
		createTable(tableName: "in_out_sku_history_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "in_out_sku_hiPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-36") {
		createTable(tableName: "income_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "income_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-37") {
		createTable(tableName: "item") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "itemPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "attribute_label", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "attribute_name", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "attribute_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "attribute_value", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "item_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "priority", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-38") {
		createTable(tableName: "item_group") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_groupPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "active_end", type: "datetime")

			column(name: "active_start", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "brand_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "category_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "delivery_date_days", type: "integer")

			column(name: "delivery_date_type_id", type: "bigint")

			column(name: "income_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "item_group_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "item_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "model", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "requires_shipping", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "sales_department_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "seller_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sub_category_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_marketing_type_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-39") {
		createTable(tableName: "item_group_profile") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_group_prPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "active_end", type: "datetime")

			column(name: "active_start", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "cashback", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "conditions", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "details", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "item_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "long_description", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "marketing_sale", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "max_per_order", type: "mediumint") {
				constraints(nullable: "false")
			}

			column(name: "max_per_user", type: "mediumint") {
				constraints(nullable: "false")
			}

			column(name: "min_per_order", type: "mediumint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "''", name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "short_description", type: "varchar(256)") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-40") {
		createTable(tableName: "item_group_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_group_tyPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-41") {
		createTable(tableName: "item_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_statusPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-42") {
		createTable(tableName: "item_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-43") {
		createTable(tableName: "odc") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odcPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "delivery_date", type: "datetime")

			column(name: "income_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "payment_conditions", type: "varchar(255)")

			column(name: "payment_max_days", type: "integer")

			column(name: "payment_status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total_received", type: "decimal(19,2)")

			column(name: "total_required", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint")

			column(name: "wms_status_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-44") {
		createTable(tableName: "odc_detail") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_detailPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "odc_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity_received", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "quantity_required", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total_cost_received", type: "decimal(19,2)")

			column(name: "total_cost_required", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-45") {
		createTable(tableName: "odc_detail_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_detail_stPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-46") {
		createTable(tableName: "odc_payment") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_paymentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "amount", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "odc_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-47") {
		createTable(tableName: "odc_payment_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_payment_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-48") {
		createTable(tableName: "odc_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_statusPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-49") {
		createTable(tableName: "odc_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "odc_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-50") {
		createTable(tableName: "odc_wms_error") {
			column(name: "odc_wms_errors_id", type: "bigint")

			column(name: "wms_error_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-51") {
		createTable(tableName: "order_detail") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "order_detailPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "bits_amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "cash_amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "output_quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "smallint") {
				constraints(nullable: "false")
			}

			column(name: "refunded_detail_id", type: "bigint")

			column(name: "shipping_amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "sku_profile_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-52") {
		createTable(tableName: "order_detail_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "order_detail_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-53") {
		createTable(tableName: "order_payment") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "order_paymentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "amount", type: "decimal(13,2)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "paid_date", type: "datetime")

			column(name: "payment_capture", type: "longtext")

			column(name: "payment_method_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "reference", type: "varchar(255)")

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-54") {
		createTable(tableName: "order_payment_bins") {
			column(name: "bin_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "order_payment_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-55") {
		createTable(tableName: "order_payment_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "order_paymentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-56") {
		createTable(tableName: "order_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "order_statusPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-57") {
		createTable(tableName: "orders") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ordersPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "bits_total", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cash_total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "items_total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "order_number", type: "varchar(20)")

			column(name: "paid_date", type: "datetime")

			column(name: "sales_agent_id", type: "bigint")

			column(name: "shipping_total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-58") {
		createTable(tableName: "outcome_request_parent") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "outcome_requePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "wms_status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "shipping_order_id", type: "bigint")

			column(name: "status_id", type: "bigint")

			column(name: "approver_id", type: "bigint")

			column(name: "receiver", type: "varchar(255)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-59") {
		createTable(tableName: "outcome_request_parent_wms_error") {
			column(name: "outcome_request_parent_wms_errors_id", type: "bigint")

			column(name: "wms_error_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-60") {
		createTable(tableName: "outcome_request_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "outcome_requePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-61") {
		createTable(tableName: "outcome_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "outcome_typePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-62") {
		createTable(tableName: "payment_method") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "payment_methoPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "active", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "display_order", type: "smallint") {
				constraints(nullable: "false")
			}

			column(name: "expire_minutes", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "identifier", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "logo", type: "varchar(255)")

			column(name: "max_amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "min_amount", type: "decimal(13,2)") {
				constraints(nullable: "false")
			}

			column(name: "offline", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-63") {
		createTable(tableName: "payment_method_exclusions") {
			column(name: "item_group_id", type: "bigint")

			column(name: "payment_method_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-64") {
		createTable(tableName: "profile") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "profilePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "birthdate", type: "date")

			column(name: "bits_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "complete_profile_remainders", type: "SMALLINT") {
				constraints(nullable: "false")
			}

			column(name: "complete_register", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "fraudulent", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "gender", type: "varchar(255)")

			column(name: "last_login", type: "datetime")

			column(name: "last_name", type: "varchar(75)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "locale", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "location", type: "varchar(75)")

			column(name: "name", type: "varchar(75)")

			column(name: "newsletter_active", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "newsletter_format", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "newsletter_periodicity", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "phone", type: "varchar(255)")

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(5)")

			column(name: "zip_code_info_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-65") {
		createTable(tableName: "profile_subscriptions") {
			column(name: "subscription_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "profile_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-66") {
		createTable(tableName: "provider") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "providerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "varchar(255)")

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-67") {
		createTable(tableName: "provider_contact") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "provider_contPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(150)") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "main_telephone", type: "varchar(15)") {
				constraints(nullable: "false")
			}

			column(name: "main_telephone_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "provider_contact_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "secondary_telephone", type: "varchar(15)")

			column(name: "secondary_telephone_type_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-68") {
		createTable(tableName: "provider_contact_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "provider_contPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-69") {
		createTable(tableName: "provider_fiscal_data") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "provider_fiscPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "accounting_id", type: "varchar(32)") {
				constraints(nullable: "false")
			}

			column(name: "billing_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "external_number", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "internal_number", type: "varchar(25)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "location", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "payment_day", type: "integer")

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "street", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "tax_id", type: "varchar(32)")

			column(name: "zip_code", type: "varchar(10)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-70") {
		createTable(tableName: "redeem_service_without_coupon") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "redeem_servicPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "order_detail_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-71") {
		createTable(tableName: "refund_detail") {
			column(name: "order_payment_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "order_detail_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "payment_date", type: "datetime")

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_atc", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "user_financial", type: "varchar(100)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-72") {
		createTable(tableName: "refund_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "refund_statusPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-73") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-74") {
		createTable(tableName: "sales_agent") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sales_agentPK")
			}

			column(name: "admin_user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "api_token", type: "varchar(255)")

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "sales_charge", type: "integer")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-75") {
		createTable(tableName: "sales_department_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sales_departmPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-76") {
		createTable(tableName: "seller") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sellerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-77") {
		createTable(tableName: "shipping_address") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shipping_addrPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "between_streets", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "county", type: "varchar(50)")

			column(name: "external_number", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "internal_number", type: "varchar(25)")

			column(name: "location", type: "varchar(50)")

			column(name: "phone", type: "varchar(15)")

			column(name: "state", type: "varchar(30)")

			column(name: "street", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(5)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(30)") {
				constraints(nullable: "false")
			}

			column(name: "indications", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "main", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "zip_code_info_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-78") {
		createTable(tableName: "shipping_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "shipping_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "between_streets", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "county", type: "varchar(50)")

			column(name: "external_number", type: "varchar(25)") {
				constraints(nullable: "false")
			}

			column(name: "internal_number", type: "varchar(25)")

			column(name: "location", type: "varchar(50)")

			column(name: "phone", type: "varchar(15)")

			column(name: "state", type: "varchar(30)")

			column(name: "street", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(5)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(30)") {
				constraints(nullable: "false")
			}

			column(name: "indications", type: "varchar(150)")

			column(name: "last_name", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "zip_code_info_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-79") {
		createTable(tableName: "sku") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "skuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "ean", type: "varchar(255)")

			column(name: "expected_sold", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "height", type: "decimal(19,2)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "length", type: "decimal(19,2)")

			column(name: "priority", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "quantity_on_hand", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "quantity_reserved", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_extra", type: "longtext")

			column(name: "venue_id", type: "bigint")

			column(name: "virtual_cost", type: "decimal(19,2)")

			column(name: "weight", type: "decimal(19,2)")

			column(name: "width", type: "decimal(19,2)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-80") {
		createTable(tableName: "sku_balance") {
			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "balance", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-81") {
		createTable(tableName: "sku_income") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_incomePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "available", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "history_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "income_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "odc_detail_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-82") {
		createTable(tableName: "sku_income_extra") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_income_exPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "available", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "history_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "income_extra_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "income_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-83") {
		createTable(tableName: "sku_income_extra_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_income_exPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-84") {
		createTable(tableName: "sku_outcome") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_outcomePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "consecutive", type: "SMALLINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "guide_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "order_detail_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "outcome_request_id", type: "bigint")

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "wms_status_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-85") {
		createTable(tableName: "sku_outcome_extra") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_outcome_ePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "guide_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "odc_detail_id", type: "bigint")

			column(name: "outcome_request_extra_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total_cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "unit_cost", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "wms_status_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-86") {
		createTable(tableName: "sku_outcome_extra_in_out_sku_history") {
			column(name: "sku_outcome_extra_id", type: "bigint")

			column(name: "in_out_sku_history_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-87") {
		createTable(tableName: "sku_outcome_extra_wms_error") {
			column(name: "sku_outcome_extra_wms_errors_id", type: "bigint")

			column(name: "wms_error_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-88") {
		createTable(tableName: "sku_outcome_in_out_sku_history") {
			column(name: "sku_outcome_id", type: "bigint")

			column(name: "in_out_sku_history_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-89") {
		createTable(tableName: "sku_outcome_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_outcome_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-90") {
		createTable(tableName: "sku_outcome_warehouse") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_outcome_wPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "quantity", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "receiver", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-91") {
		createTable(tableName: "sku_outcome_warehouse_in_out_sku_history") {
			column(name: "sku_outcome_warehouse_histories_id", type: "bigint")

			column(name: "in_out_sku_history_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-92") {
		createTable(tableName: "sku_outcome_wms_error") {
			column(name: "sku_outcome_wms_errors_id", type: "bigint")

			column(name: "wms_error_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-93") {
		createTable(tableName: "sku_profile") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sku_profilePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "full_price", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "item_group_profile_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "price", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "quantity_on_hand", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "quantity_reserved", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sku_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "1", name: "status_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-94") {
		createTable(tableName: "social_provider") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "social_providPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "logo", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "providerId", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-95") {
		createTable(tableName: "sub_category_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sub_category_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-96") {
		createTable(tableName: "subscription") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "subscriptionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-97") {
		createTable(tableName: "tags_item_group") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tags_item_groPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "item_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "wb_tags", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-98") {
		createTable(tableName: "telephone_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "telephone_typPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-99") {
		createTable(tableName: "tracking") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "trackingPK")
			}

			column(name: "campaign", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "code", type: "varchar(255)")

			column(name: "content", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)")

			column(name: "medium", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "source", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "step_id", type: "bigint")

			column(name: "term", type: "varchar(255)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-100") {
		createTable(tableName: "tracking_step") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tracking_stepPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-101") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "api_token", type: "varchar(128)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "facebook_token", type: "varchar(128)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "oauth_token", type: "varchar(255)")

			column(name: "password", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "referred_by", type: "varchar(255)")

			column(name: "referrer_code", type: "varchar(20)") {
				constraints(nullable: "false")
			}

			column(name: "salt", type: "varchar(64)") {
				constraints(nullable: "false")
			}

			column(name: "secret_token", type: "varchar(255)")

			column(name: "vertical_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-102") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-103") {
		createTable(tableName: "UserConnection") {
			column(name: "userId", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "providerId", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "providerUserId", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "accessToken", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "displayName", type: "varchar(255)")

			column(name: "expireTime", type: "bigint")

			column(name: "imageUrl", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "profileUrl", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "rank", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "refreshToken", type: "varchar(255)")

			column(name: "secret", type: "varchar(255)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-104") {
		createTable(tableName: "venue") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "venuePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "varchar(75)") {
				constraints(nullable: "false")
			}

			column(name: "external_number", type: "varchar(50)")

			column(name: "geo_coords", type: "varchar(25)")

			column(name: "internal_number", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "location", type: "varchar(75)")

			column(name: "name", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "provider_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "street", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(10)")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-105") {
		createTable(tableName: "vertical") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "verticalPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "base_url", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "logo", type: "varchar(255)")

			column(name: "max_per_vertical", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-106") {
		createTable(tableName: "vertical_marketing_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "vertical_markPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-107") {
		createTable(tableName: "waiting_list_item") {
			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sku_profile_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "order_detail_id", type: "bigint")

			column(name: "status_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-108") {
		createTable(tableName: "waiting_list_item_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "waiting_list_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(100)")

			column(name: "name", type: "varchar(25)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-109") {
		createTable(tableName: "warehouse") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehousePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "between_streets", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "country", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "county", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "external_number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "indications", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "postal_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "street", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-110") {
		createTable(tableName: "warehouse_sku_income") {
			column(name: "warehouse_sku_incomes_id", type: "bigint")

			column(name: "sku_income_id", type: "bigint")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-111") {
		createTable(tableName: "warehouse_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehouse_typPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-112") {
		createTable(tableName: "wish_list_item") {
			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "brand_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "deleted", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-113") {
		createTable(tableName: "wms_error") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "wms_errorPK")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-114") {
		createTable(tableName: "wms_status") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "wms_statusPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-115") {
		createTable(tableName: "zip_code_info") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "zip_code_infoPK")
			}

			column(name: "city", type: "varchar(255)")

			column(name: "city_code", type: "varchar(255)")

			column(name: "county", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "county_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "location", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "location_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "location_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "location_type_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "postal_office_zip_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "state", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "state_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "zone", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-116") {
		addPrimaryKey(columnNames: "userId, providerId, providerUserId", constraintName: "UserConnectioPK", tableName: "UserConnection")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-117") {
		addPrimaryKey(columnNames: "admin_role_id, admin_requestmap_id", constraintName: "admin_role_adPK", tableName: "admin_role_admin_requestmap")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-118") {
		addPrimaryKey(columnNames: "admin_role_id, admin_user_id", constraintName: "admin_user_adPK", tableName: "admin_user_admin_role")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-119") {
		addPrimaryKey(columnNames: "api_role_id, api_user_id", constraintName: "api_user_api_PK", tableName: "api_user_api_role")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-120") {
		addPrimaryKey(columnNames: "carrier_status_id, sku_outcome_status_id", tableName: "carrier_status_sku_outcome_status")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-121") {
		addPrimaryKey(columnNames: "order_payment_id, bin_id", tableName: "order_payment_bins")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-122") {
		addPrimaryKey(columnNames: "profile_id, subscription_id", tableName: "profile_subscriptions")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-123") {
		addPrimaryKey(columnNames: "order_payment_id, order_detail_id", constraintName: "refund_detailPK", tableName: "refund_detail")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-124") {
		addPrimaryKey(columnNames: "sku_id, warehouse_id", constraintName: "sku_balancePK", tableName: "sku_balance")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-125") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-126") {
		addPrimaryKey(columnNames: "user_id, sku_profile_id", constraintName: "waiting_list_PK", tableName: "waiting_list_item")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-127") {
		addPrimaryKey(columnNames: "user_id, brand_id", constraintName: "wish_list_itePK", tableName: "wish_list_item")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-296") {
		createIndex(indexName: "url_uniq_1396151817247", tableName: "admin_requestmap", unique: "true") {
			column(name: "url")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-297") {
		createIndex(indexName: "authority_uniq_1396151817250", tableName: "admin_role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-298") {
		createIndex(indexName: "FK51135B1612FC1669", tableName: "admin_role_admin_requestmap") {
			column(name: "admin_role_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-299") {
		createIndex(indexName: "FK51135B1615F72FC9", tableName: "admin_role_admin_requestmap") {
			column(name: "admin_requestmap_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-300") {
		createIndex(indexName: "FK29045ABBA6B3362C", tableName: "admin_user") {
			column(name: "department_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-301") {
		createIndex(indexName: "username_uniq_1396151817256", tableName: "admin_user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-302") {
		createIndex(indexName: "FK4944776A12FC1669", tableName: "admin_user_admin_role") {
			column(name: "admin_role_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-303") {
		createIndex(indexName: "FK4944776AB826DA49", tableName: "admin_user_admin_role") {
			column(name: "admin_user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-304") {
		createIndex(indexName: "authority_uniq_1396151817257", tableName: "api_role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-305") {
		createIndex(indexName: "FK39AA6AD098AFE749", tableName: "api_user") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-306") {
		createIndex(indexName: "email_uniq_1396151817258", tableName: "api_user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-307") {
		createIndex(indexName: "FK2926F00A229A3F46", tableName: "api_user_api_role") {
			column(name: "api_role_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-308") {
		createIndex(indexName: "FK2926F00AC7C50326", tableName: "api_user_api_role") {
			column(name: "api_user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-309") {
		createIndex(indexName: "FKC7AA9C470EF930", tableName: "attribute") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-310") {
		createIndex(indexName: "FKC7AA9C9442AA14", tableName: "attribute") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-311") {
		createIndex(indexName: "name_uniq_1396151817261", tableName: "attribute_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-312") {
		createIndex(indexName: "name_uniq_1396151817262", tableName: "bank", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-313") {
		createIndex(indexName: "FK93353B2A5F52FE00", tableName: "bank_account") {
			column(name: "bank_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-314") {
		createIndex(indexName: "FK93353B2AC9F23EFC", tableName: "bank_account") {
			column(name: "provider_fiscal_data_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-315") {
		createIndex(indexName: "name_uniq_1396151817264", tableName: "billing_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-316") {
		createIndex(indexName: "FK17D077E7AE463", tableName: "bin") {
			column(name: "issuer_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-317") {
		createIndex(indexName: "FK17D07881C9060", tableName: "bin") {
			column(name: "credit_card_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-318") {
		createIndex(indexName: "FK17DC0648E4EF9", tableName: "bom") {
			column(name: "income_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-319") {
		createIndex(indexName: "FK17DC06CC92E0", tableName: "bom") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-320") {
		createIndex(indexName: "FK17DC09442AA14", tableName: "bom") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-321") {
		createIndex(indexName: "FK59A4B8798AFE749", tableName: "brand") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-322") {
		createIndex(indexName: "FK7F43C1EC6F4B0AA9", tableName: "card_subscription") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-323") {
		createIndex(indexName: "FK7F43C1EC7720A9D4", tableName: "card_subscription") {
			column(name: "bin_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-324") {
		createIndex(indexName: "FKD300A719A23E8B6E", tableName: "carrier_status") {
			column(name: "carrier_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-325") {
		createIndex(indexName: "FKBC8BA7C759DD0EBF", tableName: "carrier_status_sku_outcome_status") {
			column(name: "carrier_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-326") {
		createIndex(indexName: "FKBC8BA7C77B4016CC", tableName: "carrier_status_sku_outcome_status") {
			column(name: "sku_outcome_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-327") {
		createIndex(indexName: "FK2E7B206F4B0AA9", tableName: "cart") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-328") {
		createIndex(indexName: "FK8EA0D650AA7A42E5", tableName: "cart_detail") {
			column(name: "sku_profile_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-329") {
		createIndex(indexName: "FK8EA0D650BAEC7C32", tableName: "cart_detail") {
			column(name: "cart_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-330") {
		createIndex(indexName: "name_uniq_1396151817281", tableName: "category_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-331") {
		createIndex(indexName: "code_uniq_1396151817282", tableName: "configuration", unique: "true") {
			column(name: "code")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-332") {
		createIndex(indexName: "name_uniq_1396151817284", tableName: "credit_card_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-333") {
		createIndex(indexName: "name_uniq_1396151817285", tableName: "department", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-334") {
		createIndex(indexName: "FK5E23AFC59DD0EBF", tableName: "guide") {
			column(name: "carrier_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-335") {
		createIndex(indexName: "FK5E23AFCA23E8B6E", tableName: "guide") {
			column(name: "carrier_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-336") {
		createIndex(indexName: "FK304922C759DD0EBF", tableName: "guide_carrier_history_status") {
			column(name: "carrier_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-337") {
		createIndex(indexName: "FK304922C77FFA79EE", tableName: "guide_carrier_history_status") {
			column(name: "guide_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-338") {
		createIndex(indexName: "name_uniq_1396151817287", tableName: "hsbc_payment_control", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-339") {
		createIndex(indexName: "FK5FAA95B7F52D28B", tableName: "image") {
			column(name: "image_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-340") {
		createIndex(indexName: "FK5FAA95B98AFE749", tableName: "image") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-341") {
		createIndex(indexName: "FK5FAA95BF1903AA0", tableName: "image") {
			column(name: "item_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-342") {
		createIndex(indexName: "name_uniq_1396151817289", tableName: "image_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-343") {
		createIndex(indexName: "FKA144DCE79442AA14", tableName: "in_out_sku_history") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-344") {
		createIndex(indexName: "FKA144DCE7EAF3AFCE", tableName: "in_out_sku_history") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-345") {
		createIndex(indexName: "FK317B137B43BAB3", tableName: "item") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-346") {
		createIndex(indexName: "FK317B13C0BC17AD", tableName: "item") {
			column(name: "item_group_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-347") {
		createIndex(indexName: "FK317B13E8CFEE8D", tableName: "item") {
			column(name: "attribute_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-348") {
		createIndex(indexName: "FK8AFCFA53105AA3D4", tableName: "item_group") {
			column(name: "item_group_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-349") {
		createIndex(indexName: "FK8AFCFA532BE0571D", tableName: "item_group") {
			column(name: "category_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-350") {
		createIndex(indexName: "FK8AFCFA5341679A0", tableName: "item_group") {
			column(name: "seller_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-351") {
		createIndex(indexName: "FK8AFCFA53641655B8", tableName: "item_group") {
			column(name: "sales_department_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-352") {
		createIndex(indexName: "FK8AFCFA53648E4EF9", tableName: "item_group") {
			column(name: "income_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-353") {
		createIndex(indexName: "FK8AFCFA536CC92E0", tableName: "item_group") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-354") {
		createIndex(indexName: "FK8AFCFA537B43BAB3", tableName: "item_group") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-355") {
		createIndex(indexName: "FK8AFCFA538818AEC7", tableName: "item_group") {
			column(name: "item_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-356") {
		createIndex(indexName: "FK8AFCFA539117E902", tableName: "item_group") {
			column(name: "delivery_date_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-357") {
		createIndex(indexName: "FK8AFCFA5398AFE749", tableName: "item_group") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-358") {
		createIndex(indexName: "FK8AFCFA539F6C4C42", tableName: "item_group") {
			column(name: "vertical_marketing_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-359") {
		createIndex(indexName: "FK8AFCFA53B7C5D850", tableName: "item_group") {
			column(name: "sub_category_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-360") {
		createIndex(indexName: "FK8AFCFA53F85BA354", tableName: "item_group") {
			column(name: "brand_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-361") {
		createIndex(indexName: "FK2FB127BD7B43BAB3", tableName: "item_group_profile") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-362") {
		createIndex(indexName: "FK2FB127BD98AFE749", tableName: "item_group_profile") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-363") {
		createIndex(indexName: "FK2FB127BDC0BC17AD", tableName: "item_group_profile") {
			column(name: "item_group_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-364") {
		createIndex(indexName: "name_uniq_1396151817301", tableName: "item_group_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-365") {
		createIndex(indexName: "name_uniq_1396151817302", tableName: "item_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-366") {
		createIndex(indexName: "name_uniq_1396151817304", tableName: "item_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-367") {
		createIndex(indexName: "FK1AD2E3FBFC989", tableName: "odc") {
			column(name: "wms_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-368") {
		createIndex(indexName: "FK1AD2E4DA4968E", tableName: "odc") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-369") {
		createIndex(indexName: "FK1AD2E648E4EF9", tableName: "odc") {
			column(name: "income_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-370") {
		createIndex(indexName: "FK1AD2E67DFBE65", tableName: "odc") {
			column(name: "payment_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-371") {
		createIndex(indexName: "FK1AD2E6CC92E0", tableName: "odc") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-372") {
		createIndex(indexName: "FK1AD2EBE5B667C", tableName: "odc") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-373") {
		createIndex(indexName: "FK1AD2EF8C552FC", tableName: "odc") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-374") {
		createIndex(indexName: "FK5481D88277BEAD2D", tableName: "odc_detail") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-375") {
		createIndex(indexName: "FK5481D8827A0ED1AE", tableName: "odc_detail") {
			column(name: "odc_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-376") {
		createIndex(indexName: "FK5481D8829442AA14", tableName: "odc_detail") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-377") {
		createIndex(indexName: "FKAFFC77F57A0ED1AE", tableName: "odc_payment") {
			column(name: "odc_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-378") {
		createIndex(indexName: "FK39E40DD521FA179E", tableName: "odc_wms_error") {
			column(name: "odc_wms_errors_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-379") {
		createIndex(indexName: "FK39E40DD57982C1CB", tableName: "odc_wms_error") {
			column(name: "wms_error_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-380") {
		createIndex(indexName: "FK23AE5A62AA7A42E5", tableName: "order_detail") {
			column(name: "sku_profile_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-381") {
		createIndex(indexName: "FK23AE5A62AC8E768", tableName: "order_detail") {
			column(name: "refunded_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-382") {
		createIndex(indexName: "FK23AE5A62EA21321", tableName: "order_detail") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-383") {
		createIndex(indexName: "FK23AE5A62EC7CF782", tableName: "order_detail") {
			column(name: "order_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-384") {
		createIndex(indexName: "name_uniq_1396151817312", tableName: "order_detail_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-385") {
		createIndex(indexName: "FKC6603215BFC639EA", tableName: "order_payment") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-386") {
		createIndex(indexName: "FKC6603215D538B1C1", tableName: "order_payment") {
			column(name: "payment_method_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-387") {
		createIndex(indexName: "FKC6603215EC7CF782", tableName: "order_payment") {
			column(name: "order_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-388") {
		createIndex(indexName: "FKB12DB0367720A9D4", tableName: "order_payment_bins") {
			column(name: "bin_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-389") {
		createIndex(indexName: "FKB12DB036A31700D5", tableName: "order_payment_bins") {
			column(name: "order_payment_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-390") {
		createIndex(indexName: "name_uniq_1396151817314", tableName: "order_payment_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-391") {
		createIndex(indexName: "name_uniq_1396151817314", tableName: "order_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-392") {
		createIndex(indexName: "FKC3DF62E56F4B0AA9", tableName: "orders") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-393") {
		createIndex(indexName: "FKC3DF62E598AFE749", tableName: "orders") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-394") {
		createIndex(indexName: "FKC3DF62E5BFD93FF0", tableName: "orders") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-395") {
		createIndex(indexName: "FK12EA7CE73FBFC989", tableName: "outcome_request_parent") {
			column(name: "wms_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-396") {
		createIndex(indexName: "FK12EA7CE74559D183", tableName: "outcome_request_parent") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-397") {
		createIndex(indexName: "FK12EA7CE7679555E5", tableName: "outcome_request_parent") {
			column(name: "shipping_order_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-398") {
		createIndex(indexName: "FK12EA7CE7799E94A0", tableName: "outcome_request_parent") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-399") {
		createIndex(indexName: "FK12EA7CE7B05ED9FF", tableName: "outcome_request_parent") {
			column(name: "approver_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-400") {
		createIndex(indexName: "FK509B4A4E5D02F168", tableName: "outcome_request_parent_wms_error") {
			column(name: "outcome_request_parent_wms_errors_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-401") {
		createIndex(indexName: "FK509B4A4E7982C1CB", tableName: "outcome_request_parent_wms_error") {
			column(name: "wms_error_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-402") {
		createIndex(indexName: "FKF73DC08AC0BC17AD", tableName: "payment_method_exclusions") {
			column(name: "item_group_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-403") {
		createIndex(indexName: "FKF73DC08AD538B1C1", tableName: "payment_method_exclusions") {
			column(name: "payment_method_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-404") {
		createIndex(indexName: "FKED8E89A967AE104E", tableName: "profile") {
			column(name: "zip_code_info_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-405") {
		createIndex(indexName: "FKED8E89A96F4B0AA9", tableName: "profile") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-406") {
		createIndex(indexName: "user_id_uniq_1396151817322", tableName: "profile", unique: "true") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-407") {
		createIndex(indexName: "FK22868AE07C2F269", tableName: "profile_subscriptions") {
			column(name: "subscription_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-408") {
		createIndex(indexName: "FK22868AE08F8C8DEB", tableName: "profile_subscriptions") {
			column(name: "profile_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-409") {
		createIndex(indexName: "FKC52405F198AFE749", tableName: "provider") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-410") {
		createIndex(indexName: "FK11024FD26CC92E0", tableName: "provider_contact") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-411") {
		createIndex(indexName: "FK11024FD2A9208B52", tableName: "provider_contact") {
			column(name: "secondary_telephone_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-412") {
		createIndex(indexName: "FK11024FD2A9FE7857", tableName: "provider_contact") {
			column(name: "main_telephone_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-413") {
		createIndex(indexName: "FK11024FD2FD352316", tableName: "provider_contact") {
			column(name: "provider_contact_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-414") {
		createIndex(indexName: "name_uniq_1396151817326", tableName: "provider_contact_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-415") {
		createIndex(indexName: "FK378C583D67977E4B", tableName: "provider_fiscal_data") {
			column(name: "billing_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-416") {
		createIndex(indexName: "FK378C583D6CC92E0", tableName: "provider_fiscal_data") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-417") {
		createIndex(indexName: "FKE131C04A5F83A7DF", tableName: "redeem_service_without_coupon") {
			column(name: "order_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-418") {
		createIndex(indexName: "FKD3565EF811E6CC8A", tableName: "refund_detail") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-419") {
		createIndex(indexName: "FKD3565EF85F83A7DF", tableName: "refund_detail") {
			column(name: "order_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-420") {
		createIndex(indexName: "FKD3565EF8A31700D5", tableName: "refund_detail") {
			column(name: "order_payment_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-421") {
		createIndex(indexName: "name_uniq_1396151817330", tableName: "refund_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-422") {
		createIndex(indexName: "authority_uniq_1396151817330", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-423") {
		createIndex(indexName: "FK6655F612B826DA49", tableName: "sales_agent") {
			column(name: "admin_user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-424") {
		createIndex(indexName: "name_uniq_1396151817331", tableName: "sales_department_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-425") {
		createIndex(indexName: "FKC9FF4F7F98AFE749", tableName: "seller") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-426") {
		createIndex(indexName: "FK29B8926367AE104E", tableName: "shipping_address") {
			column(name: "zip_code_info_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-427") {
		createIndex(indexName: "FK29B892636F4B0AA9", tableName: "shipping_address") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-428") {
		createIndex(indexName: "FKEBCB4D7D67AE104E", tableName: "shipping_order") {
			column(name: "zip_code_info_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-429") {
		createIndex(indexName: "FKEBCB4D7DEC7CF782", tableName: "shipping_order") {
			column(name: "order_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-430") {
		createIndex(indexName: "order_id_uniq_1396151817336", tableName: "shipping_order", unique: "true") {
			column(name: "order_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-431") {
		createIndex(indexName: "FK1BD1D7B43BAB3", tableName: "sku") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-432") {
		createIndex(indexName: "FK1BD1D7DE85A54", tableName: "sku") {
			column(name: "venue_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-433") {
		createIndex(indexName: "FK1BD1DF1903AA0", tableName: "sku") {
			column(name: "item_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-434") {
		createIndex(indexName: "FK390D65BA4DA4968E", tableName: "sku_balance") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-435") {
		createIndex(indexName: "FK390D65BA9442AA14", tableName: "sku_balance") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-436") {
		createIndex(indexName: "FK8A5B570B648E4EF9", tableName: "sku_income") {
			column(name: "income_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-437") {
		createIndex(indexName: "FK8A5B570B8C1208CB", tableName: "sku_income") {
			column(name: "odc_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-438") {
		createIndex(indexName: "FK8A5B570B90055ADA", tableName: "sku_income") {
			column(name: "history_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-439") {
		createIndex(indexName: "FK460699DC4DA4968E", tableName: "sku_income_extra") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-440") {
		createIndex(indexName: "FK460699DC648E4EF9", tableName: "sku_income_extra") {
			column(name: "income_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-441") {
		createIndex(indexName: "FK460699DC90055ADA", tableName: "sku_income_extra") {
			column(name: "history_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-442") {
		createIndex(indexName: "FK460699DC9442AA14", tableName: "sku_income_extra") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-443") {
		createIndex(indexName: "FK460699DCED4766AB", tableName: "sku_income_extra") {
			column(name: "income_extra_type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-444") {
		createIndex(indexName: "FKB50FE303FBFC989", tableName: "sku_outcome") {
			column(name: "wms_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-445") {
		createIndex(indexName: "FKB50FE304DA4968E", tableName: "sku_outcome") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-446") {
		createIndex(indexName: "FKB50FE305F83A7DF", tableName: "sku_outcome") {
			column(name: "order_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-447") {
		createIndex(indexName: "FKB50FE307FFA79EE", tableName: "sku_outcome") {
			column(name: "guide_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-448") {
		createIndex(indexName: "FKB50FE308A461B81", tableName: "sku_outcome") {
			column(name: "outcome_request_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-449") {
		createIndex(indexName: "FKB50FE30D8721A1B", tableName: "sku_outcome") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-450") {
		createIndex(indexName: "FK512991413FBFC989", tableName: "sku_outcome_extra") {
			column(name: "wms_status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-451") {
		createIndex(indexName: "FK512991414DA4968E", tableName: "sku_outcome_extra") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-452") {
		createIndex(indexName: "FK512991415E128E8E", tableName: "sku_outcome_extra") {
			column(name: "outcome_request_extra_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-453") {
		createIndex(indexName: "FK512991417FFA79EE", tableName: "sku_outcome_extra") {
			column(name: "guide_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-454") {
		createIndex(indexName: "FK512991418C1208CB", tableName: "sku_outcome_extra") {
			column(name: "odc_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-455") {
		createIndex(indexName: "FK512991419442AA14", tableName: "sku_outcome_extra") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-456") {
		createIndex(indexName: "FK51299141D8721A1B", tableName: "sku_outcome_extra") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-457") {
		createIndex(indexName: "FKD23960A528631DA7", tableName: "sku_outcome_extra_in_out_sku_history") {
			column(name: "in_out_sku_history_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-458") {
		createIndex(indexName: "FKD23960A5CCE6A328", tableName: "sku_outcome_extra_in_out_sku_history") {
			column(name: "sku_outcome_extra_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-459") {
		createIndex(indexName: "FK427836281A7D70DE", tableName: "sku_outcome_extra_wms_error") {
			column(name: "sku_outcome_extra_wms_errors_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-460") {
		createIndex(indexName: "FK427836287982C1CB", tableName: "sku_outcome_extra_wms_error") {
			column(name: "wms_error_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-461") {
		createIndex(indexName: "FK739975628631DA7", tableName: "sku_outcome_in_out_sku_history") {
			column(name: "in_out_sku_history_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-462") {
		createIndex(indexName: "FK7399756534F13CB", tableName: "sku_outcome_in_out_sku_history") {
			column(name: "sku_outcome_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-463") {
		createIndex(indexName: "FK2BBD37744DA4968E", tableName: "sku_outcome_warehouse") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-464") {
		createIndex(indexName: "FK2BBD3774799E94A0", tableName: "sku_outcome_warehouse") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-465") {
		createIndex(indexName: "FK2BBD37749442AA14", tableName: "sku_outcome_warehouse") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-466") {
		createIndex(indexName: "FKE705AF9228631DA7", tableName: "sku_outcome_warehouse_in_out_sku_history") {
			column(name: "in_out_sku_history_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-467") {
		createIndex(indexName: "FKE705AF92FABB80B5", tableName: "sku_outcome_warehouse_in_out_sku_history") {
			column(name: "sku_outcome_warehouse_histories_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-468") {
		createIndex(indexName: "FK34CB84577982C1CB", tableName: "sku_outcome_wms_error") {
			column(name: "wms_error_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-469") {
		createIndex(indexName: "FK34CB84578D7AC77F", tableName: "sku_outcome_wms_error") {
			column(name: "sku_outcome_wms_errors_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-470") {
		createIndex(indexName: "FK3AD380874384DA80", tableName: "sku_profile") {
			column(name: "item_group_profile_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-471") {
		createIndex(indexName: "FK3AD380877B43BAB3", tableName: "sku_profile") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-472") {
		createIndex(indexName: "FK3AD380879442AA14", tableName: "sku_profile") {
			column(name: "sku_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-473") {
		createIndex(indexName: "FK3AD3808798AFE749", tableName: "sku_profile") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-474") {
		createIndex(indexName: "name_uniq_1396151817349", tableName: "sub_category_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-475") {
		createIndex(indexName: "FK1456591D98AFE749", tableName: "subscription") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-476") {
		createIndex(indexName: "FK83D5C239C0BC17AD", tableName: "tags_item_group") {
			column(name: "item_group_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-477") {
		createIndex(indexName: "name_uniq_1396151817351", tableName: "telephone_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-478") {
		createIndex(indexName: "FK4BBA1EB74788FDB7", tableName: "tracking") {
			column(name: "step_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-479") {
		createIndex(indexName: "name_uniq_1396151817352", tableName: "tracking_step", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-480") {
		createIndex(indexName: "FK36EBCB98AFE749", tableName: "user") {
			column(name: "vertical_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-481") {
		createIndex(indexName: "email_uniq_1396151817353", tableName: "user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-482") {
		createIndex(indexName: "FK143BF46A6F4B0AA9", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-483") {
		createIndex(indexName: "FK143BF46ACA2046C9", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-484") {
		createIndex(indexName: "FK6AE6A6F6CC92E0", tableName: "venue") {
			column(name: "provider_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-485") {
		createIndex(indexName: "base_url_uniq_1396151817356", tableName: "vertical", unique: "true") {
			column(name: "base_url")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-486") {
		createIndex(indexName: "name_uniq_1396151817356", tableName: "vertical", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-487") {
		createIndex(indexName: "name_uniq_1396151817357", tableName: "vertical_marketing_type", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-488") {
		createIndex(indexName: "FK93160AA25F83A7DF", tableName: "waiting_list_item") {
			column(name: "order_detail_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-489") {
		createIndex(indexName: "FK93160AA26F4B0AA9", tableName: "waiting_list_item") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-490") {
		createIndex(indexName: "FK93160AA2AA7A42E5", tableName: "waiting_list_item") {
			column(name: "sku_profile_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-491") {
		createIndex(indexName: "FK93160AA2D84FFF69", tableName: "waiting_list_item") {
			column(name: "status_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-492") {
		createIndex(indexName: "name_uniq_1396151817358", tableName: "waiting_list_item_status", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-493") {
		createIndex(indexName: "FK88EF3AC3178ACA91", tableName: "warehouse") {
			column(name: "type_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-494") {
		createIndex(indexName: "FKC2B32A077D99A1EF", tableName: "warehouse_sku_income") {
			column(name: "sku_income_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-495") {
		createIndex(indexName: "FKC2B32A0797E28165", tableName: "warehouse_sku_income") {
			column(name: "warehouse_sku_incomes_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-496") {
		createIndex(indexName: "FK21D27C7C6F4B0AA9", tableName: "wish_list_item") {
			column(name: "user_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-497") {
		createIndex(indexName: "FK21D27C7CF85BA354", tableName: "wish_list_item") {
			column(name: "brand_id")
		}
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-128") {
		addForeignKeyConstraint(baseColumnNames: "admin_requestmap_id", baseTableName: "admin_role_admin_requestmap", constraintName: "FK51135B1615F72FC9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_requestmap", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-129") {
		addForeignKeyConstraint(baseColumnNames: "admin_role_id", baseTableName: "admin_role_admin_requestmap", constraintName: "FK51135B1612FC1669", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-130") {
		addForeignKeyConstraint(baseColumnNames: "department_id", baseTableName: "admin_user", constraintName: "FK29045ABBA6B3362C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "department", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-131") {
		addForeignKeyConstraint(baseColumnNames: "admin_role_id", baseTableName: "admin_user_admin_role", constraintName: "FK4944776A12FC1669", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-132") {
		addForeignKeyConstraint(baseColumnNames: "admin_user_id", baseTableName: "admin_user_admin_role", constraintName: "FK4944776AB826DA49", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-133") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "api_user", constraintName: "FK39AA6AD098AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-134") {
		addForeignKeyConstraint(baseColumnNames: "api_role_id", baseTableName: "api_user_api_role", constraintName: "FK2926F00A229A3F46", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "api_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-135") {
		addForeignKeyConstraint(baseColumnNames: "api_user_id", baseTableName: "api_user_api_role", constraintName: "FK2926F00AC7C50326", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "api_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-136") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "attribute", constraintName: "FKC7AA9C9442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-137") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "attribute", constraintName: "FKC7AA9C470EF930", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "attribute_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-138") {
		addForeignKeyConstraint(baseColumnNames: "bank_id", baseTableName: "bank_account", constraintName: "FK93353B2A5F52FE00", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bank", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-139") {
		addForeignKeyConstraint(baseColumnNames: "provider_fiscal_data_id", baseTableName: "bank_account", constraintName: "FK93353B2AC9F23EFC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider_fiscal_data", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-140") {
		addForeignKeyConstraint(baseColumnNames: "credit_card_type_id", baseTableName: "bin", constraintName: "FK17D07881C9060", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "credit_card_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-141") {
		addForeignKeyConstraint(baseColumnNames: "issuer_id", baseTableName: "bin", constraintName: "FK17D077E7AE463", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bank", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-142") {
		addForeignKeyConstraint(baseColumnNames: "income_type_id", baseTableName: "bom", constraintName: "FK17DC0648E4EF9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "income_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-143") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "bom", constraintName: "FK17DC06CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-144") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "bom", constraintName: "FK17DC09442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-145") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "brand", constraintName: "FK59A4B8798AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-146") {
		addForeignKeyConstraint(baseColumnNames: "bin_id", baseTableName: "card_subscription", constraintName: "FK7F43C1EC7720A9D4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bin", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-147") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "card_subscription", constraintName: "FK7F43C1EC6F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-148") {
		addForeignKeyConstraint(baseColumnNames: "carrier_id", baseTableName: "carrier_status", constraintName: "FKD300A719A23E8B6E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "carrier", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-149") {
		addForeignKeyConstraint(baseColumnNames: "carrier_status_id", baseTableName: "carrier_status_sku_outcome_status", constraintName: "FKBC8BA7C759DD0EBF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "carrier_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-150") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_status_id", baseTableName: "carrier_status_sku_outcome_status", constraintName: "FKBC8BA7C77B4016CC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-151") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "cart", constraintName: "FK2E7B206F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-152") {
		addForeignKeyConstraint(baseColumnNames: "cart_id", baseTableName: "cart_detail", constraintName: "FK8EA0D650BAEC7C32", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cart", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-153") {
		addForeignKeyConstraint(baseColumnNames: "sku_profile_id", baseTableName: "cart_detail", constraintName: "FK8EA0D650AA7A42E5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-154") {
		addForeignKeyConstraint(baseColumnNames: "carrier_id", baseTableName: "guide", constraintName: "FK5E23AFCA23E8B6E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "carrier", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-155") {
		addForeignKeyConstraint(baseColumnNames: "carrier_status_id", baseTableName: "guide", constraintName: "FK5E23AFC59DD0EBF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "carrier_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-156") {
		addForeignKeyConstraint(baseColumnNames: "carrier_status_id", baseTableName: "guide_carrier_history_status", constraintName: "FK304922C759DD0EBF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "carrier_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-157") {
		addForeignKeyConstraint(baseColumnNames: "guide_id", baseTableName: "guide_carrier_history_status", constraintName: "FK304922C77FFA79EE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "guide", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-158") {
		addForeignKeyConstraint(baseColumnNames: "image_type_id", baseTableName: "image", constraintName: "FK5FAA95B7F52D28B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "image_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-159") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "image", constraintName: "FK5FAA95BF1903AA0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-160") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "image", constraintName: "FK5FAA95B98AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-161") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "in_out_sku_history", constraintName: "FKA144DCE79442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-162") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "in_out_sku_history", constraintName: "FKA144DCE7EAF3AFCE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-163") {
		addForeignKeyConstraint(baseColumnNames: "attribute_type_id", baseTableName: "item", constraintName: "FK317B13E8CFEE8D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "attribute_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-164") {
		addForeignKeyConstraint(baseColumnNames: "item_group_id", baseTableName: "item", constraintName: "FK317B13C0BC17AD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-165") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "item", constraintName: "FK317B137B43BAB3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-166") {
		addForeignKeyConstraint(baseColumnNames: "brand_id", baseTableName: "item_group", constraintName: "FK8AFCFA53F85BA354", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "brand", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-167") {
		addForeignKeyConstraint(baseColumnNames: "category_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA532BE0571D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "category_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-168") {
		addForeignKeyConstraint(baseColumnNames: "delivery_date_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA539117E902", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "delivery_date_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-169") {
		addForeignKeyConstraint(baseColumnNames: "income_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA53648E4EF9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "income_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-170") {
		addForeignKeyConstraint(baseColumnNames: "item_group_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA53105AA3D4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-171") {
		addForeignKeyConstraint(baseColumnNames: "item_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA538818AEC7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-172") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "item_group", constraintName: "FK8AFCFA536CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-173") {
		addForeignKeyConstraint(baseColumnNames: "sales_department_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA53641655B8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sales_department_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-174") {
		addForeignKeyConstraint(baseColumnNames: "seller_id", baseTableName: "item_group", constraintName: "FK8AFCFA5341679A0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "seller", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-175") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "item_group", constraintName: "FK8AFCFA537B43BAB3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-176") {
		addForeignKeyConstraint(baseColumnNames: "sub_category_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA53B7C5D850", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sub_category_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-177") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "item_group", constraintName: "FK8AFCFA5398AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-178") {
		addForeignKeyConstraint(baseColumnNames: "vertical_marketing_type_id", baseTableName: "item_group", constraintName: "FK8AFCFA539F6C4C42", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical_marketing_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-179") {
		addForeignKeyConstraint(baseColumnNames: "item_group_id", baseTableName: "item_group_profile", constraintName: "FK2FB127BDC0BC17AD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-180") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "item_group_profile", constraintName: "FK2FB127BD7B43BAB3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-181") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "item_group_profile", constraintName: "FK2FB127BD98AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-182") {
		addForeignKeyConstraint(baseColumnNames: "income_type_id", baseTableName: "odc", constraintName: "FK1AD2E648E4EF9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "income_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-183") {
		addForeignKeyConstraint(baseColumnNames: "payment_status_id", baseTableName: "odc", constraintName: "FK1AD2E67DFBE65", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_payment_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-184") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "odc", constraintName: "FK1AD2E6CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-185") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "odc", constraintName: "FK1AD2EF8C552FC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-186") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "odc", constraintName: "FK1AD2EBE5B667C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-187") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "odc", constraintName: "FK1AD2E4DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-188") {
		addForeignKeyConstraint(baseColumnNames: "wms_status_id", baseTableName: "odc", constraintName: "FK1AD2E3FBFC989", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-189") {
		addForeignKeyConstraint(baseColumnNames: "odc_id", baseTableName: "odc_detail", constraintName: "FK5481D8827A0ED1AE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-190") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "odc_detail", constraintName: "FK5481D8829442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-191") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "odc_detail", constraintName: "FK5481D88277BEAD2D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_detail_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-192") {
		addForeignKeyConstraint(baseColumnNames: "odc_id", baseTableName: "odc_payment", constraintName: "FKAFFC77F57A0ED1AE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-193") {
		addForeignKeyConstraint(baseColumnNames: "odc_wms_errors_id", baseTableName: "odc_wms_error", constraintName: "FK39E40DD521FA179E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-194") {
		addForeignKeyConstraint(baseColumnNames: "wms_error_id", baseTableName: "odc_wms_error", constraintName: "FK39E40DD57982C1CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_error", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-195") {
		addForeignKeyConstraint(baseColumnNames: "order_id", baseTableName: "order_detail", constraintName: "FK23AE5A62EC7CF782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-196") {
		addForeignKeyConstraint(baseColumnNames: "refunded_detail_id", baseTableName: "order_detail", constraintName: "FK23AE5A62AC8E768", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-197") {
		addForeignKeyConstraint(baseColumnNames: "sku_profile_id", baseTableName: "order_detail", constraintName: "FK23AE5A62AA7A42E5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-198") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "order_detail", constraintName: "FK23AE5A62EA21321", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-199") {
		addForeignKeyConstraint(baseColumnNames: "order_id", baseTableName: "order_payment", constraintName: "FKC6603215EC7CF782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-200") {
		addForeignKeyConstraint(baseColumnNames: "payment_method_id", baseTableName: "order_payment", constraintName: "FKC6603215D538B1C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "payment_method", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-201") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "order_payment", constraintName: "FKC6603215BFC639EA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_payment_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-202") {
		addForeignKeyConstraint(baseColumnNames: "bin_id", baseTableName: "order_payment_bins", constraintName: "FKB12DB0367720A9D4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bin", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-203") {
		addForeignKeyConstraint(baseColumnNames: "order_payment_id", baseTableName: "order_payment_bins", constraintName: "FKB12DB036A31700D5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_payment", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-204") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "orders", constraintName: "FKC3DF62E5BFD93FF0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-205") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "orders", constraintName: "FKC3DF62E56F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-206") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "orders", constraintName: "FKC3DF62E598AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-207") {
		addForeignKeyConstraint(baseColumnNames: "approver_id", baseTableName: "outcome_request_parent", constraintName: "FK12EA7CE7B05ED9FF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-208") {
		addForeignKeyConstraint(baseColumnNames: "shipping_order_id", baseTableName: "outcome_request_parent", constraintName: "FK12EA7CE7679555E5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "shipping_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-209") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "outcome_request_parent", constraintName: "FK12EA7CE74559D183", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_request_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-210") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "outcome_request_parent", constraintName: "FK12EA7CE7799E94A0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-211") {
		addForeignKeyConstraint(baseColumnNames: "wms_status_id", baseTableName: "outcome_request_parent", constraintName: "FK12EA7CE73FBFC989", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-212") {
		addForeignKeyConstraint(baseColumnNames: "outcome_request_parent_wms_errors_id", baseTableName: "outcome_request_parent_wms_error", constraintName: "FK509B4A4E5D02F168", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_request_parent", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-213") {
		addForeignKeyConstraint(baseColumnNames: "wms_error_id", baseTableName: "outcome_request_parent_wms_error", constraintName: "FK509B4A4E7982C1CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_error", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-214") {
		addForeignKeyConstraint(baseColumnNames: "item_group_id", baseTableName: "payment_method_exclusions", constraintName: "FKF73DC08AC0BC17AD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-215") {
		addForeignKeyConstraint(baseColumnNames: "payment_method_id", baseTableName: "payment_method_exclusions", constraintName: "FKF73DC08AD538B1C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "payment_method", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-216") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "profile", constraintName: "FKED8E89A96F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-217") {
		addForeignKeyConstraint(baseColumnNames: "zip_code_info_id", baseTableName: "profile", constraintName: "FKED8E89A967AE104E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "zip_code_info", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-218") {
		addForeignKeyConstraint(baseColumnNames: "profile_id", baseTableName: "profile_subscriptions", constraintName: "FK22868AE08F8C8DEB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-219") {
		addForeignKeyConstraint(baseColumnNames: "subscription_id", baseTableName: "profile_subscriptions", constraintName: "FK22868AE07C2F269", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "subscription", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-220") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "provider", constraintName: "FKC52405F198AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-221") {
		addForeignKeyConstraint(baseColumnNames: "main_telephone_type_id", baseTableName: "provider_contact", constraintName: "FK11024FD2A9FE7857", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "telephone_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-222") {
		addForeignKeyConstraint(baseColumnNames: "provider_contact_type_id", baseTableName: "provider_contact", constraintName: "FK11024FD2FD352316", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider_contact_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-223") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "provider_contact", constraintName: "FK11024FD26CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-224") {
		addForeignKeyConstraint(baseColumnNames: "secondary_telephone_type_id", baseTableName: "provider_contact", constraintName: "FK11024FD2A9208B52", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "telephone_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-225") {
		addForeignKeyConstraint(baseColumnNames: "billing_type_id", baseTableName: "provider_fiscal_data", constraintName: "FK378C583D67977E4B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "billing_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-226") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "provider_fiscal_data", constraintName: "FK378C583D6CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-227") {
		addForeignKeyConstraint(baseColumnNames: "order_detail_id", baseTableName: "redeem_service_without_coupon", constraintName: "FKE131C04A5F83A7DF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-228") {
		addForeignKeyConstraint(baseColumnNames: "order_detail_id", baseTableName: "refund_detail", constraintName: "FKD3565EF85F83A7DF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-229") {
		addForeignKeyConstraint(baseColumnNames: "order_payment_id", baseTableName: "refund_detail", constraintName: "FKD3565EF8A31700D5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_payment", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-230") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "refund_detail", constraintName: "FKD3565EF811E6CC8A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "refund_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-231") {
		addForeignKeyConstraint(baseColumnNames: "admin_user_id", baseTableName: "sales_agent", constraintName: "FK6655F612B826DA49", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "admin_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-232") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "seller", constraintName: "FKC9FF4F7F98AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-233") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "shipping_address", constraintName: "FK29B892636F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-234") {
		addForeignKeyConstraint(baseColumnNames: "zip_code_info_id", baseTableName: "shipping_address", constraintName: "FK29B8926367AE104E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "zip_code_info", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-235") {
		addForeignKeyConstraint(baseColumnNames: "order_id", baseTableName: "shipping_order", constraintName: "FKEBCB4D7DEC7CF782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-236") {
		addForeignKeyConstraint(baseColumnNames: "zip_code_info_id", baseTableName: "shipping_order", constraintName: "FKEBCB4D7D67AE104E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "zip_code_info", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-237") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "sku", constraintName: "FK1BD1DF1903AA0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-238") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "sku", constraintName: "FK1BD1D7B43BAB3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-239") {
		addForeignKeyConstraint(baseColumnNames: "venue_id", baseTableName: "sku", constraintName: "FK1BD1D7DE85A54", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "venue", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-240") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "sku_balance", constraintName: "FK390D65BA9442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-241") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sku_balance", constraintName: "FK390D65BA4DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-242") {
		addForeignKeyConstraint(baseColumnNames: "history_id", baseTableName: "sku_income", constraintName: "FK8A5B570B90055ADA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-243") {
		addForeignKeyConstraint(baseColumnNames: "income_type_id", baseTableName: "sku_income", constraintName: "FK8A5B570B648E4EF9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "income_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-244") {
		addForeignKeyConstraint(baseColumnNames: "odc_detail_id", baseTableName: "sku_income", constraintName: "FK8A5B570B8C1208CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-245") {
		addForeignKeyConstraint(baseColumnNames: "history_id", baseTableName: "sku_income_extra", constraintName: "FK460699DC90055ADA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-246") {
		addForeignKeyConstraint(baseColumnNames: "income_extra_type_id", baseTableName: "sku_income_extra", constraintName: "FK460699DCED4766AB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_income_extra_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-247") {
		addForeignKeyConstraint(baseColumnNames: "income_type_id", baseTableName: "sku_income_extra", constraintName: "FK460699DC648E4EF9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "income_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-248") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "sku_income_extra", constraintName: "FK460699DC9442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-249") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sku_income_extra", constraintName: "FK460699DC4DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-250") {
		addForeignKeyConstraint(baseColumnNames: "guide_id", baseTableName: "sku_outcome", constraintName: "FKB50FE307FFA79EE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "guide", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-251") {
		addForeignKeyConstraint(baseColumnNames: "order_detail_id", baseTableName: "sku_outcome", constraintName: "FKB50FE305F83A7DF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-252") {
		addForeignKeyConstraint(baseColumnNames: "outcome_request_id", baseTableName: "sku_outcome", constraintName: "FKB50FE308A461B81", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_request_parent", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-253") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "sku_outcome", constraintName: "FKB50FE30D8721A1B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-254") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sku_outcome", constraintName: "FKB50FE304DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-255") {
		addForeignKeyConstraint(baseColumnNames: "wms_status_id", baseTableName: "sku_outcome", constraintName: "FKB50FE303FBFC989", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-256") {
		addForeignKeyConstraint(baseColumnNames: "guide_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991417FFA79EE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "guide", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-257") {
		addForeignKeyConstraint(baseColumnNames: "odc_detail_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991418C1208CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "odc_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-258") {
		addForeignKeyConstraint(baseColumnNames: "outcome_request_extra_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991415E128E8E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_request_parent", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-259") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991419442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-260") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "sku_outcome_extra", constraintName: "FK51299141D8721A1B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-261") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991414DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-262") {
		addForeignKeyConstraint(baseColumnNames: "wms_status_id", baseTableName: "sku_outcome_extra", constraintName: "FK512991413FBFC989", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-263") {
		addForeignKeyConstraint(baseColumnNames: "in_out_sku_history_id", baseTableName: "sku_outcome_extra_in_out_sku_history", constraintName: "FKD23960A528631DA7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-264") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_extra_id", baseTableName: "sku_outcome_extra_in_out_sku_history", constraintName: "FKD23960A5CCE6A328", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_extra", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-265") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_extra_wms_errors_id", baseTableName: "sku_outcome_extra_wms_error", constraintName: "FK427836281A7D70DE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_extra", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-266") {
		addForeignKeyConstraint(baseColumnNames: "wms_error_id", baseTableName: "sku_outcome_extra_wms_error", constraintName: "FK427836287982C1CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_error", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-267") {
		addForeignKeyConstraint(baseColumnNames: "in_out_sku_history_id", baseTableName: "sku_outcome_in_out_sku_history", constraintName: "FK739975628631DA7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-268") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_id", baseTableName: "sku_outcome_in_out_sku_history", constraintName: "FK7399756534F13CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-269") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "sku_outcome_warehouse", constraintName: "FK2BBD37749442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-270") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "sku_outcome_warehouse", constraintName: "FK2BBD3774799E94A0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "outcome_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-271") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sku_outcome_warehouse", constraintName: "FK2BBD37744DA4968E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-272") {
		addForeignKeyConstraint(baseColumnNames: "in_out_sku_history_id", baseTableName: "sku_outcome_warehouse_in_out_sku_history", constraintName: "FKE705AF9228631DA7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "in_out_sku_history", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-273") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_warehouse_histories_id", baseTableName: "sku_outcome_warehouse_in_out_sku_history", constraintName: "FKE705AF92FABB80B5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome_warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-274") {
		addForeignKeyConstraint(baseColumnNames: "sku_outcome_wms_errors_id", baseTableName: "sku_outcome_wms_error", constraintName: "FK34CB84578D7AC77F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_outcome", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-275") {
		addForeignKeyConstraint(baseColumnNames: "wms_error_id", baseTableName: "sku_outcome_wms_error", constraintName: "FK34CB84577982C1CB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "wms_error", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-276") {
		addForeignKeyConstraint(baseColumnNames: "item_group_profile_id", baseTableName: "sku_profile", constraintName: "FK3AD380874384DA80", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group_profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-277") {
		addForeignKeyConstraint(baseColumnNames: "sku_id", baseTableName: "sku_profile", constraintName: "FK3AD380879442AA14", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-278") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "sku_profile", constraintName: "FK3AD380877B43BAB3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-279") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "sku_profile", constraintName: "FK3AD3808798AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-280") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "subscription", constraintName: "FK1456591D98AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-281") {
		addForeignKeyConstraint(baseColumnNames: "item_group_id", baseTableName: "tags_item_group", constraintName: "FK83D5C239C0BC17AD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-282") {
		addForeignKeyConstraint(baseColumnNames: "step_id", baseTableName: "tracking", constraintName: "FK4BBA1EB74788FDB7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tracking_step", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-283") {
		addForeignKeyConstraint(baseColumnNames: "vertical_id", baseTableName: "user", constraintName: "FK36EBCB98AFE749", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "vertical", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-284") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46ACA2046C9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-285") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46A6F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-286") {
		addForeignKeyConstraint(baseColumnNames: "provider_id", baseTableName: "venue", constraintName: "FK6AE6A6F6CC92E0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "provider", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-287") {
		addForeignKeyConstraint(baseColumnNames: "order_detail_id", baseTableName: "waiting_list_item", constraintName: "FK93160AA25F83A7DF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_detail", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-288") {
		addForeignKeyConstraint(baseColumnNames: "sku_profile_id", baseTableName: "waiting_list_item", constraintName: "FK93160AA2AA7A42E5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-289") {
		addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "waiting_list_item", constraintName: "FK93160AA2D84FFF69", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "waiting_list_item_status", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-290") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "waiting_list_item", constraintName: "FK93160AA26F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-291") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "warehouse", constraintName: "FK88EF3AC3178ACA91", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-292") {
		addForeignKeyConstraint(baseColumnNames: "sku_income_id", baseTableName: "warehouse_sku_income", constraintName: "FKC2B32A077D99A1EF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sku_income", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-293") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_sku_incomes_id", baseTableName: "warehouse_sku_income", constraintName: "FKC2B32A0797E28165", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-294") {
		addForeignKeyConstraint(baseColumnNames: "brand_id", baseTableName: "wish_list_item", constraintName: "FK21D27C7CF85BA354", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "brand", referencesUniqueColumn: "false")
	}

	changeSet(author: "winbits (generated)", id: "1396151817471-295") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "wish_list_item", constraintName: "FK21D27C7C6F4B0AA9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	include file: 'changelogs/43-vertical-domain-validation.groovy'
	include file: 'changelogs/7-new-shipping-address.groovy'
	include file: 'changelogs/113-add-last-updated-card-subscription.groovy'
	include file: 'changelogs/137-last-login.groovy'
	include file: 'changelogs/160-move-fields-to-user.groovy'
	include file: 'changelogs/95-change-register-facebook.groovy'

	include file: 'changelogs/000-no-venues.groovy'

	include file: 'changelogs/changelog-attribute.groovy'

	include file: 'changelogs/136-tracking.groovy'

  include file: 'changelogs/affiliation-api_multiple-logins.groovy'
  include file: 'changelogs/create-order-detail-campaign-table.groovy'
  include file: 'changelogs/create-cart-detail-campaign-table.groovy'

	include file: 'changelogs/admin-refund-details.groovy'
  include file: 'changelogs/odc_history_table.groovy'
  include file: 'changelogs/create-user-bits-trouble-table.groovy'
  include file: 'changelogs/starbucks.groovy'
  include file: 'changelogs/starbucks-cart.groovy'
  include file: 'changelogs/user-mobile.groovy'
  include file: 'changelogs/provider-fiscal-data.groovy'
  include file: 'changelogs/odc-add-open-close-user.groovy'
  include file: 'changelogs/add-2-order-cashback-trans.groovy'
  include file: 'changelogs/add-2-order-detail-cashback-trans.groovy'

  include file: 'changelogs/tried-status-migration-status.groovy'
}
