package com.winbits.api.clients

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.DataBinder
import org.springframework.validation.Errors
import org.springframework.validation.Validator

/**
 * Common default JSR-303 validator implementation for API clients.
 */
@Component
class ApiClientsValidator {

  @Autowired
  Validator validator

  Errors validate(Object object, String objectName = DataBinder.DEFAULT_OBJECT_NAME) {
    def binder = new DataBinder(object, objectName)
    binder.validator = validator
    binder.validate()
    binder.bindingResult
  }

  Errors validate(Object object, String objectName = DataBinder.DEFAULT_OBJECT_NAME, String... exclusions) {
    def binder = new DataBinder(object, objectName)
    binder.disallowedFields = exclusions
    binder.validator = validator
    binder.validate()
    binder.bindingResult
  }
}
