package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.WaitingListItem
import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.*
import functionaltestplugin.FunctionalTestCase
import grails.converters.JSON
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/21/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
class WaitingListNotifierFunctionalTests extends FunctionalTestCase {
  void testWaitingListItemInStockErrorIfSkuProfileDoesNotExist() {
    put('/waiting-list-notifier') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: -5] as JSON
      }
    }
    assertStatus 404
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER010'
    assert json.meta.message == 'El articulo no existe'
  }

  void testWaitingListItemInStockErrorSkuProfileIsNotAvailable() {
    put('/waiting-list-notifier') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: 24] as JSON
      }
    }
    assertStatus 404
    def json = JSON.parse(response.getContentAsString())
    assert json.meta.code == 'AFER015'
    assert json.meta.message == 'El articulo no está disponible'
  }

  void testWaitingListItemInStockNotifier() {
    def vertical = Vertical.get(1)
    def itemGroupTo = new ItemGroup(name: "nameTo24", maxPerUser: 10, maxPerOrder: 10, minPerOrder: 5, activeStart: new DateTime().minusDays(1), dateCreated: new DateTime(),
        lastUpdated: new DateTime(), status: ItemStatus.load(1), requireShipping: false, vertical: vertical, incomeType: IncomeType.load(1),
        provider: Provider.load(1), brand: Brand.load(1), categoryType: CategoryType.load(1), model: 'model',
        subCategoryType: SubCategoryType.load(1), verticalMarketingType: VerticalMarketingType.load(1),
        itemGroupType: ItemGroupType.findOrSaveByName("PRODUCT"), 
        salesDepartmentType: SalesDepartmentType.load(1), itemType: ItemType.load(1), seller: Seller.build(vertical: vertical)).save()
    def itemGroupProfile = ItemGroupProfile.build(name: "nameTo24", vertical: Vertical.load(1), itemGroup: itemGroupTo, status: ItemStatus.get(1L)).save()

    def itemTo = new Item(priority: 1, attributeName: "atributoName2", attributeLabel: "atributoLabel", attributeValue: "AtributoLAbel",
        status: ItemStatus.load(1), itemGroup: itemGroupTo, attributeType: AttributeType.load(1)).save()
    def sku = Sku.build(sku: "sku22", ean: "ean22", weight: 0, width: 0, virtualCost: 0, item: itemTo, status: ItemStatus.load(1),
        height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, expectedSold: 100)
        .save()
    2.times {
      Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku, type: AttributeType.load(1)).save()
    }
    def skuProfile = new SkuProfile(fullPrice: 10, price: 10, sku: sku, vertical: Vertical.load(1), quantityReserved: 1, quantityOnHand: 5, status: ItemStatusEnum.ACTIVE.domain, itemGroupProfile: itemGroupProfile).save()
    def odc = Odc.build(provider: Provider.load(1), type: OdcType.load(1), status: OdcStatus.load(1), paymentStatus: OdcPaymentStatus.load(1), wmsStatus: WmsStatus.load(1), incomeType: IncomeType.load(1))
    def odcDetail = OdcDetail.build(odc: odc, sku: sku, status: OdcDetailStatus.load(1))
    def history = InOutSkuHistory.build(sku: sku, type: InOutSkuHistoryType.load(1))
    new SkuIncome(cost: 200, stock: 20, available: false, sku: sku, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
    new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile, status: WaitingListItemStatusEnum.AVAILABLE.domain).save(flush: true)

    put('/waiting-list-notifier') {
      headers['Content-Type'] = 'application/json'
      headers['Wb-Api-Token'] = 'W1nb1tsT3st'
      headers['Accept-Language'] = 'es'
      body {
        [skuProfileId: skuProfile.id] as JSON
      }
    }
    assertStatus 200

  }

}
