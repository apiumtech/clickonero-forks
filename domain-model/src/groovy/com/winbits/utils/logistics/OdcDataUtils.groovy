package com.winbits.utils.logistics

import com.winbits.domain.catalog.ProviderContact
import com.winbits.domain.catalog.ProviderContactTypeEnum
import com.winbits.domain.catalog.Sku
import com.winbits.domain.catalog.TelephoneTypeEnum
import com.winbits.domain.logistics.IncomeTypeEnum
import com.winbits.domain.logistics.Odc
import com.winbits.domain.logistics.OdcDetail
import com.winbits.domain.logistics.OdcDetailStatusEnum
import com.winbits.orders.utils.OrderDataUtils
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

/**
 * Created by manuel on 2/17/14.
 */
class OdcDataUtils {

  def static Odc createOdc(Sku sku, quantityRequired, warehouse, odcType, incomeType){
    def provider = sku.item.itemGroup.provider
    def contact = ProviderContact.build(firstName: 'first name contact', lastName:'last name contact',
        providerContactType: ProviderContactTypeEnum.MAIN.domain, mainTelephoneType: TelephoneTypeEnum.HOME.domain)
    provider.addToContacts(contact)
    provider.save()
    Odc odc = Odc.build(deliveryDate: new LocalDateTime(),
        warehouse: warehouse, incomeType: incomeType, provider: provider, type: odcType)
    def odcDetail = OdcDetail.build(sku: sku, quantityRequired: quantityRequired, quantityReceived: 0,
        status: OdcDetailStatusEnum.PENDING.domain, cost: 10, totalCostReceived: 0)
    odc.addToOdcDetails(odcDetail)
    odc.save()
  }

  def static createConsignmentSku(DateTime activeEnd, Integer quantityOnHand, Integer quantityReserved) {
    def sku = OrderDataUtils.createSkuProfile().sku
    def itemGroup1 = sku.item.itemGroup
    itemGroup1.activeEnd = activeEnd
    itemGroup1.incomeType = IncomeTypeEnum.CONSIGNMENT.domain
    itemGroup1.save(flush: true)
    sku.quantityReserved = quantityReserved
    sku.quantityOnHand = quantityOnHand
    sku.save(flush: true)
  }

}
