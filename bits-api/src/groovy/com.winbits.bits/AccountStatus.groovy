package com.winbits.bits

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 4/9/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public enum AccountStatus {
    ACTIVE(1), DISABLED(2)
    final int id

    AccountStatus(int id) {
        this.id = id
    }

}