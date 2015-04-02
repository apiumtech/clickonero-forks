package com.winbits.domain.editors

import com.winbits.domain.catalog.PaymentMethod

import java.beans.PropertyEditorSupport

class PaymentMethodPropertyEditor extends PropertyEditorSupport {

  void setAsText(String text) {
    def v = text.isNumber() ? PaymentMethod.get(text) : PaymentMethod.findByIdentifier(text)
    super.setValue(v)
  }

  void setValue(Object value) {
    def v = value instanceof String ? PaymentMethod.findByIdentifier(value) : PaymentMethod.get(value)
    super.setValue(v)
  }
}
