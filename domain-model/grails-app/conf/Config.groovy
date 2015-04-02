// configuration for plugin testing - will not be included in the plugin zip
grails.config.defaults.locations = [com.winbits.domain.config.DomainModelConfig, com.winbits.orders.config.CybersourceConfig]

log4j = {
  // Example of changing the log pattern for the default console
  // appender:
  //
  //appenders {
  //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
  //}

  error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
      'org.codehaus.groovy.grails.web.pages', //  GSP
      'org.codehaus.groovy.grails.web.sitemesh', //  layouts
      'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
      'org.codehaus.groovy.grails.web.mapping', // URL mapping
      'org.codehaus.groovy.grails.commons', // core / classloading
      'org.codehaus.groovy.grails.plugins', // plugins
      'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
      'org.springframework',
      'org.hibernate',
      'net.sf.ehcache.hibernate'

  warn 'org.mortbay.log'
}

grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.winbits.domain.affiliation.User'
grails.plugins.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = 'salt'

def springSecurityActive = false
environments {
  test {
    springSecurityActive = true
  }
  ci {
    springSecurityActive = true
  }
}
grails.plugins.springsecurity.active = springSecurityActive

// Added by the Joda-Time plugin:
grails.gorm.default.mapping = {
  "user-type" type: org.joda.time.contrib.hibernate.PersistentDateTime, class: org.joda.time.DateTime
  "user-type" type: org.joda.time.contrib.hibernate.PersistentInterval, class: org.joda.time.Interval
  "user-type" type: org.joda.time.contrib.hibernate.PersistentInstant, class: org.joda.time.Instant
  "user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDate, class: org.joda.time.LocalDate
  "user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
  "user-type" type: org.joda.time.contrib.hibernate.PersistentTimeOfDay, class: org.joda.time.TimeOfDay
  "user-type" type: org.joda.time.contrib.hibernate.PersistentYearMonthDay, class: org.joda.time.YearMonthDay
}

def defaultDateFormat = 'dd/MM/yyyy'
jodatime.format.org.joda.time.LocalDate = defaultDateFormat
jodatime.format.org.joda.time.DateTime = "${defaultDateFormat}THH:mm:ss"

grails.plugin.databasemigration.databaseChangeLogTableName = 'database_changelog'
grails.plugin.databasemigration.databaseChangeLogLockTableName = 'database_changelog_lock'

def createDefaultModel = false
environments {
  test {
    createDefaultModel = true
  }
  ci {
    createDefaultModel = true
  }
}
grails.plugin.winbits.domain.createDefaultModel = createDefaultModel
