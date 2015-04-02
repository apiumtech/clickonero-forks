package com.winbits.domain.catalog

import com.winbits.domain.logistics.SkuBalance
import org.joda.time.DateTime

class Sku {
  Integer quantityOnHand
  Integer quantityReserved = 0
  Integer expectedSold
  BigDecimal width
  BigDecimal height
  BigDecimal length
  BigDecimal weight
  String ean
  String sku
  String supplierExtra
  /** Publication Status */
  ItemStatus status
  DateTime dateCreated
  DateTime lastUpdated
  Boolean deleted = false
  BigDecimal virtualCost
  Integer priority

  String ref
  static transients = ['ref', 'avaliable', 'available']
  static hasMany = [attributes: Attribute, skuProfiles: SkuProfile, skuBalances: SkuBalance]
  static belongsTo = [item: Item]

  static constraints = {
    virtualCost nullable: true, min: 0.0
    weight nullable: true
    length nullable: true
    width nullable: true
    height nullable: true
    ean nullable: true
    supplierExtra nullable: true, size:0..500
    skuBalances nullable: true
  }

  static mapping = {
    supplierExtra type: 'text'
  }

  boolean getAvaliable() {
    //this.item.

        //(activeStart.beforeNow && (!activeEnd || activeEnd.afterNow))


  }

  boolean isActive(){
    def active
    this.item.itemGroup.activeStart 
    active =  status.enum == ItemStatusEnum.INACTIVE
    active = active || (status.enum == ItemStatusEnum.SOLD_OUT && 
      item.itemGroup.activeStart > DateTime.now())

    active
  }

  boolean isActiveEnd(){
    def activeEnd=false
    if(this.item.itemGroup.activeEnd < DateTime.now()){
      activeEnd=true
    }
    activeEnd
  }


  def afterInsert() {
    if (!ean){
        ean = id
    }
    if (!sku){
        sku = id
    }
  }

  static hibernateFilters = {
    enabledFilter(condition: 'deleted=0', default: true)
  }

  boolean isAvailable() {
    item.isAvailable()
  }

  String joinedAttributes(String separator = ', ') {
    def mainAttribute = new Attribute(name: item.attributeName, label: item.attributeLabel)
    mainAttribute.discard()
    def allAttributes = [mainAttribute]
    if (attributes) {
      allAttributes.addAll(attributes)
    }
    def mapAttribute= []
    allAttributes.each {
      if(it.type != AttributeTypeEnum.HIDDEN)
         mapAttribute.add("${it.name}: ${it.label}")
    }
    mapAttribute.join(separator)
  }
}
