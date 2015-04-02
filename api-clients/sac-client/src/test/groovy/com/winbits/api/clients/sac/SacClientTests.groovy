package com.winbits.api.clients.sac

import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = ['/app-ctx.xml'])
@Ignore
class SacClientTests {
  private static Logger log = LoggerFactory.getLogger (SacClientTests)

  @Autowired
  CambioTallaClient cambiaTallaClient

  @Test
  void shouldDoLogin (){
    def var = cambiaTallaClient.login ('marco@dream-it.com.mx', 'P@ssword')
    assert var
    def respuesta = var.response
    assert respuesta.state == 200
    assert respuesta.obj
    assert respuesta.obj.apiToken
  }

  @Test
  void shouldDoListadoCambioTalla (){
    def response = cambiaTallaClient.listadoCambioTalla ([email:'diegoazd@hotmail.com'])
    assert response
    def restpond = response.response
    assert restpond.state == 200
    assert restpond.obj
  }

  @Test
  void shouldDoCambioTallaDetalle (){
    def response = cambiaTallaClient.cambioTallaDetalle (729).response
    assert response
    assert response.state == 200
    def orderDetail = response.obj.first ()
    assert orderDetail.idOrden == 729
    assert orderDetail.order_number == '1401221842--729' 
    assert orderDetail.user_name
    assert orderDetail.date_created
    assert orderDetail.order_details
    assert orderDetail.order_details.size () == 2

    def orderDetail1 = orderDetail.order_details.first ()
    assert orderDetail1.sku_profile_id
    assert orderDetail1.quantity
    assert orderDetail1.delivery_date
    assert orderDetail1.status.size () == 2
    assert orderDetail1.status.refunded == 0
    assert orderDetail1.status.almacen == 1
    assert !orderDetail1.logistic_status
    assert orderDetail1.quantity == 1
    assert orderDetail1.delivery_date
    
    assert orderDetail1.item
    assert orderDetail1.item.name == 'Bra'
    assert orderDetail1.item.attribute_label == 'Rojo'
    assert orderDetail1.item.brand == 'Wonderbra'
    assert orderDetail1.item.att_n == 'Talla'
    assert orderDetail1.item.att_v == '34B'
    assert orderDetail1.item.price == 500.00

    def orderDetail2 = orderDetail.order_details.last ()
    assert orderDetail2.sku_profile_id
    assert orderDetail2.quantity
    assert orderDetail2.delivery_date
    assert orderDetail2.status.size () == 2
    assert orderDetail2.status.refunded == 0
    assert orderDetail2.status.almacen == 1
    assert !orderDetail2.logistic_status
    assert orderDetail2.quantity == 1
    assert orderDetail2.delivery_date
                      
    assert orderDetail2.item
    assert orderDetail2.item.name == 'Bra'
    assert orderDetail2.item.attribute_label == 'Rojo'
    assert orderDetail2.item.brand == 'Wonderbra'
    assert orderDetail2.item.att_n == 'Talla'
    assert orderDetail2.item.att_v == '34C'
    assert orderDetail2.item.price == 500.00
  }

  @Test
  void shouldPostListadoItems (){
    def response = cambiaTallaClient.listadoItems (4845,1).response
    
    assert response
    assert response.state == 200
    assert response.obj
    assert response.obj.size () == 2
    def item = response.obj.first ()
    assert item
    assert item.sku_profile_id == 11248
    assert item.attribute_label == 'Negro'
    assert item.attribute_name == 'Color'
    assert item.att_v == 'S'
    assert item.att_n == 'Talla'
    assert item.stock_sku_profile == 0
    assert item.sku_id == 11248
    assert item.cantidadMostrar == 0

    def item2 = response.obj.last ()
    assert item2
    assert item2.sku_profile_id == 11250
    assert item2.attribute_label == 'Negro'
    assert item2.attribute_name == 'Color'
    assert item2.att_v == 'L'
    assert item2.att_n == 'Talla'
    assert item2.stock_sku_profile == 1
    assert item2.sku_id == 11250
    assert item2.cantidadMostrar == 1
  }

  @Test
  void shouldPutCambioTalla (){
    def response = cambiaTallaClient.cambiaTalla (1,1,1).response
    assert response
    assert response.state
    assert response.message
  }

  @Test
  void shouldGetLogin (){
    def response = cambiaTallaClient.login ('marco@dream-it.com.mx', 'P@ssword').response
    assert response
    assert response.state == 200
    assert response.obj.apiToken
  }

  @Test
  void shouldFailLogin (){
    def response = cambiaTallaClient.login ('', 'P@ssword').response
    assert response
    assert response.state == 400
    assert response.message
  }

}
