package com.winbits.domain.catalog

import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.logistics.IncomeTypeEnum
import org.joda.time.DateTime

class ItemGroupProfile {

  String name
  String shortDescription
  String longDescription
  String conditions
  String details
  Integer maxPerOrder = 1
  Integer maxPerUser = -1
  Integer minPerOrder = 1
  DateTime activeStart
  DateTime activeEnd
  ItemStatus status

  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false
  Boolean marketingSale = false

  Vertical vertical
  static belongsTo = [itemGroup: ItemGroup]
  static hasMany = [skuProfiles: SkuProfile]
  BigDecimal cashback = 0
  Integer hoursGenerateFile = 0

  static constraints = {
    maxPerUser       min       : -1
    minPerOrder      min       : 1, validator : { val, obj -> if(!(val <= obj.maxPerUser || obj.maxPerUser == -1)) return ["maxPerUser"] }
    maxPerOrder      min       : 1, validator : { val, obj -> if(!(val <= obj.maxPerUser || obj.maxPerUser == -1)) return ["maxPerUser"] }
    activeEnd        nullable  : true
    activeStart      nullable  : false
    shortDescription size      : 0..256
    hoursGenerateFile nullable  : true, min: 0
  }

  static mapping = {
    maxPerOrder sqlType  : 'mediumint'
    maxPerUser sqlType   : 'mediumint'
    minPerOrder sqlType  : 'mediumint'
    name blank           : false, defaultValue : "''"
    conditions type      : 'text'
    longDescription type : 'text'
    details type         : 'text'
    hoursGenerateFile sqlType  : 'smallint'
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }
}
