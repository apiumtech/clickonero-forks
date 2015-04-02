package com.winbits.domain.catalog

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

import com.winbits.domain.affiliation.Vertical

@TestFor(ItemGroupProfileService)
@Mock([Item, ItemGroupProfile, Vertical, ItemGroup, Sku, SkuProfile])
class ItemGroupProfileServiceSpec extends Specification {

	void "Getting value from a field in ItemGroupProfile"() {
    setup : "Init domains"
      Vertical vertical = new Vertical()
      vertical.save(validate:false)

      ItemGroup itemGroup = new ItemGroup(vertical:vertical)
      ItemGroupProfile itemGroupProfile = new ItemGroupProfile(vertical:vertical,maxPerUser:10000,shortDescription:"LOL")
      itemGroup.addToItemGroupProfiles(itemGroupProfile)
      itemGroup.save(validate:false)

      Item item = new Item(itemGroup:itemGroup)
      item.save(validate:false)

      Sku sku = new Sku(item:item)

      SkuProfile skuProfile = new SkuProfile(sku:sku, vertical:vertical, itemGroupProfile: itemGroupProfile)
      skuProfile.save(validate:false)

    when :
      def result = service.fieldValueFromItemGroupProfileWhereFieldAndSkuProfile(field, skuProfile)

    then :
      assert result != null
      assert result == value

    where :
      field                || value
        "maxPerUser"       || 10000
        "shortDescription" || "LOL"
	}
}
