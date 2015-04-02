package com.winbits.domain.utils

import com.winbits.domain.PersistentEnum
import grails.test.MockUtils
import org.codehaus.groovy.runtime.InvokerHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DomainModelUtils {

  private static Logger log = LoggerFactory.getLogger(DomainModelUtils)

  static List createPersistentEnumModelForTest(Class<PersistentEnum> persistentEnumClass) {
    def persistentClass = getPersistentClass(persistentEnumClass)
    MockUtils.mockDomain(persistentClass)
    createPersistentEnumModel(persistentEnumClass)
  }

  private static Class getPersistentClass(Class<PersistentEnum> persistentEnumClass) {
    def persistentEnum = persistentEnumClass.interfaces.find { it.is(PersistentEnum) }
    if (persistentEnum) {
      persistentEnumClass.genericInterfaces.find {
        it.rawType.is(PersistentEnum)
      }.actualTypeArguments.first()
    }
  }

  static List createPersistentEnumModel(Class<PersistentEnum> persistentEnumClass, Class domainClass = null) {
    def enumConstants = persistentEnumClass.enumConstants
    def persistentClass = domainClass ?: getPersistentClass(persistentEnumClass)
    enumConstants.collect { enumConstant ->
      log.debug "Persisting enum constant [id: ${enumConstant.id}, name: ${enumConstant.name()}]"
      def domain = InvokerHelper.invokeStaticMethod(persistentClass, 'findOrCreateWhere', [name: enumConstant.name()])
      if (domain.id != enumConstant.id) {
        domain.name = enumConstant.name()
        domain.save()
      }
    }
  }
}
