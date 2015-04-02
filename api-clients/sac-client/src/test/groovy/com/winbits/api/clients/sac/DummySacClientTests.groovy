package com.winbits.api.clients.sac

import com.winbits.api.dummyclients.sac.DummyCambioTallaClient

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class DummySacClientTests {

  private static Logger log = LoggerFactory.getLogger(DummySacClientTests)

  @Autowired
  DummyCambioTallaClient dummyCambioTallaClient

  @Test
  void shouldDoGet (){
    def response = dummyCambioTallaClient.listadoCambioTalla ([orden:1]).response
    assert response
    assert response.state == 200
    assert response.obj.size () == 2
    def first = response.obj.first ()
    assert first.idOrden == 729
    assert first.email == 'email@domain.com'
    assert first.order_number == '1401221842--729'
    assert first.noItems == 2
    assert first.bits_total == 0
    assert first.total == 1000
    
    def last = response.obj.last ()
    assert last.idOrden == 730
    assert last.email == 'email2@domain.com'
    assert last.order_number == '1401221842--730'
    assert last.noItems == 2
    assert last.bits_total == 0
    assert last.total == 1000
    assert last.statusName == "PAID"
  }

  @Test
  void shouldGetCambioTallaDetail (){
    def response = dummyCambioTallaClient.cambioTallaDetalle (729).response

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
    assert orderDetail1.order_detail_id == 3550
    assert orderDetail1.sku_profile_id
    assert orderDetail1.quantity
    assert orderDetail1.delivery_date
    assert orderDetail1.status.size () == 2
    assert orderDetail1.status.refunded == 0
    assert orderDetail1.status.almacen == 1
    assert orderDetail1.logistic_status == null
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
    assert orderDetail2.order_detail_id == 3551
    assert orderDetail2.sku_profile_id
    assert orderDetail2.quantity
    assert orderDetail2.delivery_date
    assert orderDetail2.status.size () == 2
    assert orderDetail2.status.refunded == 0
    assert orderDetail2.status.almacen == 1
    assert orderDetail2.logistic_status == null
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
  void shouldObtainListadoItems (){
    def response = dummyCambioTallaClient.listadoItems (1,1).response

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
    assert item2.att_v == 'S'
    assert item2.att_n == 'Talla'
    assert item2.stock_sku_profile == 2
    assert item2.sku_id == 11250
    assert item2.cantidadMostrar == 2
  }

  @Test (expected=PowerAssertionError.class)
  void shouldFailOnNullIdDetalle (){
    dummyCambioTallaClient.listadoItems (null, 1)
  }
  
  @Test (expected=PowerAssertionError.class)
  void shouldFailOnNullCantidad (){
    dummyCambioTallaClient.listadoItems (1, null)
  }
  
  @Test (expected=PowerAssertionError.class)
  void shouldFailOnNullParameters (){
    dummyCambioTallaClient.listadoItems (null, null)
  }

  @Test
  void shouldCambiarTalla (){
    def response = dummyCambioTallaClient.cambiaTalla (1,1,1).response

    assert response
    assert response.status_code == 200
    assert response.message == 'Actualizacion exitosa'
  }

  @Test (expected= PowerAssertionError.class)
  void shouldFailOnNullCantidadCambiaTalla (){
    dummyCambioTallaClient.cambiaTalla (null, 1,1)
  }
  
  @Test (expected= PowerAssertionError.class)
  void shouldFailOnNullSkuProfile (){
    dummyCambioTallaClient.cambiaTalla (1, null,1)
  }

  @Test
  void shouldGetLogin (){
    def response = dummyCambioTallaClient.login ('user', 'password').response
    assert response
    assert response.state == 200
    assert response.obj
    assert response.obj.apiToken
  }
  
  @Test (expected= PowerAssertionError.class)
  void shouldFailLoginOnNullUser (){
    dummyCambioTallaClient.login (null, 'password')
  }
  
  @Test (expected= PowerAssertionError.class)
  void shouldFailLoginOnNullpASS (){
    dummyCambioTallaClient.login ('user', null)
  }
}
