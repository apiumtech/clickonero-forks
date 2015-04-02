package com.winbits.api.affiliation.exception

import com.winbits.exceptions.api.ApiClientException

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/14/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
class WaitingListItemNotFoundException extends ApiClientException {
  String code = 'AFER014'
}
