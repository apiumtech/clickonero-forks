package com.winbits.domain.catalog

class Attribute {

  String name
  String label
  String value

  Sku sku

  AttributeType type

  String ref
  static transients = ['ref']
  static constraints = {
    name maxSize: 255
//    value maxSize: 25
    label maxSize: 255

  }
}
