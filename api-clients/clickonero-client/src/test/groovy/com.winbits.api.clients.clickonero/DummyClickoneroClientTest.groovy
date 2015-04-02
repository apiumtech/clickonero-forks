package com.winbits.api.clients.clickonero

import com.winbits.api.clients.clickonero.ClickoneroClient
import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class DummyClickoneroClientTest{

  @Autowired
  private ClickoneroClient clickoneroClient

  @Test
  void dummyClickoneroClientTest(){
    def response = clickoneroClient.ordersClickoneroHistory(1)
    def resp = response.response
    assert resp.id ==  2570990
    assert resp.email == "test@clients.sms.com.mx"
    assert resp.firstName == "test"

    def orderProduct = resp.orders.first()
    assert orderProduct.id == 682243
    assert orderProduct.orderStatus == "PAID"
    assert orderProduct.dateCreated == 1401893182000

    def skuProduct = orderProduct.skus.first()
    assert skuProduct.itemType == "PRODUCT"
    assert skuProduct.orderDetailId ==  948816
    assert skuProduct.cost ==  399
    assert skuProduct.itemId ==  167092
    assert skuProduct.quantity ==  1
    assert skuProduct.shortDescription == "Producto"
    assert skuProduct.name == "Producto Name"
    assert skuProduct.path == "Producto Path"
    assert !skuProduct.coupons

    def orderService = resp.orders.last()
    assert orderService.id ==  664056
    assert orderService.orderStatus ==  "PAID"
    assert orderService.dateCreated == 1400687293000

    def skuService = orderService.skus.first()
    assert skuService.itemType == "SERVICE"
    assert skuService.orderDetailId ==  920197
    assert skuService.cost ==  59
    assert skuService.itemId ==  160463
    assert skuService.quantity ==  1
    assert skuService.shortDescription == "Service"
    assert skuService.name == "Service Name"
    assert skuService.path == "Service Path"
    assert skuService.coupons

  }

}
