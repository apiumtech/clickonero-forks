package com.winbits.orders.utils

import org.springframework.stereotype.Component

@Component("orderPaymentUtils")
class OrderPaymentUtils{

    String obtainIdFromTransactionReference(String transactionReference){
        def parts = transactionReference.split('--')
        parts.last()
    }
}
