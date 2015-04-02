package com.winbits.api.dummyclients.clickonero
import com.winbits.api.clients.clickonero.ClickoneroClient
import org.springframework.stereotype.Component

@Component("clickoneroClient")
class DummyClickoneroClientImpl implements ClickoneroClient {

  Map ordersClickoneroHistory(Long personId){

    def map = [
        id: 2570990,
        email: "test@com.winbits.api.clients.sms.com.mx",
        firstName: "test",
        lastName: "test test",
        birthdate: 579333600000,
        gender: "M",
        referrerCode: "rzhwg1dyx6rmpd2",
        registered: true,
        enabled: true,
        accountLocked: false,
        merchantId: null,
        merchantType: 0,
        dateCreated: 1380890988000,
        lastUpdated: 1403365451000,
        phoneNumber: "51166854",
        zipCode: [
            id: 52846,
            zipCode: "55080",
            allRelated: [
                [
                    class: "com.com.winbits.api.clients.sms.hipstore.model.catalog.address.ZipCodeInfo",
                    id: 52845,
                    city: "Ecatepec de Morelos",
                    cityCode: "15",
                    county: "Ecatepec de Morelos",
                    countyCode: "4801",
                    locationCode: "31",
                    locationName: "Lomas de Ecatepec",
                    locationType: "Colonia",
                    postalOfficeCode: "55031",
                    state: "México",
                    stateCode: "15",
                    zipcode: "55080",
                    zone: "Urbano"
                ]
            ]
        ],
        orders: [
            [
                id: 682243,
                orderStatus: "PAID",
                dateCreated: 1401893182000,
                paidDate: 1401893415000,
                orderNumber: "1406040946--682243",
                itemsTotal: 399,
                total: 478,
                code: null,
                shippingStatus: "PENDING",
                skus: [
                    [
                        orderDetailId: 948816,
                        id: 510097,
                        sku: "3304MUJOOU017506CO00167092TR",
                        status: "AVAILABLE",
                        cost: 399,
                        itemId: 167092,
                        itemType: "PRODUCT",
                        availableWhileRunning: false,
                        customCode: null,
                        quantity: 1,
                        shippingCost: 0,
                        ean: "1.663433E7",
                        name: "Producto Name",
                        path: "Producto Path",
                        shortDescription: "Producto",
                        url: "//s3.amazonaws.com/store.media.com.winbits.api.clients.sms.com.mx/media/item/00167092/xZ5yS-1",
                        ext: "jpg",
                        attributes: [
                            [
                                attrName: "Color",
                                label: "Turquesa",
                                value: "#4EA5A6",
                                valueType: "COLOR"
                            ]
                        ],
                        endDateCampaign: 1402462799000,
                        dateDelivery: 1404190799000
                    ]
                ],
                shippingStatusMessage: "Pagada  y en espera de envio"
            ],
            [
                id: 664056,
                orderStatus: "PAID",
                dateCreated: 1400687293000,
                paidDate: 1400687360000,
                orderNumber: "1405211048--664056",
                itemsTotal: 59,
                total: 59,
                code: null,
                shippingStatus: "PENDING",
                skus: [
                    [
                        orderDetailId: 920197,
                        id: 493201,
                        sku: "4303EXGABE016966ALnullDEFAULT7B9SZ",
                        status: "AVAILABLE",
                        cost: 59,
                        itemId: 160463,
                        itemType: "SERVICE",
                        availableWhileRunning: false,
                        customCode: false,
                        quantity: 1,
                        shippingCost: 0,
                        ean: "0000000000000000000000000",
                        name: "Service Name",
                        path: "Service Path",
                        shortDescription: "Service",
                        url: "//s3.amazonaws.com/store.media.com.winbits.api.clients.sms.com.mx/media/item/00160463/x0baV-beikery",
                        ext: "jpg",
                        attributes: [
                            [
                                attrName: "Default Event Attribute",
                                label: "Default",
                                value: "Default",
                                valueType: "TEXT"
                            ]
                        ],
                        endDateCampaign: 1402030799000,
                        dateDelivery: 1403758799000,
                        coupons: [
                            [
                                id: 354974,
                                counter: 3,
                                randomCode: "9uwkhu",
                                ccv: "6d2",
                                code: "3#9uwkhu-6d2",
                                dateUsed: 1405116665000,
                                enable: true
                            ]
                        ],
                        claimEnd: 1406005199000
                    ]
                ],
                shippingStatusMessage: "Pagada "
            ]
        ],
        oldCoupons: [],
        puCoupons: []
    ]

    return [response:map]
  }
}