import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.*
import com.winbits.domain.orders.OrderDetailStatus
import com.winbits.domain.orders.OrderStatus
import org.apache.commons.lang.RandomStringUtils

testDataConfig {
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
    "${ItemGroupType.name}" {
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

