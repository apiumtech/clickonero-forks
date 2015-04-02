package com.winbits.orders
 
import com.winbits.bits.secure.User
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.IncomeType
import com.winbits.domain.logistics.IncomeTypeEnum
 
import grails.converters.*
import grails.plugin.spock.IntegrationSpec
 
import groovyx.gpars.*
 
import org.joda.time.DateTime
 
import spock.lang.IgnoreRest

class ActorStockServiceIntegrationSpec extends IntegrationSpec {
    def actorStockService
    def stockService
    def lista = [0,1,2,3,4,5,6,7,8,9, 10, 11, 12 ,13, 14, 15 ,16, 17, 18]

    static transactional = false

    def setup() {
    }

    def cleanup() {
    }
    
    void "actiiiiiiiiiiiiiiing 4 sku"() {
    setup:
    def sku = createItems(15,0).first()
    when:
    def message = [skuId: sku.id, quantity: 2]
    
    GParsPool.withPool {
          lista.collectParallel { 
            def reply = actorStockService.reservedStock4Sku.sendAndWait(message)
          }
    }
    
    then:
        sku.refresh().quantityReserved == 15
    }

    void "not acting"(){
    setup:
    def sku = createItems(15,0).first()
    when:
    GParsPool.withPool {
          lista.collectParallel { 
            stockService.reserveStockForSkuAndQuantity(sku.id,1)
          }
    }
    then:
    sku.refresh().quantityReserved == 15
    }

    void "4 my next trick i'll gonna act with sku profile"(){
    setup:
    def skuProfile = createItems(15,0).last()
    when:
    def message = [skuProfileId: skuProfile.id, quantity: 2]
    GParsPool.withPool {
          lista.collectParallel { 
            actorStockService.reservedStock4SkuProfile.sendAndWait message
          }
    }
    then:
    skuProfile.refresh().quantityReserved == 38
    }
    
    void "not acting 4 sku profile"(){
    setup:
    def skuProfile = createItems(15,0).last()
    when:
    GParsPool.withPool {
          lista.collectParallel { 
            stockService.reservingSkuProfileStockForQuantity(skuProfile.id, 1)
             
          }
    }
    then:
    skuProfile.refresh().quantityReserved == 19
    }

    void "not acting 4 release stock 4 sku"(){
    setup:
    def sku = createItems(20,20).first()
    when:
    GParsPool.withPool {
          lista.collectParallel { 
            stockService.releaseStockForSkuWithQuantity(sku.id, 1)
          }
    }
    then:
    sku.refresh().refresh().quantityReserved == 1
    }

    void "acting 4 release stock 4 sku"(){
    setup:
    def sku = createItems(20,20).first()
    when:
    def message = [skuId: sku.id, quantity: 1]
    GParsPool.withPool {
          lista.collectParallel { 
            actorStockService.releaseStock4Sku.sendAndWait message
          }
    }
    then:
    sku.refresh().refresh().quantityReserved == 1
    }

    void "not acting 4 release sku profile"(){
    setup:
    def skuProfile = createItems(20,20).last()
    when:
    GParsPool.withPool {
          lista.collectParallel { 
            stockService.releaseStockForSkuProfileWithQuantity(skuProfile.id, 1)
             
          }
    }
    then:
    skuProfile.refresh().quantityReserved == 1
    }
    
    void "acting 4 release sku profile"(){
    setup:
    def skuProfile = createItems(20,20).last()
    and:
    def message = [skuProfileId: skuProfile.id, quantity: 1]
    when:
    GParsPool.withPool {
          lista.collectParallel { 
             actorStockService.releaseStock4SkuProfile.sendAndWait message
          }
    }
    then:
    skuProfile.refresh().quantityReserved == 1
    }

    private def createItems(int quantityOnHand, int quantityReserved){
        ItemStatus itemStatus = ItemStatus.get(1L)
        IncomeType incomeType = IncomeType.get(1L)

        def status = ItemStatusEnum.ACTIVE
        ItemGroup itemGroup = createItemGroup(status)
        ItemGroupProfile itemGroupProfile = createItemGroupProfile(status, itemGroup)
        relationItemGroupToItemGroupProfile(itemGroup, itemGroupProfile)
        Item item = createItemByItemGroup(status, itemGroup)
        def sku = createSku(quantityOnHand, quantityReserved, status, item)
        def skuProfile = createSkuProfiles(item, sku, itemGroupProfile, status, quantityOnHand,
            quantityReserved)
        [sku, skuProfile]
    }

  private def createItemGroup(ItemStatusEnum itemStatus){
        Brand brand = new Brand(name:"Brand",deleted:false,logo:"http://www.google.com",description:"description",vertical:Vertical.get(1))
        brand.save()
        Provider provider = new Provider(name:"provider1",url:"http://www.google.com",vertical:Vertical.get(1))
        provider.save()
        def itemType = ItemType.findOrSaveWhere(name: 'itemType1')
        def itemGroupType = ItemGroupType.findOrSaveWhere(name: 'itemGroup1')
        def verticalMarketingType = VerticalMarketingType.findOrSaveWhere(name: 'verticalMarketingType1')
        def categoryType = CategoryType.findOrSaveWhere(name: 'categoryType1')
        def salesDepartmentType = SalesDepartmentType.findOrSaveWhere(name: 'salesDepartmentType1')
        def subCategoryType = SubCategoryType.findOrSaveWhere(name: 'subCategoryType1')
        def seller = Seller.findOrSaveWhere(name: 'vendedor1', vertical: Vertical.load(1))

    def itemGroup = ItemGroup.findOrSaveWhere(
        activeStart: new DateTime(),
        dateCreated: new DateTime(),
        lastUpdated: new DateTime(),
        status: itemStatus.domain,
        requiresShipping: false,
        vertical: Vertical.get(1),
        provider: provider,
        brand: brand,
        incomeType: IncomeTypeEnum.FIRM.domain,
        itemType: itemType,
        verticalMarketingType: verticalMarketingType,
        categoryType: categoryType,
        model: 'zxcv',
        salesDepartmentType: salesDepartmentType,
        seller: seller,
        subCategoryType: subCategoryType,
        itemGroupType: itemGroupType
    )
    itemGroup.save(failOnError:true)
  }

  private def createItemGroupProfile(ItemStatusEnum itemStatus, ItemGroup itemGroup){
      def itemGroupProfile = ItemGroupProfile.findOrSaveWhere(name: "naim" , 
              status: itemStatus.domain, 
              shortDescription: "shortDescription", 
              longDescription: "longDescription", 
              conditions: "conditions",
              details: "details", 
              maxPerOrder: 10, 
              maxPerUser: 100, 
              minPerOrder: 1, 
              vertical: Vertical.get(1), 
              activeStart: DateTime.now(), 
              activeEnd: DateTime.now().plusDays(5),
              itemGroup: itemGroup
              )
    itemGroupProfile.save(failOnError:true)

  }

  private def relationItemGroupToItemGroupProfile(ItemGroup itemGroup, ItemGroupProfile itemGroupProfile){
    itemGroup.addToItemGroupProfiles(itemGroupProfile)
    itemGroup
  }

  private def createItemByItemGroup(ItemStatusEnum itemStatus, ItemGroup itemGroup){
      IncomeType incomeType = IncomeType.get(1L)
          def seller = Seller.findOrSaveWhere(name: "Vendedor de pruebas", 
                  vertical: Vertical.get(1))

          def item =  Item.findOrSaveWhere(priority: 1, 
                  attributeName: "atributoName", 
                  attributeLabel: "atributoLabel", 
                  attributeValue: "AtributoLAbel",
                  status: itemStatus.domain, 
                  itemGroup: itemGroup,
                  attributeType: AttributeTypeEnum.TEXT.domain)
        item.save()
  }

  private def createSku(int quantityOnHand, int quantityReserved,
          ItemStatusEnum status, Item item){
      def sku =Sku.findOrSaveWhere(priority: 1, 
              sku: "sku", 
              ean: "ean", 
              weight: 0.0, 
              width: 0.0, 
              item: item, 
              status: status.domain,
              height: 0.0,
              quantityOnHand: quantityOnHand, 
              quantityReserved: quantityReserved, 
              length: 0.0, 
              deleted: false, 
              expectedSold: 0)
    sku.save()
  }

  private def createSkuProfiles(Item item, Sku sku, 
        ItemGroupProfile itemGroupProfile, ItemStatusEnum status,
        int quantityOnHand, int quantityReserved){
      def skuProfile = SkuProfile.findOrSaveWhere(fullPrice: 10.0, 
              price: 10.0, 
              sku: sku, 
              quantityOnHand: quantityOnHand, 
              quantityReserved: quantityReserved, 
              vertical: Vertical.build(), 
              itemGroupProfile: itemGroupProfile,
              status: status.domain)
    skuProfile.save()

  }

  private User obtainUser() {
      User.metaClass.encodePassword = {-> }
      User.build(password: "123", enabled: true, profile: obtainProfile())

  }

  private Profile obtainProfile(){
      Profile profile = new Profile(name: "naim", lastName: "apellido", bitsId: 1L)
      profile.save(validate: false)
  }


}
