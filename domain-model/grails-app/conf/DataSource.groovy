dataSource {
  pooled = true
  driverClassName = "org.h2.Driver"
  username = "sa"
  password = ""
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
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://127.0.0.1:3306/winbits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "root"
      password = ""
    }
  }
  test {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "update"
      url = "jdbc:mysql://127.0.0.1:3306/winbits_test?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "root"
      password = ""
    }
  }
  ci {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://localhost:3306/winbits_ci?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbitsdev"
      password = "n6qD6bLF"
      properties {
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1"
      }
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
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1"
      }
    }
  }
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
  production {
    dataSource {
      dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
      driverClassName = "com.mysql.jdbc.Driver"
      dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
      url = "jdbc:mysql://172.31.42.121:3306/winbits?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
      username = "winbits-api"
      password = "zNclPBWM"
      properties {
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1"
      }
    }
  }
}
