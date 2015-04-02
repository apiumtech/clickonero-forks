package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/12/13
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
class WaitingListInStockException extends ApiClientException {
  String code = 'AFER012'
}
