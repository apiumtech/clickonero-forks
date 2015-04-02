package com.winbits.domain.exception

import com.winbits.exceptions.api.ApiClientException
import groovy.transform.InheritConstructors
import org.springframework.expression.ExpressionInvocationTargetException

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 5/8/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
@InheritConstructors
class ProviderNotFoundException extends ApiClientException {
  String code = 'CAER008'

  ProviderNotFoundException(long id) {
    this.data = [providerId: id] ExpressionInvocationTargetException
  }
}
