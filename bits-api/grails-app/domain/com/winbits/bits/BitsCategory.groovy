package com.winbits.bits

class BitsCategory {
  String code
  String description
  String value
  Bag bag

  static belongsTo = [bag: Bag]

  static constraints = {
    code unique: true, nullable: false, blank: false
    description nullable: true
    value nullable: false
    bag nullable: false
  }

}
