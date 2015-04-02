package com.winbits.api.clients.orders

import com.winbits.api.clients.orders.OrdersClient
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class DummyOrdersClientTest{

  @Autowired
  private OrdersClient ordersClient

  @Test
  void dummyOrdersClientTest(){
    def response = ordersClient.showForAdmin(1,"6a5sdf798as49564s")
    def cardInfo = response.response.cardInfo
    assert cardInfo
    assert cardInfo.cardData
    assert cardInfo.cardAddress
  }

}
