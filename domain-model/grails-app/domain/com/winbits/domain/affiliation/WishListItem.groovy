package com.winbits.domain.affiliation

import com.winbits.domain.catalog.Brand

class WishListItem implements Serializable {

  User user
  Brand brand
  Date dateCreated
  Boolean deleted = false


  static constraints = {
  }

  static mapping = {
    id(composite: ['user', 'brand'])
    version(false)
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

}
