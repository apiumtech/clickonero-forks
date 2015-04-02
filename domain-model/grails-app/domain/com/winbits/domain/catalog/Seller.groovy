package com.winbits.domain.catalog
 
import com.winbits.domain.affiliation.Vertical

class Seller {

  String name
  Vertical vertical

  static constraints = {
    name   maxSize: 50
  }
}
