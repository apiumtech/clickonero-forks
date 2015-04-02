import org.apache.log4j.DailyRollingFileAppender

import javax.servlet.http.HttpServletResponse

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all: '*/*',
    atom: 'application/atom+xml',
    css: 'text/css',
    csv: 'text/csv',
    form: 'application/x-www-form-urlencoded',
    html: ['text/html', 'application/xhtml+xml'],
    js: 'text/javascript',
    json: ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss: 'application/rss+xml',
    text: 'text/plain',
    xml: ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
  development {
    grails.logging.jul.usebridge = true
  }
  production {
    grails.logging.jul.usebridge = false
    // TODO: grails.serverURL = "http://www.changeme.com"
  }
}

def logDirectory = null
def defaultAppenderThreshold = org.apache.log4j.Level.DEBUG
environments {
  qa {
    logDirectory = 'logs/'
  }
  staging {
    logDirectory = 'logs/'
    defaultAppenderThreshold = org.apache.log4j.Level.WARN
  }
  production {
    logDirectory = 'logs/'
    defaultAppenderThreshold = org.apache.log4j.Level.ERROR
  }
}

// log4j configuration
log4j = {
  // Example of changing the log pattern for the default console appender:
  //
  appenders {
    console name: 'stdout', layout: pattern(conversionPattern: '%d{HH:mm:ss} %p %c:%L %m%n'),
        threshold: defaultAppenderThreshold
    if (logDirectory) {
      appender new DailyRollingFileAppender(name: 'file', file: logDirectory + 'bits-api.log',
          datePattern: '\'_\'yyyy-MM-dd', layout: pattern(conversionPattern: '%d{HH:mm:ss} %p %c:%L %m%n'),
          threshold: org.apache.log4j.Level.INFO)
    }
  }

  root {
    error 'stdout', 'file'
  }

  error 'org.codehaus.groovy.grails.web.servlet',        // controllers
      'org.codehaus.groovy.grails.web.pages',          // GSP
      'org.codehaus.groovy.grails.web.sitemesh',       // layouts
      'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
      'org.codehaus.groovy.grails.web.mapping',        // URL mapping
      'org.codehaus.groovy.grails.commons',            // core / classloading
      'org.codehaus.groovy.grails.plugins',            // plugins
      'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
      'org.springframework',
      'org.hibernate',
      'net.sf.ehcache.hibernate'

  warn 'grails.app.services.com.winbits.domain',
      'com.winbits.domain',
      'com.winbits.grest'

  debug 'grails.app.conf',
      'grails.app.filters.com.winbits',
      'grails.app.taglib.com.winbits',
      'grails.app.services.com.winbits',
      'grails.app.controllers.com.winbits',
      'grails.app.domain.com.winbits',
      'com.winbits'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.winbits.bits.secure.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.winbits.bits.secure.UserRole'
grails.plugins.springsecurity.authority.className = 'com.winbits.bits.secure.Role'

grails.plugins.springsecurity.useBasicAuth = true
grails.plugins.springsecurity.basic.realmName = 'Winbits: Please, enter your username and password'
grails.plugins.springsecurity.apf.allowSessionCreation = false
grails.plugins.springsecurity.filterChain.chainMap = [
    '/**': 'JOINED_FILTERS'
]

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

def defaultDateFormat = 'yyyy-MM-dd'
jodatime.format.org.joda.time.LocalDate = defaultDateFormat
jodatime.format.org.joda.time.DateTime = "${defaultDateFormat}'T'HH:mm:ss"

environments {
  development {
    grails.convertes.default.pretty.print = true
  }
  test {
    grails.convertes.default.pretty.print = true
  }
}

environments {
  development {
    grails.gorm.failOnError = true
  }
  test {
    grails.gorm.failOnError = true
  }
}

grest.api.errors.BITS001.status = HttpServletResponse.SC_NOT_FOUND

swagger {
  discoveryUrl = 'http://localhost/winbits/bits-api/swagger-doc/resources/api-docs.json'
  resourcesBaseUrl = 'http://localhost/winbits/bits-api/swagger-doc/resources'
  apiBaseUrl = 'http://localhost:8001/bits-api'
}

grails.plugin.databasemigration.databaseChangeLogTableName = 'database_changelog'
grails.plugin.databasemigration.databaseChangeLogLockTableName = 'database_changelog_lock'

environments {
  qa {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/qa/bits/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/qa/bits/resources'
    swagger.apiBaseUrl = 'http://apiqa.winbits.com/v1/bits'
  }
  staging {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/staging/bits/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/staging/bits/resources'
    swagger.apiBaseUrl = 'http://apistaging.winbits.com/v1/bits'
  }
  production {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/prod/bits/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/prod/bits/resources'
    swagger.apiBaseUrl = 'http://api.winbits.com/v1/bits'
  }
  test {
    grails.plugin.databasemigration.updateOnStart = true
    grails.plugin.databasemigration.autoMigrateScripts = 'TestApp'
    //grails.plugin.databasemigration.updateOnStartFileNames = ['routines.groovy']
  }
  ci {
    grails.plugin.databasemigration.updateOnStart = true
    grails.plugin.databasemigration.autoMigrateScripts = 'TestApp'
    grails.plugin.databasemigration.updateOnStartFileNames = ['routines.groovy']
  }
}

environments {
  development {
    grails.plugins.springsecurity.active = true
  }
}

winbits.config.expired_offset = 5

def roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'

environments {
  qa {
    roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'
  }
  staging {
    roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'
  }
  production {
    roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'
  }
}

grails.plugins.springsecurity.controllerAnnotations.staticRules = [
    '/monitoring/**': [roleForSpecialURLs],
    '/build-info/**': [roleForSpecialURLs],
    '/dbconsole/**': [roleForSpecialURLs]
]

def rabbitmqUsername = 'guest'
def rabbitmqPassword = 'guest'
def rabbitmqHostname = '127.0.0.1'
def rabbitVirtualHost = '/'

def disableRabbitMQListenerProperty =  System.properties['bits.rabbitmq.listener.disable']
def disableRabbitMQListeners = ('true' == disableRabbitMQListenerProperty)

if (disableRabbitMQListeners) {
  def bannerText = 'RabbitMQ listeners disabled'
  def bannerLength = 40
  def banner = new StringBuilder()
  bannerLength.times { banner.append('#') }
  bannerText = bannerText.toUpperCase().center(bannerLength - 2)
  banner.append("\n#${bannerText}#\n")
  bannerLength.times { banner.append('#') }
  print banner.toString()
}

environments {
  qa {
    rabbitmqUsername = 'winbits'
    rabbitmqPassword = 'khop5i69y8u'
    rabbitmqHostname = '10.0.0.103'
  }
  staging {
    rabbitmqUsername = 'winbits'
    rabbitmqPassword = '6U74-rvjHmK'
    rabbitmqHostname = '10.0.0.220'
  }
  production {
    rabbitmqUsername = 'winbits-rmq'
    rabbitmqPassword = 'er65h4r65gt'
    rabbitmqHostname = '172.31.18.76'
  }
}

rabbitmq {
  connectionfactory {
    username = rabbitmqUsername
    password = rabbitmqPassword
    hostname = rabbitmqHostname
    virtualHost = rabbitVirtualHost
  }
  queues = {
    bitsTransfer autoDelete: false, durable: true, exclusive: false
    bitsTransferDeadLetter autoDelete: false, durable: true, exclusive: false
    bitsWithdraw autoDelete: false, durable: true, exclusive: false
    bitsWithdrawDeadLetter autoDelete: false, durable: true, exclusive: false
    bitsRollback autoDelete: false, durable: true, exclusive: false
    bitsRollbackDeadLetter autoDelete: false, durable: true, exclusive: false
    bitsRewardRegister autoDelete: false, durable: true, exclusive: false
    bitsRewardRegisterDeadLetter autoDelete: false, durable: true, exclusive: false
    bitsGiftRegister autoDelete: false, durable: true, exclusive: false
    bitsGiftRegisterDeadLetter autoDelete: false, durable: true, exclusive: false
    responseChangeStatusOrder autoDelete: false, durable: true, exclusive: false
    responseChangeStatusOrderDeadLetter autoDelete: false, durable: true, exclusive: false
  }
  disableListening = disableRabbitMQListeners
}
