package com.winbits.bits

import org.joda.time.DateTime

class RefundBitsService {

    def refundBits(Long bitsAccountId, Long referenceOrderPayment) {
            [balance: 50.0, expirationDate: DateTime.now().plusDays(7).toDateMidnight(), transactionId: 1L] 
    }
}
