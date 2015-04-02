package com.winbits.api.affiliation.exception

import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.exceptions.api.ApiClientException

class ShippingAddressDoesNotBelongsToUserException extends ApiClientException {

  final String code = 'AFER050'

  ShippingAddress shippingAddress
  User user

  ShippingAddressDoesNotBelongsToUserException(ShippingAddress shippingAddress, User user) {
    this.shippingAddress = shippingAddress
    this.user = user
  }
}
