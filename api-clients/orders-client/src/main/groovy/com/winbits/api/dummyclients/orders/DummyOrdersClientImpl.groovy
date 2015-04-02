package com.winbits.api.dummyclients.orders
import com.winbits.api.clients.orders.OrdersClient
import org.springframework.stereotype.Component

@Component("ordersClient")
class DummyOrdersClientImpl implements OrdersClient {

  Map showForAdmin(Long id, String apiToken){

    def map = [
        [
            cardInfo: [
                cardData: [
                    'accountNumber': 'XXXXXXXXXXXX1111',
                    'expirationMonth': '08',
                    'expirationYear': '2018',
                    'cardType': 'Visa',
                    'currency': 'MXN',
                    'firstName': 'QWEQWE',
                    'lastName': 'QWEQWE',
                    'phoneNumber': '987987987987987',
                    'unmasked': '4111111111111111'
                ],
                'cardAddress': [
                    'country': 'MX',
                    'postalCode': '55080',
                    'state': '987987',
                    'street1': '987987',
                    'number': '987987',
                    'city': '987987'
                ],
                'subscriptionId': '9997000125386132',
                'supportInstallments': true,
                'cardPrincipal': true
            ]
        ]
    ]
    [response:map]
  }

    @Override
    Map cartItemsFromNomicka(List cartCommands, String apiToken) {
       def map = [
               'meta':[
                       status:200

               ],
               'response':[
                       "id":1538,
                       "email":"nomicka@clients.sms.com",
                       "orderNumber":"1409011607--0000",
                       "itemsTotal":0,
                       "shippingTotal":0,
                       "bitsTotal":0,
                       "total":0,
                       "cashTotal":250,
                       'status':"CHECKOUT",
                       'orderDetails':[],
                       'orderPayments':[],
                       'vertical':[],
                       'shippingOrder':[],
                       'cashback':null,
                       'estimatedDeliveriDate':null
               ]

               ]

        [response:map]
    }

    @Override
    Map paymentOrder(Map request, String apiToken) {
        def response =[
                'meta':[
                        status:200

                ],
                'response':[
                        "id":1538,
                        "email":"nomicka@clients.sms.com",
                        "orderNumber":"1409011607--0000",
                        "itemsTotal":0,
                        "shippingTotal":0,
                        "bitsTotal":0,
                        "total":0,
                        "cashTotal":250,
                        'status':"PENNDING",
                        'orderDetails':[],
                        'orderPayments':[],
                        'vertical':[],
                        'shippingOrder':[],
                        'cashback':null,
                        'estimatedDeliveriDate':null
                ]

        ]
        [response:response]
    }
}