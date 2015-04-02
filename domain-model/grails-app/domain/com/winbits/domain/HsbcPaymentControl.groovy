package com.winbits.domain

class HsbcPaymentControl {
    String name

    static constraints = {
        name blank:false, size: 5..250, unique: true 
    }
}
