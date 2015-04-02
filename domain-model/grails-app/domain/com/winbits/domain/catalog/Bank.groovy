package com.winbits.domain.catalog

class Bank {

  String name
  String logo
  Boolean installment

  static hasMany = [bins:Bin]
    static belongsTo = Bin

  static constraints = {
    name   maxSize: 50, unique: true
    logo url: true, nullable: true
    installment nullable: true, defaultValue: false
  }
}
