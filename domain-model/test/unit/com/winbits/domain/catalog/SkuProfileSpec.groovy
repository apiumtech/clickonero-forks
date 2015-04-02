package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.WaitingListItemStatus
import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import com.winbits.domain.utils.DomainModelUtils
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.DateTime
import spock.lang.Specification

@TestFor(SkuProfile)
@Mock([Sku, Vertical, ItemStatus, Item, ItemGroup, ItemGroupProfile, WaitingListItemStatus])
class SkuProfileSpec extends Specification {

  def setup() {
    DomainModelUtils.createPersistentEnumModel(ItemStatusEnum)
    DomainModelUtils.createPersistentEnumModel(WaitingListItemStatusEnum)
  }

  void "Validation of errors"() {
    when: "Create a SkuProfile"
    SkuProfile skuProfile = new SkuProfile(quantityOnHand: -2, quantityReserved: -1)

    then: "The constraints results"
    assert !skuProfile.validate()
    assert skuProfile.errors["fullPrice"].code == "nullable"
    assert skuProfile.errors["price"].code == "nullable"
    assert skuProfile.errors["sku"].code == "nullable"
    assert skuProfile.errors["vertical"].code == "nullable"
    assert skuProfile.errors["quantityOnHand"].code == "min.notmet"
    assert skuProfile.errors["quantityReserved"].code == "min.notmet"
  }

  void "Validation of errors 2"() {
    setup: "Init dependencies"
    Sku sku = new Sku().save(validate: false)
    Vertical vertical = new Vertical().save(validate: false)
    def itemGroupProfile = new ItemGroupProfile().save(validate: false)

    when: "Create a SkuProfile"
    SkuProfile skuProfile = new SkuProfile(fullPrice: 100,
        sku: sku,
        vertical: vertical,
        status: ItemStatusEnum.ACTIVE.domain,
        price: 50,
        quantityOnHand: 10,
        itemGroupProfile: itemGroupProfile)
    skuProfile.save(failOnError: true)

    then: "The constraints results"
    assert skuProfile.id > 0
  }

  void "Validation in WaitingList"() {
    setup: "Init dependencies"
    // mockForConstraintsTests(SkuProfile)
//    mockFor(ItemStatus)

    when: "SkuProfile SOLD OUT"
    def item = new Item().save(id: 10, validate: false)
    def itemGroup = new ItemGroup(id: 20, activeStart: DateTime.now().minusDays(7),
        activeEnd: DateTime.now().plusDays(7)).save(validate: false)
    item.itemGroup = itemGroup
    def sku = new Sku(item: item).save(id: 30, validate: false)
    def skuProfile = new SkuProfile(id: 40, quantityOnHand: 1, quantityReserved: 1, sku: sku, status: ItemStatusEnum.ACTIVE.domain).save(validate: false)
    skuProfile.save(validate: false)

    then: "The constraints results"
    assert skuProfile.getAvaliable().enum == WaitingListItemStatusEnum.SOLD_OUT

    when: "SkuProfile Avaliable"
    skuProfile = new SkuProfile(id: 40, quantityOnHand: 10, quantityReserved: 1, sku: sku).save(validate: false)
    skuProfile.save(validate: false)
    skuProfile.status = ItemStatusEnum.ACTIVE.domain

    then:
    assert skuProfile.getAvaliable().enum == WaitingListItemStatusEnum.AVAILABLE

    when: "SkuProfile Not Avaliable"
    skuProfile.status = ItemStatusEnum.INACTIVE.domain

    then:
    assert skuProfile.getAvaliable().enum == WaitingListItemStatusEnum.NOT_AVAILABLE


  }

}
