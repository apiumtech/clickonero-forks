package com.winbits.domain.editors

import com.winbits.domain.Enumable
import com.winbits.domain.PersistentEnum
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsClass
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.lang.reflect.ParameterizedType

class EnumablePropertyEditorRegistrar implements PropertyEditorRegistrar {

  @Autowired
  private GrailsApplication grailsApplication

  private Map<Class, PersistentEnum> persistentEnumByDomain

  @PostConstruct
  void init() {
    def enumableDomainClasses = grailsApplication.domainClasses.findAll { GrailsClass domainClass ->
      findEnumableClass(domainClass.clazz)
    }
    def map = enumableDomainClasses.collectEntries {
      [it.clazz, findEnumableClass(it.clazz).actualTypeArguments.first()]
    }
    persistentEnumByDomain = map.asImmutable()
  }

  @Override
  void registerCustomEditors(PropertyEditorRegistry registry) {
    persistentEnumByDomain.each { Class enumableClass, Class<PersistentEnum> persistentEnumClass ->
      registry.registerCustomEditor(enumableClass, new EnumablePropertyEditor(persistentEnumClass: persistentEnumClass))
    }
  }

  private ParameterizedType findEnumableClass(Class domainClass) {
    domainClass.genericInterfaces.find {
      it instanceof ParameterizedType && it.rawType == Enumable
    }
  }
}
