package com.winbits.api.clients.migration

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
@Ignore
class MigrationClientTests {

  @Autowired
  MigrationClient migrationClient

  @Test
  void shouldGetPerson () {
    def response = migrationClient.obtainPersonByEmail ('diego.zarate@clients.sms.com.mx').response
    assert response
    assert response.status
    assert response.status == 200
    assert response.person
    def person = response.person
    assert person.email
    assert person.dateCreated
  }
  
  @Test
  void shouldGetShippingAddress () {
    def response = migrationClient.getShippingAddressByEmail ('diego.zarate@clients.sms.com.mx').response
    assert response
    assert response.status
    assert response.status == 200
  }
  
  @Test
  void shouldObtainUserInfo () {
    def response = migrationClient.obtainUserInfo ('diego.zarate@clients.sms.com.mx').response
    assert response
    assert response.status
    assert response.status == 200
    assert response.shippingAddress
    assert response.clickoneroCredits
  }
  
  @Test
  void shouldNotObtainUserInfo () {
    def response = migrationClient.obtainUserInfo ().response
    assert response
    assert response.status
    assert response.status == 200
    assert !response.shippingAddress
    assert !response.clickoneroCredits
  }
}
