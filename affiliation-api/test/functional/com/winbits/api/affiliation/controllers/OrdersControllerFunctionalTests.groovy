package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.Image
import com.winbits.domain.catalog.ImageType
import com.winbits.domain.catalog.ImageTypeEnum
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatus
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderStatus
import com.winbits.domain.orders.OrderStatusEnum
import functionaltestplugin.FunctionalTestCase

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 8/13/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
class OrdersControllerFunctionalTests extends FunctionalTestCase {

  def order1

  void setUp() {
    super.setUp()
    order1 = createOrderDummy(OrderStatus.get(OrderStatusEnum.PAID.id))
    createOrderDummy(OrderStatus.get(OrderStatusEnum.PAID.id))
    createOrderDummy(OrderStatus.get(OrderStatusEnum.PENDING.id))
    createOrderDummy(OrderStatus.get(OrderStatusEnum.PENDING.id))
    createOrderDummy(OrderStatus.get(OrderStatusEnum.REFUNDED.id))
  }

  private Order createOrderDummy(OrderStatus status) {
    def user = User.findByApiToken('W1nb1tsT3st')
    def skuProfile = SkuProfile.get(1)
    Order order = new Order(itemsTotal: 1, shippingTotal: 1, bitsTotal: 1, total: 1, cashTotal: 1,
        status: status, user: user, vertical: Vertical.get(1) ).save()
    def image = new Image(url: 'http://urltoimage', imageType: ImageType.get(ImageTypeEnum.THUMB.id),
        vertical: skuProfile.vertical, item: skuProfile.sku.item ).save()
    skuProfile.sku.item.addToImages( image ).save()
    def orderDetail = new OrderDetail(shippingAmount: 1, cashAmount: 1, bitsAmount: 1, amount: 1, quantity: 1,
        status: OrderDetailStatus.get( OrderDetailStatusEnum.CHECKOUT.id ),
        order: order, skuProfile: skuProfile ).save()
    order.addToOrderDetails(orderDetail)
    order.save(flush: true)
  }


  void testShowOrdersErrorIfUserIsInvalid() {
    get('/orders'){
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
    }
    assertStatus 401
    def json = grails.converters.JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER011'
    assert json.meta.message == 'Usuario inválido'
  }

  void testShowOrders() {
    get('/orders'){
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
    }
    assertStatus 200
    def json = grails.converters.JSON.parse(response.getContentAsString())
    def jsonResponse = json.response
    assert jsonResponse
    assert jsonResponse.orderNumber.find { it == order1.orderNumber }

  }

  void testShowOrdersWithFilter() {

    get("/orders?status=${order1.status.id}"){
      headers['Content-Type'] = 'application/json'
      headers['Accept-Language'] = 'es'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
    }
    assertStatus 200
    def json = grails.converters.JSON.parse(response.getContentAsString())
    def jsonResponse = json.response
    assert jsonResponse
    assert jsonResponse.orderNumber.find { it == order1.orderNumber } == order1.orderNumber

  }

}
