package com.winbits.api.clients.bebitos

import org.joda.time.LocalDate
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Test cases for BebitosClient.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class BebitosClientTests {

  private static Logger log = LoggerFactory.getLogger(BebitosClientTests)

  @Autowired
  private BebitosClient bebitosClient

  @Test
  void findUserTest() {
    def result = bebitosClient.findUserByCredentials("manuel.gomez@clients.sms.com.mx", "m4nu3lg")
    log.info("Find User result: ${result}")
    assert result
    assert result.response.statusCode == 200 
  }

  @Test
  void findUser404ErrorTest() {
    def result = bebitosClient.findUserByCredentials("manuel.gomez@clients.sms.com.mx", "qsdfdsfdsfsdwerty123")
    log.info("User not found: ${result}")
    assert result
    assert result.response.statusCode == 422 
  }

  @Test
  void userDetailTest() {
    def result = bebitosClient.userDetail("manuel.gomez@clients.sms.com.mx")
    log.info("retrieve user details: ${result}")
    assert result
    assert result.response.statusCode == 200
  }

  @Test
  void userDetail404ErrorTest() {
    def result = bebitosClient.userDetail("ewqeqwe_manuel.gomez@clients.sms.com.mx")
    log.info("details not found: ${result}")
    assert result
    assert result.response.statusCode == 422
  }
}
