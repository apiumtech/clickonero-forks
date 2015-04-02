package com.winbits.api.orders.services

class BitsDomainService {
  static transactional = false

  def bitsClient

  Map refundBits(Long bitsAccountId, Long referenceOrderPayment){
    bitsClient.refundBits(bitsAccountId, referenceOrderPayment)
  }
}
