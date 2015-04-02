package com.winbits.api.clients.clickonero

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class ClickoneroClientTest{

  @Autowired
  private ClickoneroClient clickoneroClient

  @Test
  void shouldResponseClickonero(){
    def response = clickoneroClient.ordersClickoneroHistory(2570990)
    assert response instanceof Map
    assert response.id == 2570990
    assert response.orders instanceof List
    def orderSkus = response.orders.first()
    assert orderSkus.skus instanceof List
  }

}
