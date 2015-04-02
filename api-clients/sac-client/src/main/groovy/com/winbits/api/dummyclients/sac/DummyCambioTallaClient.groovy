package com.winbits.api.dummyclients.sac

import com.winbits.api.clients.sac.CambioTallaClient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component('cambioTallaClient')
class DummyCambioTallaClient implements CambioTallaClient {
  private static Logger log = 
    LoggerFactory.getLogger(DummyCambioTallaClient)

    @Override
    Map listadoCambioTalla (Map params){
      log.info "query params: {}", params

      def response = [state:200,
      obj:[[idOrden:729,
      user_id:47,
      email:"email@domain.com",
      order_number:"1401221842--729",
      user_name:"Diego Zarate",
      date_created:"01/05/2014",
      statusName:"PENDING",
      paid_date:"01/05/2014",
      payment_method:"reference.hsbc",
      noItems:2,
      shipping_total:0,
      bits_total:0,
      total:1000],[idOrden:730,
      user_id:48,
      email:"email2@domain.com",
      order_number:"1401221842--730",
      user_name:"name lastname",
      date_created:"01/05/2014",
      statusName:"PAID",
      paid_date:"01/05/2014",
      payment_method:"cybersource_cc",
      noItems:2,
      shipping_total:0,
      bits_total:0,
      total:1000]]
      ]

      [response:response]
  }

  @Override
  Map cambioTallaDetalle (Long orderId){
    log.info "id de la orden {}", orderId

    assert orderId

    def response = [state: 200,
    obj:[
    [
    idOrden: orderId,
    order_number: "1401221842--$orderId",
    user_name: "name lastNeim",
    date_created: "01-23-2014 00:42:44",
    order_details: [
    [
    order_detail_id: 3550,
    sku_profile_id: 14595,
    item: [
    name: 'Bra',
    brand: 'Wonderbra',
    attribute_label: 'Rojo',
    attribute_name: 'Color',
    att_n: 'Talla',
    att_v: '34B',
    price: 500.00
    ],
    quantity: 1,
    delivery_date: '01/23/2014 00:42:44',
    status: [
    refunded: 0,
    almacen: 1
    ],
    logistic_status: null
    ],
    [
    order_detail_id: 3551,
    sku_profile_id: 14596,
    item: [
    name: 'Bra',
    brand: 'Wonderbra',
    attribute_label: 'Rojo',
    attribute_name: 'Color',
    att_n: 'Talla',
    att_v: '34C',
    price: 500.00
    ],
    quantity: 1,
    delivery_date: '01/23/2014 00:42:44',
    status: [
    refunded: 0,
    almacen: 1
    ],
    logistic_status: null
    ]
    ],
    status_name: 'PENDING',
    paid_date: '',
    payment_method: 'reference.hsbc',
    noItems: 1,
    shipping_total: 0.0,
    bits_total: 0.0,
    total: 1000.0
    ]
    ],
    message: null
    ]

    [response:response]
  }

  @Override
  Map listadoItems (Long idDetalle, Integer cantidad){
    log.info ("id del detalle: {} cantidad: {}", idDetalle, cantidad)

    assert idDetalle
    assert cantidad

    def response = [ state: 200,
      obj: [
        [
        sku_profile_id:11248,
        quantity: 1,
        stock_sku: 0,
        stock_sku_profile:0,
        attribute_label: 'Negro',
        attribute_name: 'Color',
        att_v: 'S',
        att_n: 'Talla',
        sku_id: 11248,
        cantidadMostrar: 0
        ],
        [
        sku_profile_id:11250,
        quantity: 1,
        stock_sku: 1,
        stock_sku_profile:2,
        attribute_label: 'Negro',
        attribute_name: 'Color',
        att_v: 'S',
        att_n: 'Talla',
        sku_id: 11250,
        cantidadMostrar: 2
        ]
      ]
    ]

    [response:response]
  }  

  @Override
  Map cambiaTalla (Integer cantidad, Long idSkuProfile, Long idDetalle){
    log.info ("cambio de talla cantidad: {}  --- sku profile: {}")

    assert cantidad
    assert idSkuProfile
    assert idDetalle
    
    [response: [status_code: 200, message: "Actualizacion exitosa"]]
  }

  @Override
  Map login (String user, String password){
    assert user
    assert password

    def response = [
       state:200,
       obj:[apiToken: 'abcdefgkijklmnop'],
       message: null
     ]
    [response:response]
  }
}
