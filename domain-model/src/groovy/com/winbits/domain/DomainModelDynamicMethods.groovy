package com.winbits.domain

import com.winbits.api.catalog.controllers.PartialResponseCommand
import com.winbits.api.catalog.services.CatalogReadable
import com.winbits.exceptions.api.client.EntityValidationException
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsClass
import org.slf4j.LoggerFactory
import org.springframework.dao.OptimisticLockingFailureException

/**
 * Decorate Grails classes with dynamic methods.
 */
class DomainModelDynamicMethods {

  static void decorate(GrailsApplication application) {
    decorateControllerClasses(application)
    decorateServiceClasses(application)
  }

  static def decorateControllerClasses(GrailsApplication application) {
    for (controllerClass in application.controllerClasses) {
      addWithPartialResponseCommand(controllerClass)
      addWithCommand(controllerClass)
      addWithOptimisticLocking(controllerClass)
    }
  }

  private static void addWithPartialResponseCommand(GrailsClass controllerClass) {
    controllerClass.metaClass.withPartialResponseCommand = { params, Class clazz ->
      def command = bindData(new PartialResponseCommand(), params)

      def domain = bindData(clazz.newInstance(), params, [exclude: CatalogReadable.ORDER_FILTER])
      DefaultGrailsDomainClass defaultDomain = new DefaultGrailsDomainClass(clazz)
      def filters = [:]
      defaultDomain.getPersistentProperties().each {
        if (params."$it.name" && domain.hasProperty(it.name)) {
          filters."${it.name}" = domain?."${it.name}"
        }
      }
      command.filters = filters
      command
    }
  }

  private static void addWithCommand(GrailsClass controllerClass) {
    controllerClass.metaClass.withCommand = { command, Closure c ->
      if (command.metaClass.respondsTo(command, "beforeValidate")) {
        command.beforeValidate()
      }

      if (!command.validate()) {
        throw new EntityValidationException(command.errors)
      }
      c.call command
    }
  }

  private static void addWithOptimisticLocking(GrailsClass grailsClass) {
    def log = LoggerFactory.getLogger(DomainModelDynamicMethods.name + '.addWithOptimisticLocking')
    grailsClass.metaClass.withOptimisticLocking = { Long timeout = 3000L, Closure cls ->
      def startTime = new Date().time
      def duration = 0
      def attempts = 0
      while (true) {
        try {
          attempts++
          cls.call()
          break
        } catch (OptimisticLockingFailureException e) {
          duration = new Date().time - startTime
          if (duration > timeout) {
            log.warn("withOptimisticLocking timeout! attempts = ${attempts}")
            throw e
          }
        }
      }
      duration = new Date().time - startTime
      log.info("withOptimistickLocking success! attempts = ${attempts}, duration = ${duration}")
    }
  }

  static void decorateServiceClasses(GrailsApplication application) {
    for (serviceClass in application.serviceClasses) {
      addWithOptimisticLocking(serviceClass)
    }
  }
}
