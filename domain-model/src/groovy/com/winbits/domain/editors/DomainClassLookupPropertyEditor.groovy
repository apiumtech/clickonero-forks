package com.winbits.domain.editors

import org.apache.commons.lang.StringUtils

import java.beans.PropertyEditorSupport

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 5/7/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomainClassLookupPropertyEditor extends PropertyEditorSupport {

  Class domainClass
  String property

  void setAsText(String text) {
    def v = domainClass."findBy${StringUtils.capitalize(property)}"(text)
    super.setValue(v)
  }

  void setValue(Object value) {
    def v = domainClass."findBy${StringUtils.capitalize(property)}"(value)
    super.setValue(v)
  }
}
