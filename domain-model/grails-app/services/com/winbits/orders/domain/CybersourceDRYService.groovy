package com.winbits.orders.domain

import com.winbits.payments.commons.cybersource.CyberSourceCommonPaymentInfo
import com.winbits.payments.cybersource.service.CybersourceService

class CybersourceDRYService {

   def grailsApplication
   def cyberSourcePaymentService

    List obtainCardSubscriptions(List cardSubscriptions) {
      def subscriptions = cardSubscriptions.collect{ subscription ->
        CyberSourceCommonPaymentInfo paymentInfo = fillPaymentInfo (subscription.subscriptionId, 
          subscription.user.id)

        def data = cyberSourcePaymentService.getCard (paymentInfo)
        def cardInfo = fillCardResponse (data,subscription)
        cardInfo ? [cardInfo:cardInfo] : null
      }
      subscriptions - null
    }

    Map fillCardResponse (def data, def subscription){
     Map cardInfo
     if (data.reasonCode == CybersourceService.CYBERSOURCE_OK){
       cardInfo = [:]
       cardInfo.cardData = data.cardData
       cardInfo.cardAddress = data.address
       cardInfo.subscriptionId = subscription.subscriptionId
       cardInfo.supportInstallments = subscription?.bin?.issuer?.installment
       cardInfo.cardPrincipal = subscription.principal
         
     }
     cardInfo
    }

    def fillPaymentInfo (String subscription, Long userId){
      CyberSourceCommonPaymentInfo paymentInfo = new CyberSourceCommonPaymentInfo ()
      paymentInfo.with {
        merchantID = grailsApplication.config.winbits.cybersource.merchantId
        transactionKey = grailsApplication.config.winbits.cybersource.transactionKey
        transactionURL = grailsApplication.config.winbits.cybersource.transactionURL
        subscriptionId = subscription
        transactionReference = userId.toString ()
      }

      paymentInfo
    }
}
