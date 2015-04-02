package com.winbits.api.catalog.services

import com.winbits.api.catalog.controllers.PartialResponseCommand
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class DomainUtilService implements CatalogReadable {

  def getTotalByFields(PartialResponseCommand command, Class clazz) {
    def c = clazz.createCriteria()
    c.count
        {
          if(command.ids) {
            'in'("id", command.ids)
          }
          command.filters.each {
            eq(it.key, it.value)
          }
        }
  }

  @Override
  List<Map> getAllDomainByFields(PartialResponseCommand command, Class clazz) {
    command.fields = validFieldsInDomain(command.fields, clazz, [])
    command.sort = validFieldsInDomain(command.sort, clazz, [])
    def domain = clazz.withCriteria {
      projections {
        for (field in command.fields) {
          property(field)
        }
      }
      if (command.ids) {
        'in'("id", command.ids)
      }
      command.filters.each {
        eq(it.key, it.value)
      }
      maxResults(command.max)
      firstResult(command.offset)
      and {
        command.sort.each {
          order(it, command.order)
        }
      }
    }
    def result = collectProjectionEntries(domain, command.fields)


    result << [totalCount: getTotalByFields(command, clazz), limit: command.max, offset: command.offset]
  }

  @Override
  List validFieldsInDomain(List fields, Class clazz, List defaultFields) {
    def fixedFields = ['id'] + defaultFields ?: []
    DefaultGrailsDomainClass domain = new DefaultGrailsDomainClass(clazz)
    List res = []
    List persistenceProperties = []

    domain.getPersistentProperties().each {
      if (!it.oneToMany) {
        persistenceProperties << it.name
      }
    }
    persistenceProperties += defaultFields ?: []
    if (fields?.size()) {
      res = persistenceProperties.intersect(fields)
    } else {
      res = persistenceProperties
    }

    res.remove('deleted')
    res += fixedFields
    res.unique(true)
    res
  }


  @Override
  List<Map> collectProjectionEntries(List projections, List fields) {
    if (fields.size() == 1) {
      projections = [projections]
    }

    projections.collect { projectionsProps ->
      def index = 0
      projectionsProps.collectEntries { propValue -> [fields[index++], propValue] }
    }
  }
}
