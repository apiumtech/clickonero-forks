package com.winbits.api.clients.migration

interface MigrationClient {

  Map obtainPersonByEmail (String email)

  Map getShippingAddressByEmail (String email)

  Map obtainUserInfo (String email)
}
