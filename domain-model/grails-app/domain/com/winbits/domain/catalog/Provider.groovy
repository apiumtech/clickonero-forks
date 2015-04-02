package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import org.joda.time.DateTime

class Provider {
  String name
  String url
  Vertical vertical


  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false


  static hasMany = [itemsGroup: ItemGroup, contacts: ProviderContact, fiscalDatas: ProviderFiscalData]

  static constraints = {
    url url: true, nullable: true
    name maxSize: 100
  }

  static namedQueries = {
    skus { providerId ->

      itemsGroup {
        items {
          skus {
          }
        }
      }
      eq 'id', providerId
    }
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }
}
