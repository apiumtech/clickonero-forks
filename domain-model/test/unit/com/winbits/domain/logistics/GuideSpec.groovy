package com.winbits.domain.logistics

import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import com.winbits.domain.catalog.ItemStatusEnum
import com.winbits.domain.utils.DomainModelUtils
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Carrier,GuideCarrierHistoryStatus, CarrierStatus])
@TestFor(Guide)
class GuideSpec extends Specification {

	def setup() {
//    DomainModelUtils.createPersistentEnumModel(GuideCarrierHistoryStatus)
    DomainModelUtils.createPersistentEnumModel(CarrierStatusEnum)

	}

	def cleanup() {
	}

	void "test InsertGuide"() {


    when:
    def carrier1 = new Carrier().save(validate:false)
    def carrier2 = new Carrier().save(validate:false)

    def sizeHistory = GuideCarrierHistoryStatus.count
    def sizeGuide = Guide.count

    def guide = new Guide(guide: "111111", carrier: carrier1,carrierStatus: CarrierStatusEnum.COMFIRMED.domain).save()

    then:
          assert guide==Guide.findByGuide("111111")

	}
}