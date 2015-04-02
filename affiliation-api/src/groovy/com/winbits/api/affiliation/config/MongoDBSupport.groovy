package com.winbits.api.affiliation.config

import com.mongodb.DB
import com.mongodb.Mongo
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by winbits on 8/19/14.
 */
@Configuration
class MongoDBSupport {

  @Autowired
  GrailsApplication grailsApplication

  @Bean
  public Mongo mongoConnection() {
    def host = grailsApplication.config.com.winbits.mongodb.host
    def port = grailsApplication.config.com.winbits.mongodb.port
    new Mongo(host,  port)
  }

  @Bean
  public DB mongoDb() {
    mongoConnection().getDB("affiliation")
  }
}
