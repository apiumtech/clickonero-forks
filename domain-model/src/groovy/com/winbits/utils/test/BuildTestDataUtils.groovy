package com.winbits.utils.test

import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.AttributeType
import com.winbits.domain.catalog.CategoryType
import com.winbits.domain.catalog.ItemGroupType
import com.winbits.domain.catalog.ItemStatus
import com.winbits.domain.catalog.ItemType
import com.winbits.domain.catalog.SalesDepartmentType
import com.winbits.domain.catalog.SubCategoryType
import com.winbits.domain.catalog.VerticalMarketingType
import com.winbits.domain.orders.OrderDetailStatus
import com.winbits.domain.orders.OrderStatus
import org.apache.commons.lang.RandomStringUtils

/**
 * Created by winbits on 7/8/14.
 */
class BuildTestDataUtils {

  static Closure getTestDataConfig(ctx) {
    def cls = {
      sampleData {
        "${Vertical.name}" {
          def i = 1
          name = {-> "vertical-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
          baseUrl = {-> "http://www.vertical-${i++}-${RandomStringUtils.randomAlphanumeric(7)}.com" }
        }
        "${CategoryType.name}" {
          def i = 1
          name = {-> "categoryType-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${ItemType.name}" {
          def i = 1
          name = {-> "itemType-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${SalesDepartmentType.name}" {
          def i = 1
          name = {-> "SDT-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${SubCategoryType.name}" {
          def i = 1
          name = {-> "SCT-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${VerticalMarketingType.name}" {
          def i = 1
          name = {-> "VMT-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${ItemGroupType.name}" {
          def i = 1
          name = {-> "itemType-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${AttributeType.name}" {
          def i = 1
          name = {-> "AT-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${ItemStatus.name}" {
          def i = 1
          name = {-> "itemStatus-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${OrderStatus.name}" {
          def i = 1
          name = {-> "orderStatus-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
        "${OrderDetailStatus.name}" {
          def i = 1
          name = {-> "orderDetailStatus-${i++}-${RandomStringUtils.randomAlphanumeric(7)}" }
        }
      }
    }
    cls.delegate = ctx
    cls.resolveStrategy = Closure.DELEGATE_FIRST
    cls
  }
}
