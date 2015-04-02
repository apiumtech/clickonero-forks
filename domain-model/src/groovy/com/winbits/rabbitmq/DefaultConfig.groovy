package com.winbits.rabbitmq

def rabbitUser = 'guest'
def rabbitPassword = 'guest'
def rabbitHost = '127.0.0.1'
def rabbitVirtualHost = '/'

environments {
  qa {
    rabbitUser = 'winbits'
    rabbitPassword = 'khop5i69y8u'
    rabbitHost = '10.0.0.103'
  }
  staging {
    rabbitUser = 'winbits'
    rabbitPassword = '6U74-rvjHmK'
    rabbitHost = '10.0.0.220'
  }
  production {
    rabbitHost = '172.31.18.76'
    rabbitUser = 'winbits-rmq'
    rabbitPassword = 'er65h4r65gt'
  }
}


rabbitmq {
  connectionfactory {
    username = rabbitUser
    password = rabbitPassword
    hostname = rabbitHost
    virtualHost = rabbitVirtualHost
  }

  queues = {
    exchange name: 'paid.orders', type: fanout, durable: true, autoDelete: false, {
      weighOrderTotals autoDelete: false, durable: true, exclusive: false
      cashbackOrders autoDelete: false, durable: true, exclusive: false // cola del cashback
      sendOrderConfirmation autoDelete: false, durable: true, exclusive: false // cola del confirmacion de orden generada
    }
    exchange name: 'postprocess.payments', type: topic, durable: true, autoDelete: false, {
        postProcessOxxoPayment          binding:                    'oxxo.bc',          autoDelete:   false,   durable:          true,   exclusive:   false   //   notificacion   de   envio   de   correo   para   el   mail
        postProcessCyberSourcePayment   binding:'cybersource.*.#.#',   autoDelete:false,   durable:      true,    exclusive:false
        postProcessAmexPayment          binding:'amex.*.#',   autoDelete:false,   durable:      true,    exclusive:false
        postProcessReferencePayment     binding:'reference.hsbc',   autoDelete:false,   durable:      true,    exclusive:false
        postProcessAmexPayment          binding:'reference.hsbc',   autoDelete:false,   durable:      true,    exclusive:false
//      postProcessPayPalPayment binding: 'paypal.*.#', autoDelete: false, durable: true, exclusive: false
//      postProcessReferencePayment binding: 'reference.payment', autoDelete: false, durable: true, exclusive: false
    }

    exchange name: 'winbits.dead', type: fanout, durable: true, autoDelete: false, {
      notifyDeadMessage autoDelete: false, durable: true, exclusive: false
    }

      //nomicka queues
      exchange name: 'nomicka', type: fanout, durable: true, autoDelete: false, {
          exportItemGRoupProfilesNomicka autoDelete: false, durable: true, exclusive: false
      }

    generateOxxoPaymentTicket autodelete: false, durable: true, exclusive: false   //subir a s3 cola de mensaje
    generateOxxoPaymentTicketDeadLetter autodelete: false, durable: true, exclusive: false
    sendOrderConfirmationDeadLetter autoDelete: false, durable: true, exclusive: false
    weighOrderTotalsDeadLetter autoDelete: false, durable: true, exclusive: false
    cashbackOrdersDeadLetter autoDelete: false, durable: true, exclusive: false   //DeadLeter Cashback
    postProcessOxxoPaymentDeadLetter autoDelete: false, durable: true, exclusive: false
    generateHsbcPaymentTicket autoDelete: false, durable: true, exclusive: false   //subir a s3 cola de mensaje
    generateHsbcPaymentTicketDeadLetter autoDelete: false, durable: true, exclusive: false

    approveOrderPayment autoDelete: false, durable: true, exclusive: false
    approveOrderPaymentDeadLetter autoDelete: false, durable: true, exclusive: false
    checkOrderBalance autoDelete: false, durable: true, exclusive: false
    checkOrderBalanceDeadLetter autoDelete: false, durable: true, exclusive: false
    attemptRefundbits autoDelete: false, durable: true, exclusive: false
    cancelOrder autoDelete: false, durable: true, exclusive: false

    payPalConfirm autoDelete: false, durable: true, exclusive: false  //Es invocada en el proyecto de winbits-jobs

    postProcessCyberSourcePaymentDeadLetter autodelete: false, durable: true, exclusive: false
    postProcessAmexPaymentDeadLetter autodelete: false, durable: true, exclusive: false

    //tx mailing
    exchange name: 'mailing.tx', type: topic, durable: true, autoDelete: false, {
      sendOxxoPaymentTicketMail binding: 'oxxo.payment', autoDelete: false, durable: true, exclusive: false // ficha de pago
      sendResetMail binding: 'reset.password', autoDelete: false, durable: true, exclusive: false // reseteo de password
      sendConfirmAccountMail binding: 'confirm.account', autoDelete: false, durable: true, exclusive: false // confirmacion de cuenta
      sendWelcomeMail binding: 'confirm.welcome', autoDelete: false, durable: true, exclusive: false // Confirmacion de bienvenida
      sendOrderConfirmationMail binding: 'confirm.order', autoDelete: false, durable: true, exclusive: false // Confirmacion de orden
      sendHsbcPaymentTicketMail binding: 'hsbc.ticket.payment', autoDelete: false, durable: true, exclusive: false // Ficha de pago Hsbc
    }

    //DeadLetters of tx mailing
    sendOxxoPaymentTicketMailDeadLetter autoDelete: false, durable: true, exclusive: false
    sendHsbcPaymentTicketMailDeadLetter autoDelete: false, durable: true, exclusive: false
    sendResetMailDeadLetter autoDelete: false, durable: true, exclusive: false
    sendConfirmAccountMailDeadLetter autoDelete: false, durable: true, exclusive: false
    sendWelcomeMailDeadLetter autoDelete: false, durable: true, exclusive: false
    sendOrderConfirmationMailDeadLetter autoDelete: false, durable: true, exclusive: false

    misLogEvent autoDelete: false, durable: true, exclusive: false
    misLogEventDeadLetter autoDelete: false, durable: true, exclusive: false

    sendStatusODCWMS binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics
    sendSkuIncomeWMS binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics
    sendStatusSkuOutcomeWMS binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics

    sendExtraOutcomeWMS binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics
    sendExtraOutcomeWMSDeadLetter autoDelete: false, durable: true, exclusive: false

    cancelExtraOutcomeItemInWMS binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics
    cancelExtraOutcomeItemInWMSDeadLetter binding: 'admin.to.logistics', autoDelete: false, durable: true, exclusive: false   // comunication admin to logistics

    outcomesFactoryBySku autoDelete: false, durable: true, exclusive: false   //generacion de outcomes cuando se ingresa stock al almacen params = skuId, warehouseId
    outcomesFactoryBySkuDeadLetter autoDelete: false, durable: true, exclusive: false   //generacion de outcomes cuando se ingresa stock al almacen params = skuId, warehouseId

    generateSkuOutcomesByOrder autoDelete: false, durable: true, exclusive: false
    generateSkuOutcomesByOrderDeadLetter autoDelete: false, durable: true, exclusive: false

    skuOutcomeExtra autoDelete: false, durable: true, exclusive: false
    skuOutcomeExtraDeadLetter autoDelete: false, durable: true, exclusive: false

    generateOutcomeRequestBySkuOutcomeIds autoDelete: false, durable: true, exclusive: false   //generacion de outcomeRequest con una lista de skuOutcomes
    generateOutcomeRequestBySkuOutcomeIdsDeadLetter autoDelete: false, durable: true, exclusive: false   //deaadLetter generacion de outcomeRequest con una lista de skuOutcomes

    executeWinbitsJobCommon autoDelete: false, durable: true, exclusive: false   //execucion de cualquier job con un mensaje compuesto por nombre de la clase y los parametros si los necesita
    executeWinbitsJobCommonDeadLetter autoDelete: false, durable: true, exclusive: false   //deaadLetter execucion de cualquier job con un mensaje compuesto por nombre de la clase y los parametros si los necesita

    sendStatusCancelSkuOutcomeToWMS autoDelete: false, durable: true, exclusive: false   //envia peticion de cancelacion de skuOutcome a wms
    sendStatusCancelSkuOutcomeToWMSDeadLetter autoDelete: false, durable: true, exclusive: false   //deaadLetter envia peticion de cancelacion de skuOutcome a wms

    sendIncomeResponseToWMS autoDelete: false, durable: true, exclusive: false //envia el reporte despues de procesar entradas a wms
    sendIncomeResponseToWMSDeadLetter autoDelete: false, durable: true, exclusive: false

    migrateUserFromPartner autoDelete: false, durable: true, exclusive: false
    migrateUserFromPartnerDeadLetter autoDelete: false, durable: true, exclusive: false

    //Refund Orders
    refundPartialOrder autoDelete: false, durable: true, exclusive: false
    refundPartialOrderDeadLetter autoDelete: false, durable: true, exclusive: false

    refundTotalOrder autoDelete: false, durable: true, exclusive: false
    refundTotalOrderDeadLetter autoDelete: false, durable: true, exclusive: false

    resendOdcMongoToWms autoDelete: false, durable: true, exclusive: false
    resendOdcMongoToWmsDeadLetter autoDelete: false, durable: true, exclusive: false

    virtualWarehouseProcess autoDelete: false, durable: true, exclusive: false
    virtualWarehouseProcessDeadLetter autoDelete: false, durable: true, exclusive: false

    ordersPaymentBitsPending autoDelete: false, durable: true, exclusive: false
    ordersPaymentBitsPendingDeadLetter autoDelete: false, durable: true, exclusive: false
  }
}
