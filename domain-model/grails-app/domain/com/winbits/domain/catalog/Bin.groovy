package com.winbits.domain.catalog
 
import com.winbits.domain.orders.CardSubscription
import com.winbits.domain.orders.OrderPayment

class Bin {
    
    String bin
    CreditCardType creditCardType
    String logo
    String name
    
    static hasOne = [issuer:Bank]
    static belongsTo = OrderPayment
    static hasMany = [cardSubscriptions : CardSubscription, orderPayments: OrderPayment]

    static constraints = {
        creditCardType nullable: true
        logo nullable: true
    }
}
