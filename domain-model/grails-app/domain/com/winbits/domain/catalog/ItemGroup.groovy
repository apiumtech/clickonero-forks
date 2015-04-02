package com.winbits.domain.catalog
 
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.logistics.DeliveryDateType
import com.winbits.domain.logistics.DeliveryDateTypeEnum
import com.winbits.domain.logistics.IncomeType
 
import org.joda.time.DateTime
import org.joda.time.Interval

class ItemGroup {

  DateTime activeStart
  DateTime activeEnd
  DateTime dateCreated
  DateTime lastUpdated
  ItemStatus status
  DeliveryDateType deliveryDateType
  Integer deliveryDateDays
  IncomeType incomeType
  ItemGroupType itemGroupType

  Boolean deleted = false
  Boolean requiresShipping = false
  Boolean requireData = false

  String model

  VerticalMarketingType verticalMarketingType
  SalesDepartmentType salesDepartmentType
  CategoryType categoryType 
  SubCategoryType subCategoryType
  ItemType itemType
  Seller seller

  Vertical vertical
  Provider provider
  Brand brand

  String ref
  static transients = ['ref', 'avaliable', 'available', 'defaultProfile']
  static hasMany = [paymentMethodExclusions: PaymentMethod, items: Item, itemGroupProfiles: ItemGroupProfile]

  static constraints = {
    activeEnd nullable: true
//    activeStart validator: {it > DateTime.now()}
    deliveryDateType nullable: true
    deliveryDateDays nullable: true, min: 0
    incomeType       nullable: true
    model            nullable: true
    requireData      nullable: true
  }

  static mapping = {
    paymentMethodExclusions column: 'item_group_id', joinTable: 'payment_method_exclusions'
    itemGroupType defaultValue: '1'
  }

  boolean getAvaliable() {
    (activeStart.beforeNow && (!activeEnd || activeEnd.afterNow))
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

  boolean isAvailable() {
    if (!activeEnd) {
      return true
    }
    def interval = new Interval(activeStart, activeEnd)
    interval.containsNow()
  }

  DateTime obtainDeliveryDate(){
    DateTime deliveryDate = new DateTime()
    if(!deliveryDateDays){
     return deliveryDate
    }
    if(deliveryDateType == DeliveryDateTypeEnum.ACTIVE_END_DATE_RELATIVE){
        if(activeEnd){
            deliveryDate = activeEnd.plusDays(deliveryDateDays)
        }else{
            deliveryDate = deliveryDate.plusDays(deliveryDateDays)
        }
    }else{
            deliveryDate = deliveryDate.plusDays(deliveryDateDays)
    }

    deliveryDate
  }

  ItemGroupProfile getDefaultProfile() {
      ItemGroupProfile.findByItemGroupAndVertical(this, vertical)
  }


  static namedQueries = {
    quantityReservedGtZero {incomeType ->
      eq "incomeType", incomeType
        or {
          gt "activeEnd", DateTime.now()
            isNull "activeEnd"
        }
      items {
        skus {
          or {
            gt "quantityReserved", 0
              skuProfiles {
                gt "quantityReserved", 0
              }
          }
        }
      }
    }

    quantityReservedGtZeroEnded {incomeType ->
      eq "incomeType", incomeType
      between('activeEnd', DateTime.now().minusDays(1).withTimeAtStartOfDay(), DateTime.now().withTimeAtStartOfDay())
      items {
        skus {
          or {
            gt "quantityReserved", 0
              skuProfiles {
                gt "quantityReserved", 0
              }
          }
        }
      }
    }
  }

}
