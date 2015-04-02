package com.winbits.api.dummyclients.affiliation

import com.winbits.api.clients.affiliation.AffiliationClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component('affiliationClient')
class DummyAffiliationClient implements AffiliationClient {

  private static Logger log = LoggerFactory.getLogger(DummyAffiliationClient)

  @Override
  Map register(String email, String password) {
    log.info('Registering new account [email: {}, password: {}]', email, password)
    Assert.notNull(email, 'Please, specify the email!')
    Assert.notNull(email, 'Please, specify the password!')
    [active: true]
  }

  @Override
  Map login (String email, String password){
    log.info('Logging with account [email: {}, password: {}]', email, password)
    Assert.notNull(email, 'Please, specify the email!')
    Assert.notNull(password, 'Please, specify the password!')
    [meta: [
       status:200
     ],
     response: [
       id: 1,
       email: 'email@foo.com',
       apiToken:'ap1T0k3n',
       bitsBalance: 1000000,
       profile: [
         name: 'name',
         lastName: 'last name',
         birthdate: '2000-02-29',
         gender: null,
         zipCodeInfo:[]
       ]
     ]
    ]
  }

    @Override
    Map couponList(Long orderDetailId, String apiToken) {
        def today= new Date()
        [meta: [
                status:200
        ],
        coupon :[
            "id": "long",
            "orderDetailId": "long",
            "url": "string",
            "code":"string"
        ],
        available:[availableCouponDate:today , expireCouponDate: today.plus(7)]
        ]
    }

    @Override
    Map couponId(Long couponId, Long orderDetailId, String format, String apiToken) {
        [meta: [
                status:200
        ],
                response :[
                        "coupon":[
                                "orderDetailId":106564,
                                "id":32396,
                                "url":"http://coupondev.winbits.com.s3.amazonaws.com/coupons/2516717/35385/cupon_8_a34ffe-gvs.pdf?Expires=1423521534&AWSAccessKeyId=AKIAJTVXKA22V566I5TQ&Signature=wS6d83xs3veWwrO6CQauTrgE%2B0A%3D"

                        ]
                ]
        ]

    }
}
