package com.winbits.api.clients.migration

import org.joda.time.LocalDate
import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class MigrationDummyClientTests {

  private static Logger log = LoggerFactory.getLogger(MigrationDummyClientTests)

  @Autowired
  def migrationClient

  @Test
  void shouldDoObtainPerson (){
    def response = migrationClient.obtainPersonByEmail ('foo@foo.com')
    assert response
  
  }

  @Test
  void shouldDoObtainShippingAddress (){
    def response = migrationClient.getShippingAddressByEmail ('foo@foo.com')
    assert response
  }

  @Test
  void shouldDoObtainUserInfo (){
    def response = migrationClient.obtainUserInfo('foo@foo.com').response
    assert response
    assert response.status == 200
    assert response.shippingAddress
    assert response.clickoneroCredits

  }
}
