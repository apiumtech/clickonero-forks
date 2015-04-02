package com.winbits.domain.orders
 
import com.winbits.bits.secure.User
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.*
 
import org.apache.commons.lang.RandomStringUtils
import grails.plugin.spock.IntegrationSpec
 
import org.joda.time.DateTime

class OrderDRYServiceIntegrationSpec extends IntegrationSpec {
    
    static transactional = false

    OrderDRYService orderDRYService

    def setup() {
    }

    def cleanup() {
    }

    void "check 4 expire order "() {
    setup: "Se crea datos de prueba"
    List skuProfiles = []
    def skuProfile = createItems(20,3).last()
    skuProfiles << skuProfile
    def skuProfile2 = createItems(20,3).last()
    skuProfiles << skuProfile2
    def skuProfile3 = createItems(20,3).last()
    skuProfiles << skuProfile3
    def skuProfile4 = createItems(20,3).last()
    skuProfiles << skuProfile4

    and:"Creando usuario"
    User user = createUserWithRandomEmail()
    and:"Creando orden"
    def listDetails = []
    skuProfiles.each{
       Map skuProfileMap = [quantity: 3, skuProfile: it, status: OrderDetailStatusEnum.CHECKOUT]
       listDetails << skuProfileMap
    }
    Order order = createOrders(listDetails ,user, OrderStatusEnum.CHECKOUT)
    when: "Verificando createOrderDetailWithCartDetail"
    Order orderResult = orderDRYService.expireOrder(order.id)
    then:
    assert orderResult
    assert orderResult.status.enum == OrderStatusEnum.ATTEMPTED
    assert orderResult.orderDetails.size() == 4
    orderResult.orderDetails.each{
        assert it.skuProfile.refresh().quantityReserved == 0 
        assert it.skuProfile.sku.refresh().quantityReserved == 0 
    }

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
        itemGroupType: itemGroupType,
        verticalMarketingType: verticalMarketingType,
        categoryType: categoryType,
        model: 'zxcv',
        salesDepartmentType: salesDepartmentType,
        seller: seller,
        subCategoryType: subCategoryType
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

   def createOrders(List skuProfileWithOrderStatusAndQuantity, 
        User user, OrderStatusEnum orderStatus){
    Order order = new Order(vertical: Vertical.load(1), 
        itemsTotal: 100, 
        shippingTotal: 0, 
        bitsTotal: 0, 
        total: 100, 
        orderNumber: "ASDFQWE", 
        paidDate: new DateTime(), 
        status: orderStatus.domain , 
        user: user, 
        cashTotal: 0)
    
    skuProfileWithOrderStatusAndQuantity.each{ map ->
        OrderDetail orderDetail = new OrderDetail(shippingAmount: 0, 
                cashAmount: 100, 
                bitsAmount: 100, amount: 100, 
                quantity: map.quantity, 
                status: map.status.domain, 
                skuProfile: map.skuProfile)
        order.addToOrderDetails(orderDetail)
    }

    order.save(flush:true)
   }

   User createUserWithRandomEmail(){
       String prefix = RandomStringUtils.randomAlphanumeric(10)
       User user = User.findOrCreateWhere(email:"${prefix}@random.com")
       user.properties = [password:"123", referrerCode:"asdfasdf", enabled:true, vertical:Vertical.get(1)]
       user.generateApiToken()
       user.getSalt()
       user.save(flush:true)
   }




}
