package com.winbits.orders.domain

import com.winbits.domain.affiliation.User
import com.winbits.domain.orders.CardSubscription

import grails.test.mixin.TestFor

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CybersourceDRYService)
class CybersourceDRYServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "should obtain card subscriptions"() {
  setup:"mocking card subscriptions"
    User user = new User ()
    CardSubscription cardSubscription = 
      new CardSubscription (subscriptionId: '1',
                            user: user)
     CardSubscription cardSubscription2 = new CardSubscription (subscriptionId: '2',
                            user: user)
    List cardSubscriptions = [cardSubscription, cardSubscription2]
  and: "mocking cyberSourcePaymentService"
    Map cardData = [accountNumber: '123456789012345']
    Map cardAddress = [addres: 'address']
    def cyberSourcePaymentService = new Object ()
    cyberSourcePaymentService.metaClass.getCard = {paymentInfo ->
      int reasonCode = paymentInfo.subscriptionId == '1' ? 100 : 200
      Map response = [reasonCode: reasonCode]
      response.cardData = cardData
      response.address = cardAddress
      response
    }
    service.cyberSourcePaymentService = cyberSourcePaymentService

  when:
    def cards = service.obtainCardSubscriptions (cardSubscriptions)
  then:
    assert cards
    assert cards.size()  == 1
    def cardInfo = cards.first ().cardInfo
    assert cardInfo.cardData
    assert cardInfo.cardAddress
    assert cardInfo.subscriptionId
    assert !cardInfo.supportInstallments
	}

  void "should fill payment info" (){
  setup:"mocking grails application"
    Map grailsApplication = [config:[winbits:[cybersource:[merchantId:"1", 
                                            transactionURL:"url",
                                            transactionKey:'12345']
                                            ]]]
    service.grailsApplication = grailsApplication
  when:
    def paymentInfo = service.fillPaymentInfo ('1',1)
  then:
    assert paymentInfo
    assert paymentInfo.merchantID == "1"
    assert paymentInfo.transactionURL == "url"
    assert paymentInfo.transactionKey == "12345"
    assert paymentInfo.subscriptionId == "1"
  }

  void "should fill reponse when response status 100" (){
  setup:
  Map data = [:]
  data.reasonCode = 100
  CardSubscription subscription = new CardSubscription (subscriptionId:'1')
  when:
    def response = service.fillCardResponse (data, subscription)
  then:
    assert response
    assert response.subscriptionId == '1'
    assert !response.supportInstallments
    assert !response.cardData
    assert !response.address
  }
}
