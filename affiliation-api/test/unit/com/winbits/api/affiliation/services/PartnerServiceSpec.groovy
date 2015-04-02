package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.VerticalPartner
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@TestMixin(GrailsUnitTestMixin)
@Mock([User, Vertical, VerticalPartner])
@Build([User, Vertical, VerticalPartner])
@TestFor(PartnerService)
class PartnerServiceSpec extends Specification {

  def setup() {
    User.metaClass.encodePassword {->
    }
    User.metaClass.generateApiToken {->
      apiToken = 'asdasdadsasd'
      apiToken
    }
  }

  def cleanup() {
  }


  void "find orders history test"() {
    setup:
    def vertical = Vertical.build(name: 'Bebitos')
    def user = User.build(email: "bebitos_partner_9890438vdv@mail.com")
    VerticalPartner.build(user: user, vertical: vertical)
    service.mongoDbService = Stub(MongoDbService) {
      findDocumentById(vertical.name, _) >> '{"orders": [{"id": 1}]}'
    }

    when:
    def result = service.findOrdersHistory(user, vertical)

    then:
    result

  }
}
