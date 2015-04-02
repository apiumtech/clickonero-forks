package com.winbits.api.affiliation.config.social

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.support.ConnectionFactoryRegistry
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.social.facebook.connect.FacebookConnectionFactory
import org.springframework.social.twitter.api.Twitter
import org.springframework.social.twitter.api.impl.TwitterTemplate
import org.springframework.social.twitter.connect.TwitterConnectionFactory

import javax.sql.DataSource

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/25/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
class SocialConfig {

  @Autowired
  GrailsApplication grailsApplication

  @Bean
  public ConnectionFactoryLocator connectionFactoryLocator() {

    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry()

    registry.addConnectionFactory(new FacebookConnectionFactory(
        grailsApplication.config.social.facebook.clientId,
        grailsApplication.config.social.facebook.clientSecret) )

    registry.addConnectionFactory(new TwitterConnectionFactory(
        grailsApplication.config.social.twitter.consumerKey,
        grailsApplication.config.social.twitter.consumerSecret
    ))
    //Add more providers here

    registry
  }

  @Bean
  @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
  public ConnectionRepository connectionRepository() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
      if (authentication == null) {
        throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in")
      }
      usersConnectionRepository().createConnectionRepository( authentication.getName() )
  }

  @Bean
  UsersConnectionRepository usersConnectionRepository() {
    new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(),
        textEncryptor)
  }

  @Bean
  @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
  public Facebook facebook() {
    Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class)
    return facebook != null ? facebook.getApi() : new FacebookTemplate()
  }

  @Bean
  @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
  public Twitter twitter() {
    Connection<Twitter> twitter = connectionRepository().findPrimaryConnection(Twitter.class)
    return twitter != null ? twitter.getApi() : new TwitterTemplate()
  }

  @Autowired
  DataSource dataSource

  @Autowired
  TextEncryptor textEncryptor

}
