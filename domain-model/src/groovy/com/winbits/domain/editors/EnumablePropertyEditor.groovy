package com.winbits.domain.editors

import com.winbits.domain.PersistentEnum

import java.beans.PropertyEditorSupport

class EnumablePropertyEditor extends PropertyEditorSupport {

  Class<? extends PersistentEnum> persistentEnumClass

  void setAsText(String text) {
    def v = persistentEnumClass.enumConstants.find { it.name() == text }
    super.setValue(v?.domain)
  }
}
