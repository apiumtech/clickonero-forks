package com.winbits.domain.catalog

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PaymentMethod)
class PaymentMethodSpec extends Specification {

  void "Saving PaymentMethod"() {
    when: "Create a PaymentMethod"
      PaymentMethod paymentMethod = new PaymentMethod(
        identifier : "identifier",
        active : true,
        maxAmount : 1,
        minAmount : 0,
        displayOrder : 0,
        logo : "http://www.lol.com/",
        expireMinutes : 120,
        offline : false
      )
      paymentMethod.save()

    then: "The constraints results"
      assert paymentMethod.id > 0
  }
}
