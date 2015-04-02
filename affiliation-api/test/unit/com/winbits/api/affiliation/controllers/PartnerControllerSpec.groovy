package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.services.PartnerService
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([User, Vertical])
@Build([User, Vertical])
@TestFor(PartnerController)
class PartnerControllerSpec extends Specification {

  def user

  def setup() {

    controller.metaClass.restpond { response ->
      this.response = response
    }

    User.metaClass.encodePassword {->
    }
    User.metaClass.generateApiToken {->
      apiToken = 'asdasdadsasd'
      apiToken
    }

    controller.metaClass.getAuthenticatedUser {->
      user
    }

    user = User.build(email: 'bebitos_partner_2308239jekf@mail.com')
  }

  def cleanup() {
  }


  void "getting orders history"() {
    given:
    controller.partnerService = Mock(PartnerService)
    def vertical = Vertical.build(name: "Bebitos")

    when:
    controller.history(vertical.id)

    then:
    1 * controller.partnerService.findOrdersHistory(user, _)

  }
}