import org.grails.plugin.hibernate.filter.HibernateFilterDomainConfiguration

dataSource {
  pooled = true
  driverClassName = "org.h2.Driver"
  username = "sa"
  password = ""
  configClass = HibernateFilterDomainConfiguration
  properties {
    maxActive = 10
    minIdle = 3
    maxIdle = 5
    minEvictableIdleTimeMillis = 1800000
    timeBetweenEvictionRunsMillis = 1800000
    testOnBorrow = true
    testWhileIdle = true
    testOnReturn = false
    validationQuery = "SELECT 1"
    jdbcInterceptors = "ConnectionState;StatementFinalizer"
  }
}
hibernate {
  cache.use_second_level_cache = false
  cache.use_query_cache = false
  cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
  development {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost:3306/winbits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "root"
      password = ""
    }
  }
  test {
    dataSource {
      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
    }
  }
  ci {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost:3306/affiliation_ci?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "n6qD6bLF"
    }
  }
//  test {
//    dataSource {
//      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
//      driverClassName = "com.mysql.jdbc.Driver"
//      dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
//      url = "jdbc:mysql://127.0.0.1:3306/winbits_test?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
//      username = "root"
//      password = ""
//    }
//  }
//  ci {
//    dataSource {
//      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//      url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//    }
//  }
  qa {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://10.0.0.17:3306/winbits_qa?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "e0Y9Yhj^#G3h@`#"
    }
  }
  staging {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://72.55.168.154:3306/winbits_staging?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "n6qD6bLF"
      properties {
        maxActive = 200
        minIdle = 20
        maxWait = 10000
        initialSize = 20
        testWhileIdle = true
        testOnBorrow = true
        testOnReturn = false
        validationQuery = "SELECT 1"
        validationInterval = 30000
        timeBetweenEvictionRunsMillis = 5000
        minEvictableIdleTimeMillis = 30000
        removeAbandonedTimeout = 60
        removeAbandoned = true
        logAbandoned = false
        jdbcInterceptors = "ConnectionState;StatementFinalizer"
      }
    }
  }
  production {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://172.31.42.121:3306/winbits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbits-api"
      password = "zNclPBWM"
      properties {
        maxActive = 300
        minIdle = 10
	maxIdle = 50
        maxWait = 10000
        initialSize = 20
        testWhileIdle = true
        testOnBorrow = true
        testOnReturn = false
        validationQuery = "SELECT 1"
        validationInterval = 30000
        timeBetweenEvictionRunsMillis = 5000
        minEvictableIdleTimeMillis = 30000
        removeAbandonedTimeout = 60
        removeAbandoned = true
        logAbandoned = false
        jdbcInterceptors = "ConnectionState;StatementFinalizer"
      }
    }
  }
}
