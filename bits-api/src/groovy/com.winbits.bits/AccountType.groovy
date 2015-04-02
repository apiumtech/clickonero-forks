package com.winbits.bits

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 4/9/13
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public enum AccountType {
        USER(1), VERTICAL(2), PROMOTION(3)
        final int id

    AccountType(int id){
            this.id = id
        }
}