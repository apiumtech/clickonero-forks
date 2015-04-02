package com.winbits.domain.orders
 
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.Bin
 
import org.joda.time.DateTime

class CardSubscription {

    String subscriptionId
    DateTime dateCreated
    DateTime lastUpdated
    boolean principal = false

    static hasOne = [ user : User, bin: Bin]

    static constraints = {
        user nullable : false
        subscriptionId nullable: false
        bin nullable : true
        principal nullable: true
    }
}
