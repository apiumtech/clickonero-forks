package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import org.joda.time.DateTime

class Image {

  Item item
  String url
  ImageType imageType
  Vertical vertical
  Integer ordinal

  DateTime dateCreated
  DateTime lastUpdated

  String ref
  static transients = ['ref']


  static constraints = {
    ordinal nullable: true, min: 0
  }
}
