package com.winbits.domain.catalog

class ItemGroupProfileService {

  def fieldValueFromItemGroupProfileWhereFieldAndSkuProfile(String field, SkuProfile skuProfile) {
    ItemGroupProfile itemGroupProfile = skuProfile.itemGroupProfile
    itemGroupProfile."$field"
  }

}
