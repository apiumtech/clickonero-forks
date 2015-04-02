package com.winbits.api.dummyclients.migration

import com.winbits.api.clients.migration.MigrationClient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component('migrationClient')
class DummyMigrationClient implements MigrationClient {
  
  private static Logger log = LoggerFactory.getLogger(DummyMigrationClient)

  Map obtainPersonByEmail (String email){
    log.info('Obtaining person [email: {}]', email)
    Assert.notNull(email, 'Please, specify the email!')
    [firstName:'name', lastName: 'lastName', gender:'M', locale:'es_MX',
     referrerCode:'012312asda', referredById: 0, email:'foo@foo.com']
  }

  Map getShippingAddressByEmail(String email){
    log.info('Obtaining shipping address [email: {}]', email)
    Assert.notNull(email, 'Please, specify the email!')
        [status:200,
         shippingAddress:[
            [
              firstName:"maria",
              lastName:"rodriguez",
              lastName2:"medina",
              phone:"3929241011",
              location:"Jamay Centro",
              zipCode:"47900",
              externalNumber:"# 76",
              internalNumber:null,
              street:"ortiz ",
              indications:""
            ]
        ]
        ]
  }
  
  Map obtainUserInfo (String email){
    log.info('Obtaining shipping address [email: {}]', email)
    List credits = []
    Map credit1 = [balance:50, expirationDate:null, description:'una descripcion corta']
    Map credit2 = [balance:279, expirationDate:1416635998000 , description:'una descripcion corta']
    credits << credit1
    credits << credit2

    Assert.notNull(email, 'Please, specify the email!')
    [response: [status:200,
    shippingAddress:[
    [
    firstName:"maria",
    lastName:"rodriguez",
    lastName2:"medina",
    phone:"3929241011",
    location:"Jamay Centro",
    zipCode:"47900",
    externalNumber:"# 76",
    internalNumber:null,
    street:"ortiz ",
    indications:""
    ]
    ],
    clickoneroCredits: credits
    ]
    ]
  }

}
