package com.winbits.bits

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 4/12/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AccountValidity {
    ABSOLUTE(1), RELATIVE(2)

    final int id

    AccountValidity(int id) {
        this.id = id
    }

}