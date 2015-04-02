package com.winbits.domain.config

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

environments {
  development {
    grails.gorm.failOnError = true
  }
  test {
    grails.gorm.failOnError = true
  }
  ci {
    grails.gorm.failOnError = true
  }
}

def defaultDateFormat = 'dd/MM/yyyy'
jodatime.format.org.joda.time.LocalDate = defaultDateFormat
jodatime.format.org.joda.time.DateTime = "${defaultDateFormat}THH:mm:ss"

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.winbits.domain.affiliation.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.winbits.domain.affiliation.UserRole'
grails.plugins.springsecurity.authority.className = 'com.winbits.domain.affiliation.Role'

grails.plugins.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = 'salt'

grails.plugins.springsecurity.useBasicAuth = true
grails.plugins.springsecurity.basic.realmName = "Winbits needs your credentials"

// Session prevention
//grails.plugins.springsecurity.scr.allowSessionCreation = false
//grails.plugins.springsecurity.requestCache.createSession = false

/*grails.plugins.springsecurity.filterChain.chainMap = [
    '/**': 'JOINED_FILTERS,-exceptionTranslationFilter'
]*/

def roleForSpecialURLs = 'ROLE_SUPER_ADMIN'

environments {
  development {
    roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'
  }
  test {
    roleForSpecialURLs = 'IS_AUTHENTICATED_ANONYMOUSLY'
  }
}

grails.plugins.springsecurity.controllerAnnotations.staticRules = [
    '/monitoring/**': [roleForSpecialURLs],
    '/build-info/**': [roleForSpecialURLs],
    '/dbconsole/**': [roleForSpecialURLs]
]

winbits.config.promo.completeRegisterCashbackAmount = 100

def redisHost = 'localhost'
def redisPoolMaxActive = 10
def redisPort = 6379
environments {
  qa {
    redisHost = '10.0.0.103'
    redisPoolMaxActive = 20
    redisPort = 22121
  }
  staging {
    redisHost = '10.0.0.103'
    redisPoolMaxActive = 100
    redisPort = 22121
  }
  production {
    redisHost = '172.31.18.76'
    redisPoolMaxActive = 100
    redisPort = 22121
  }
}

grails {
  redis {
    poolConfig {
      maxActive = redisPoolMaxActive
    }
    timeout = 2000 //default in milliseconds
    port = redisPort
    host = redisHost
  }
}

def testUrl = 'http://www.winbits-test.com'
def myLooqUrl = 'http://dev.mylooq.com'
def pandaSportsUrl = 'http://dev.pandasports.com'
def clickoneroUrl = 'http://dev.clickonero.com'
environments {
  qa {
    myLooqUrl = 'http://qa.mylooq.com'
    pandaSportsUrl = 'http://qa.pandasports.com'
    clickoneroUrl = 'http://qa.clickonero.com'
  }
  staging {
    testUrl = 'http://staging.mylooq.com'
    myLooqUrl = 'http://mylooq.dream-it.com.mx'
    pandaSportsUrl = 'http://panda.dream-it.com.mx'
    clickoneroUrl = 'http://clickonero.dream-it.com.mx'
  }
  production {
    myLooqUrl = 'http://www.mylooq.com'
    pandaSportsUrl = 'http://www.pandasports.com'
    clickoneroUrl = 'http://www.clickonero.com'
  }
}

winbits {
  verticals {
    test.url = testUrl
    mylooq.url = myLooqUrl
    pandasports.url = pandaSportsUrl
    clickonero.url = clickoneroUrl
  }
}
