package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/12/13
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
class WaitingListAlreadyIsInListException extends ApiClientException {
  String code = 'AFER013'
}
