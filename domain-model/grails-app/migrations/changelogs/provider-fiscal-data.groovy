package changelogs

databaseChangeLog = {

  changeSet(author: "winbits", id: "profisdata-01") {
    sql('ALTER TABLE `provider_fiscal_data` ' +
         'CHANGE COLUMN `name` `name` VARCHAR(250) CHARACTER SET \'utf8\' ' +
         'COLLATE \'utf8_unicode_ci\' NOT NULL ;')
  }

}
