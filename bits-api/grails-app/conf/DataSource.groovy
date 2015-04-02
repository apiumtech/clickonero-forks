dataSource {
  pooled = true
  driverClassName = "org.h2.Driver"
  username = "sa"
  password = ""
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
  cache.use_second_level_cache = true
  cache.use_query_cache = false
  cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
  development {
    dataSource {
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost/bits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      username = "root"
      password = ""
    }
  }
  test {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost/bits_test?noAccessToProcedureBodies=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "root"
      password = ""
    }
  }
  ci {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost:3306/bits_ci?noAccessToProcedureBodies=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "n6qD6bLF"
    }
  }
  qa {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://10.0.0.17:3306/bits_qa?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "e0Y9Yhj^#G3h@`#"
      properties {
        maxActive = 30
        minIdle = 3
        maxWait = 10000
        initialSize = 3
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
  staging {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://72.55.168.154:3306/bits_staging?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "n6qD6bLF"
      properties {
        maxActive = 100
        minIdle = 10
        maxWait = 10000
        initialSize = 10
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
      url = "jdbc:mysql://172.31.42.121:3306/bits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbits-api"
      password = "zNclPBWM"
      properties {
        maxActive = 300
        minIdle = 10
	maxIdle = 25
        maxWait = 10000
        initialSize = 10
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
