import org.apache.log4j.DailyRollingFileAppender

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]
grails.config.defaults.locations = [com.winbits.domain.config.DomainModelConfig, com.winbits.rabbitmq.DefaultConfig]

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
grails.spring.bean.packages = ['com.winbits.api.affiliation.config.social']
// whether to disable processing of multi part requests
grails.web.disable.multipart = false
// User hyphenated URLs
grails.web.url.converter = 'hyphenated'

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
  }
  qa {
    grails.logging.jul.usebridge = false
  }
}

def logDirectory = null
def defaultAppenderThreshold = org.apache.log4j.Level.DEBUG
environments {
  qa {
    logDirectory = 'logs/'
    defaultAppenderThreshold = org.apache.log4j.Level.INFO
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
      appender new DailyRollingFileAppender(name: 'file', file: logDirectory + 'affiliation-api.log',
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
      'com.winbits',
      'com.winbits.grest.test.spock'
}

//Parametros para redireccion de mail
grails.resetPassowd.urlBase = "http://www.winbits.com"

winbits.default.locale = new Locale("es", "MX")
winbits.cookies.guest.name = 'wb_guest_token'
winbits.cookies.api.name = 'wb_api_token'

api.winbits.baseUrl = 'https://apidev.winbits.com/v1/users'
api.clients.bits.baseUrl = 'https://apidev.winbits.com/v1'
api.clients.bits.contextSuffix = ''
api.winbits.login.redirectUrl = 'http://localhost/widgets/login.html'
api.winbits.logout.redirectUrl = 'http://localhost/widgets/logout.html'
api.clients.coupon.baseUrl = 'http://localhost:5000'
api.clients.service.baseUrl = 'http://localhost:5010'
api.clients.travel.baseUrl = 'http://localhost:5060'
api.clients.balance.baseUrl = 'http://localhost:9999'
api.clients.balance.contextSuffix = ''
api.clients.migration.baseUrl = 'http://localhost:5020'
api.clients.migration.user = 'migration@winbits.com'
api.clients.migration.password = 'w1nb1tsM1gr4t10n'
api.clients.bebitos.baseUrl = 'https://bebitos-admin.herokuapp.com'
api.clients.bebitos.token = '81f4adec925c3ee0bb041f12614bd4e4ec45210c7684e23c'

api.clients.sms.baseUrl = 'http://api.broadcaster.mx'

environments {
  development {
    grails.gorm.failOnError = true
  }
  test {
    grails.gorm.failOnError = true
  }
  ci {
    grails.resetPassowd.urlBase = "https://apidev.winbits.com"
  }
  qa {
    grails.resetPassowd.urlBase = "https://apiqa.winbits.com"
    api.winbits.baseUrl = 'https://apiqa.winbits.com/v1/users'

    api.clients.bits.baseUrl = 'https://apiqa.winbits.com/v1'
    api.clients.bits.contextSuffix = ''

    api.clients.coupon.baseUrl = 'http://10.0.0.220:5000'
    api.clients.service.baseUrl = 'http://10.0.0.220:5010'
    api.clients.travel.baseUrl = 'http://10.0.0.220:5060'
    api.clients.migration.baseUrl = 'http://10.0.0.220:6020'
    api.clients.balance.baseUrl = 'http://10.0.0.220:5020'
    api.clients.balance.contextSuffix = 'balance'
  }
  staging {
    grails.resetPassowd.urlBase = "https://apistaging.winbits.com"
    api.winbits.baseUrl = 'https://apistaging.winbits.com/v1/users'

    api.clients.bits.baseUrl = 'https://apistaging.winbits.com/v1'
    api.clients.bits.contextSuffix = ''

    api.clients.coupon.baseUrl = 'http://10.0.0.220:5001'
    api.clients.service.baseUrl = 'http://10.0.0.220:5011'
    api.clients.travel.baseUrl = 'http://10.0.0.220:5061'
    api.clients.migration.baseUrl = 'http://10.0.0.220:6030'
    api.clients.balance.baseUrl = 'http://10.0.0.220:5021'
    api.clients.balance.contextSuffix = 'balance'
  }
  production {
    grails.resetPassowd.urlBase = "https://api.winbits.com"
    api.winbits.baseUrl = 'https://api.winbits.com/v1/users'

    api.clients.bits.baseUrl = 'https://api.winbits.com/v1'
    api.clients.bits.contextSuffix = ''

    api.clients.coupon.baseUrl = 'http://172.31.34.1:5000'
    api.clients.service.baseUrl = 'http://172.31.34.1:5010'
    api.clients.travel.baseUrl = 'http://172.31.34.1:5060'
    api.clients.migration.baseUrl = 'http://172.31.34.1:6020'
    api.clients.balance.baseUrl = 'http://172.31.34.1:5020'
    api.clients.balance.contextSuffix = 'balance'
  }
}

grest.api.errors.ERR002.status = 404
grest.api.errors.AFER001.status = 400
grest.api.errors.AFER002.status = 409
grest.api.errors.AFER003.status = 410
grest.api.errors.AFER006.status = 401
grest.api.errors.AFER010.status = 404
grest.api.errors.AFER011.status = 401
grest.api.errors.AFER012.status = 409
grest.api.errors.AFER013.status = 409
grest.api.errors.AFER014.status = 404
grest.api.errors.AFER015.status = 404
grest.api.errors.AFER016.status = 401
grest.api.errors.AFER017.status = 404
grest.api.errors.AFER018.status = 400
grest.api.errors.AFER019.status = 409
grest.api.errors.AFER020.status = 409
grest.api.errors.AFER021.status = 404
grest.api.errors.AFER022.status = 409
grest.api.errors.AFER023.status = 409
grest.api.errors.AFER024.status = 409
grest.api.errors.AFER025.status = 409
grest.api.errors.AFER026.status = 400
grest.api.errors.AFER027.status = 400
grest.api.errors.AFER028.status = 422
grest.api.errors.AFER029.status = 409
grest.api.errors.AFER030.status = 404
grest.api.errors.AFER031.status = 405
grest.api.errors.AFER032.status = 404

def defaultDateFormat = 'yyyy-MM-dd'
jodatime.format.org.joda.time.LocalDate = defaultDateFormat
jodatime.format.org.joda.time.DateTime = "${defaultDateFormat}THH:mm:ss"

swagger {
  discoveryUrl = 'http://localhost/winbits/affiliation-api/swagger-doc/resources/api-docs.json'
  resourcesBaseUrl = 'http://localhost/winbits/affiliation-api/swagger-doc/resources'
  apiBaseUrl = 'http://localhost:8000/affiliation-api'
}

environments {
  qa {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/qa/affiliation/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/qa/affiliation/resources'
    swagger.apiBaseUrl = 'http://apiqa.winbits.com/v1/affiliation'
  }
  staging {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/staging/affiliation/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/staging/affiliation/resources'
    swagger.apiBaseUrl = 'http://apistaging.winbits.com/v1/affiliation'
  }
  production {
    swagger.discoveryUrl = 'http://apidoc.winbits.com/v1/prod/affiliation/resources/api-docs.json'
    swagger.resourcesBaseUrl = 'http://apidoc.winbits.com/v1/prod/affiliation/resources'
    swagger.apiBaseUrl = 'http://api.winbits.com/v1/affiliation'
  }
}

grails.serverURL = "https://api.winbits.com/v1/users"
social.facebook.clientId="468430606512928"
social.facebook.clientSecret="558ffb873f5f07cf04d0ada92a689b92"
social.twitter.consumerKey="MKxX37680ZvS4GwLzd28Lw"
social.twitter.consumerSecret="8CPwAEhqtzDsvWkgQQKV3R0LtTvnKGzTekOwk9OnzI"
social.loginUrl = "/"
social.connectedUrl = "/"
social.deniedUrl = "/"
social.disconnectUrl = "/"

environments {
  development {
    grails.serverURL = "https://apidev.winbits.com/v1/users"
    social.facebook.clientId="486640894740634"
    social.facebook.clientSecret="69e465d8526ab5a2f3c815f40f4fb06a"
    social.twitter.consumerKey="te2a3glzxwmHGkR1sidErw"
    social.twitter.consumerSecret="QfHfaZLNCiDRtaWRGOswhRqQ9XtJnmCdVQl1eGow"
    social.loginUrl = "/"
    social.connectedUrl = "/"
    social.deniedUrl = "/"
    social.disconnectUrl = "/"
  }
  test {
    grails.serverURL = "https://localhost:8000/${appName}"
    social.facebook.clientId="560088460697262"
    social.facebook.clientSecret="b6f0839f84c0e609bb71adc49669a8a4"
    social.twitter.consumerKey="te2a3glzxwmHGkR1sidErw"
    social.twitter.consumerSecret="QfHfaZLNCiDRtaWRGOswhRqQ9XtJnmCdVQl1eGow"
    social.loginUrl = "/"
    social.connectedUrl = "/"
    social.deniedUrl = "/"
    social.disconnectUrl = "/"
  }

  qa {
    grails.serverURL = "https://apiqa.winbits.com/v1/users"
    social.facebook.clientId="494663817271863"
    social.facebook.clientSecret="811d5a3e42a70cafc81081ad6dd18d93"
    social.twitter.consumerKey="MKxX37680ZvS4GwLzd28Lw"
    social.twitter.consumerSecret="8CPwAEhqtzDsvWkgQQKV3R0LtTvnKGzTekOwk9OnzI"
    social.loginUrl = "/"
    social.connectedUrl = "/"
    social.deniedUrl = "/"
    social.disconnectUrl = "/"
  }

  ci {
    grails.serverURL = "https://apici.winbits.com/v1/users"
    social.facebook.clientId="486640894740634"
    social.facebook.clientSecret="69e465d8526ab5a2f3c815f40f4fb06a"
    social.twitter.consumerKey="te2a3glzxwmHGkR1sidErw"
    social.twitter.consumerSecret="QfHfaZLNCiDRtaWRGOswhRqQ9XtJnmCdVQl1eGow"
    social.loginUrl = "/"
    social.connectedUrl = "/"
    social.deniedUrl = "/"
    social.disconnectUrl = "/"
  }

  staging {
    grails.serverURL = "https://apistaging.winbits.com/v1/users"
    social.facebook.clientId="1407560009467593"
    social.facebook.clientSecret="a0ea14abf8ae3339efbb03eabb7fe090"
    social.twitter.consumerKey="te2a3glzxwmHGkR1sidErw"
    social.twitter.consumerSecret="QfHfaZLNCiDRtaWRGOswhRqQ9XtJnmCdVQl1eGow"
    social.loginUrl = "/"
    social.connectedUrl = "/"
    social.deniedUrl = "/"
    social.disconnectUrl = "/"
  }
}

def bitsApiUsername = 'admin'
def bitsApiPassword = 'admin'

environments {
  production {
    bitsApiUsername = 'winbits'
    bitsApiPassword = '.OE0(}W}|^iFCQ#'
  }
}

winbits.api.clients.bits.auth.username = bitsApiUsername
winbits.api.clients.bits.auth.password = bitsApiPassword

grails.plugins.springsecurity.filterChain.chainMap = [
    '/**': 'JOINED_FILTERS,-exceptionTranslationFilter'
]

com.winbits.mongodb.host = 'localhost'
com.winbits.mongodb.port = 27017

environments {
  test {
    com.winbits.mongodb.host = 'localhost'
    com.winbits.mongodb.port = 27017
  }

  ci {
    com.winbits.mongodb.host = 'localhost'
    com.winbits.mongodb.port = 27017
  }
  qa {
    com.winbits.mongodb.host = 'localhost'
    com.winbits.mongodb.port = 27017
  }
  staging {
    com.winbits.mongodb.host = '54.208.45.186'
    com.winbits.mongodb.port = 27017
  }
  production {
    com.winbits.mongodb.host = '172.31.18.76'
    com.winbits.mongodb.port = 27017
  }
}

winbits.config.promo.completeRegisterCashbackAmount = 100
winbits.config.promo.completeRegisterCashback.bag = 'REGISTER-PROMO'
winbits.config.promo.completeRegisterCashback.duration = 10

winbits.config.credit.migration.bag = 'CREDIT-MIGRATION'
winbits.config.credit.migration.daysToExpireCredit = 365

winbits.config.sms.apikey = '7738f97e6812874bbd232a49482cdf5f'

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
