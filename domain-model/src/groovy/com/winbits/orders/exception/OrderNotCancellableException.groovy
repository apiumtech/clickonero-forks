package com.winbits.orders.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Thrown when an order is not cancellable
 */
class OrderNotCancellableException extends ApiClientException {

  String code = 'ORDE004'

  Serializable id

  OrderNotCancellableException(Serializable id) {
    this.id = id
    this.data = [id: id]
  }
}
