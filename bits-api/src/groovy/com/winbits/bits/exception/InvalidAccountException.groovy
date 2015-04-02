package com.winbits.bits.exception

import com.winbits.bits.Account
import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 4/29/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
class InvalidAccountException extends ApiClientException {
  String code = 'BITS002'

  Account account

  InvalidAccountException(Account id) {
    this.account = account
    this.data = [id: account.id]
  }
}
