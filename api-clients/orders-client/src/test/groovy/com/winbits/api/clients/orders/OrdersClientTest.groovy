package com.winbits.api.clients.orders

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class OrdersClientTest{
  //TODO CREATE APITOKEN IN REDIS TO MAKE PAST THE TEST

  @Autowired
  private OrdersClient ordersClient

  @Ignore
  @Test
  void shouldFailsWithoutApiToken(){
    def response = ordersClient.showForAdmin(1,'')
    assert response.response == [:]
    assert response.meta.status == 401
  }
  @Ignore
  @Test
  void shouldNotFindApiToken(){
    def response = ordersClient.showForAdmin(1,'tokenDoesntExist')
    assert response.meta.status == 401
  }
  @Ignore
  @Test
  void shouldFindApiTokenAndResponseNotCardSubscription(){
    def response = ordersClient.showForAdmin(1,'4p1t0k3n')
    assert response.response == []
    assert response.meta.status == 200
  }
  @Ignore
  @Test
  void shouldFindApiTokenButNotValidUser(){
    def response = ordersClient.showForAdmin(10000000000,'4p1t0k3n')
    assert response.response == [:]
    assert response.meta.status == 428
  }
  @Ignore
  @Test
  void shouldFindApiTokenAndResposeCardSubscriptions(){
    def response = ordersClient.showForAdmin(5,'4p1t0k3n')
    assert !response.response.isEmpty()
    assert response.meta.status == 200
  }

  /*-----------------------nomicka client test--------------------------*/
  @Ignore
  @Test
    void shouldGetAnOrderFromNomickaEndPoint(){

      List<Map> cartDetails = [["skuProfileId": 63878, "quantity": 1]]
      10.times{
          def response = ordersClient.cartItemsFromNomicka(cartDetails,'sRR92s3qIOqp36kVrhoUHKE8tyOstIcJz4IRt5dUCiYgtNpO53zT9XSxmpW2Rtb2')
          assert response.meta.status == 200
      }


  }
  @Ignore
  @Test
   void shouldFailsWithoutApiTokenForNomicka(){
     def response = ordersClient.cartItemsFromNomicka([],'')
     assert response.response == [:]
     assert response.meta.status == 401
    }

      @Ignore
    @Test
   void shouldPayFailWithoutApiTokenForNomicka(){
        def response = ordersClient.paymentOrder([:],'')
        assert response.response == [:]
        assert response.meta.status == 401
    }
    @Ignore
    @Test
    void shouldPayForNomicka(){
        Map request = ['paymentMethod': 21, 'vertical': 9, 'order':121016 , 'shippingAddress': 735 , 'paymentInfo':[:]]  //el paymentMethod  varia segun el ds
        println request
        def response = ordersClient.paymentOrder(request,'MbubTki0ga3AOxLplCiIbmultRjH8iHUKDdAtq3zpYm1OWfdojaZZC1dsD5ASAVM')    //todo traer el apitoken desde affiliacion
//        assert response.response
        assert response.meta.status == 200
    }

}
