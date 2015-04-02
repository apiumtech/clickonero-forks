package com.winbits.api.affiliation.services

import com.winbits.api.clients.bebitos.BebitosClient
import com.winbits.domain.affiliation.MigrationStatus
import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.VerticalPartner
import com.winbits.domain.catalog.Country
import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.bson.types.ObjectId
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BebitosService)
@Mock([User, VerticalPartner, MigrationStatus, Country])
@Build([User, Vertical, VerticalPartner, MigrationStatus])
@TestMixin(GrailsUnitTestMixin)
class BebitosServiceSpec extends Specification {

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

    void "account consult in Bebitos"() {
        setup:
        def email = "bebitos_partner_2323124asfd@mail.com"
        def password = "qwdwqdwqdwqd"
        Vertical.build(name: 'Bebitos')
        service.bebitosClient = Stub(BebitosClient) {
            findUserByCredentials(email, password) >> [response: [statusCode: 200]]
        }

        service.codeGeneratorService = Stub(CodeGeneratorService) {
            generateReferredCode() >> "1234567890"
        }

        when:
        def user = service.migrateUser(email, password)

        then:
        !user
        //user.email == email

    }

    void "migrate data user"() {
        setup:
        def vertical = Vertical.build(name: 'Bebitos')
        def user = User.build(email: "bebitos_partner_pending_data_23213213@mail.com")
        def verticalPartner = VerticalPartner.build(user: user, vertical: vertical)
        service.shippingAddressService = Mock(ShippingAddressService)
        service.partnerService = Mock(PartnerService)

        service.bebitosClient = Stub(BebitosClient) {
            userDetail(user.email) >> [
                    response: [statusCode: 200,
                            parsedResponseContent: [
                                    json: [id:16965,
                                            email:"manuel.gomez@clickonero.com.mx",
                                            created_at:"2014-08-13T13:53:51.332-05:00",
                                            orders:[],
                                            addresses:[
                                                    [phone:"1234567890",
                                                            zipcode:"16050",
                                                            country_id:1,
                                                            state:[name:"Distrito Federal", abbr:"DF"],
                                                            lastname:"Gomez N",
                                                            address1:"Francisco goytia",
                                                            address2:"San Marcos",
                                                            firstname:"Manuel",
                                                            country:[iso:"MX", name:"Mexico"],
                                                            main:true,
                                                            city:"Xochimilco",
                                                            id:36191,
                                                            state_id:9,
                                                            between_streets:null,
                                                            int_num:null,
                                                            ext_num:72]
                                            ]
                                    ]
                            ]
                    ]
            ]
        }

        when:
        service.migrateUserData(verticalPartner)

        then:
        1 * service.partnerService.saveInfoInMongoDb(_, vertical)
        1 * service.partnerService.updateAsMigrationComplete(_, verticalPartner)
    }

}
