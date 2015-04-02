package com.winbits.orders.utils

import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.catalog.Attribute
import com.winbits.domain.catalog.ZipCodeInfo
import com.winbits.domain.orders.OrderPayment
import com.winbits.domain.orders.RefundDetail
import com.winbits.domain.orders.ShippingOrder
import org.joda.time.DateTime
import com.winbits.domain.orders.OrderDetailStatus
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.orders.OrderStatus
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.orders.Order
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.CategoryType
import com.winbits.domain.catalog.ItemType
import com.winbits.domain.catalog.SalesDepartmentType
import com.winbits.domain.catalog.SubCategoryType
import com.winbits.domain.catalog.VerticalMarketingType
import com.winbits.domain.logistics.IncomeType
import com.winbits.domain.catalog.Seller
import com.winbits.domain.catalog.Provider
import com.winbits.domain.catalog.ItemStatus
import com.winbits.domain.catalog.Brand
import com.winbits.domain.catalog.ItemGroup
import com.winbits.domain.catalog.ItemGroupProfile
import com.winbits.domain.catalog.AttributeType
import com.winbits.domain.catalog.ItemGroupType
import com.winbits.domain.catalog.AttributeTypeEnum
import com.winbits.domain.catalog.Item
import com.winbits.domain.catalog.Sku

/**
 * Created with IntelliJ IDEA.
 * User: manolo
 * Date: 10/13/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
def static createOrderPayment(order, status, amount, paymentMethod) {
    OrderPayment orderPayment = new OrderPayment()
    orderPayment.amount = amount
    orderPayment.paidDate = DateTime.now().minusDays(5)
    orderPayment.status = status
    orderPayment.order = order
    orderPayment.paymentMethod = paymentMethod
    orderPayment.save(validate: false)
}

def static createOrder(skuProfile) {
    DateTime week = new DateTime(new Date() - 10)
    OrderDetail orderDetailCheckout = createOrderDetails(skuProfile)
    OrderStatus checkoutOrderStatus = OrderStatusEnum.PENDING.domain
    Order order = new Order(itemsTotal:100,shippingTotal:0,bitsTotal:0,total:100,orderNumber:"ASDFQWE",paidDate:week,status:checkoutOrderStatus,user:User.get(1), cashTotal:100, vertical: Vertical.load(1) )
    order.addToOrderDetails(orderDetailCheckout)
    order.save(failOnError:true)
}

def static createOrderDetails(skuProfile) {
    OrderDetailStatus orderDetailStatus = OrderDetailStatusEnum.CHECKOUT.domain
    new OrderDetail(shippingAmount:0, cashAmount:100,
            bitsAmount:100, amount:100, quantity:10, status: orderDetailStatus, skuProfile: skuProfile )
}

def static createSkuProfile() {

    def categoryType = CategoryType.findOrSaveByName('KIDS_ACCESORIES')
    def itemType = ItemType.findOrSaveByName('BOOTS')
    def salesDepartmentType = SalesDepartmentType.findOrSaveByName('MALE')
    def subCategoryType = SubCategoryType.findOrSaveByName('SHOES')
    def verticalMarketingType = VerticalMarketingType.findOrSaveByName('SPORTS')
    def incomeType = IncomeType.findOrSaveByName('VIRTUAL')
    def itemGroupType = ItemGroupType.findOrSaveByName('PRODUCT')

    Vertical vertical = Vertical.load(1)
    vertical.maxPerVertical = 1000.00
    vertical.save()
    def seller = Seller.findOrSaveWhere(name: 'seller1', vertical: vertical)
    User user = User.findOrCreateWhere(email:"a@b.com")
    if (!user.id) {
        user.properties = [password:"123", referrerCode:"asdfasdf", enabled:true, vertical:vertical]
        user.generateApiToken()
        user.getSalt()
        user.save()
    }

    Provider provider = new Provider(name:"provider1",url:"http://www.google.com",vertical:vertical)
    provider.save()

    ItemStatus itemStatus = ItemStatus.get(1L)

    Brand brand = new Brand(name:"Brand",deleted:false,logo:"http://www.google.com",description:"description",vertical:vertical)
    brand.save()

    DateTime twoDaysAgo = new DateTime(new Date() - 2)
    DateTime afterWeek = new DateTime(new Date() + 7)
    ItemGroup itemGroup = new ItemGroup(activeStart:twoDaysAgo,
            activeEnd:afterWeek,
            deleted:false,
            requiresShipping:true,
            status:itemStatus,
            vertical:vertical,
            provider:provider,
            brand:brand,
            itemGroupType: itemGroupType,
            incomeType: incomeType,
            categoryType: categoryType,
            subCategoryType: subCategoryType, verticalMarketingType: verticalMarketingType, model: 'model',
            salesDepartmentType: salesDepartmentType, itemType: itemType, seller: seller )
    ItemGroupProfile itemGroupProfile = new ItemGroupProfile(name:"ItemGroupProfile",
            shortDescription: 'shortDescription',
            longDescription: 'longDescription',
            conditions: 'conditions',
            details: 'details',
            activeStart: new DateTime(),
            maxPerOrder:100,
            minPerOrder:1,
            maxPerUser:100,
            vertical:vertical,
            status: itemStatus)
    itemGroup.addToItemGroupProfiles(itemGroupProfile)
    itemGroup.save()

    AttributeType attributeType = AttributeTypeEnum.TEXT.domain

    Item item = new Item(priority:1,
            attributeName:"attributeName",
            attributeLabel:"attributeLabel",
            attributeValue:"attributeValue",
            status:itemStatus,
            deleted:false,
            itemGroup:itemGroup,
            attributeType:attributeType)
    item.save()

    Sku sku1 = new Sku(cost:10.00,
            quantityOnHand:100,
            quantityReserved:0,
            width:10.0,
            height:10.0,
            length:10.0,
            weight:10.0,
            ean:"123123",
            sku:"123123",
            status:itemStatus,
            deleted:false,
            item:item,
            priority:1,
            expectedSold: 100)
    sku1.save()
    def attribute = new Attribute(sku: sku1, name: 'talla', value: 'mediana', label: 'talla', type: AttributeType.get(1)).save()
    sku1.attributes = [] as Set
    sku1.attributes.add(attribute)

    Sku sku2 = new Sku(cost:10.00,
            quantityOnHand:100,
            quantityReserved:0,
            width:10.0,
            height:10.0,
            length:10.0,
            weight:10.0,
            ean:"123123",
            sku:"123123",
            status:itemStatus,
            deleted:false,
            item:item,
            priority:1,
            expectedSold: 100)
    sku2.save()

    Sku sku3 = new Sku(cost:10.00,
            quantityOnHand:100,
            quantityReserved:0,
            width:10.0,
            height:10.0,
            length:10.0,
            weight:10.0,
            ean:"123123",
            sku:"123123",
            status:itemStatus,
            deleted:false,
            item:item,
            priority:1,
            expectedSold: 100)
    sku3.save()

    SkuProfile skuProfile1 = new SkuProfile(fullPrice:100,status:itemStatus,price:10,vertical:vertical,sku:sku1,itemGroupProfile: itemGroupProfile)
    skuProfile1.save()
}

static def createShippingOrder(Order order) {
    def shippingOrder = ShippingOrder.findByOrder(order)
    if (!shippingOrder) {
      def commonAddress = new CommonAddress(street: 'street', phone: '1234567890', county: 'Xochimilco', internalNumber: '11',
          externalNumber: '123', zipCode: '16050', location: 'location1', state: 'DF')
      shippingOrder = new ShippingOrder(order: order, firstName: 'firstName1', lastName: 'lastName1',
          indications: 'indications1', betweenStreets: 'betweenStreets1', zipCodeInfo: ZipCodeInfo.get(1),
          commonAddress: commonAddress).save()
    }
    shippingOrder
}

def static createOrderGeneric(Map map) {
  map.order.paidDate = new DateTime(new Date() - 10)
  Order order = new Order(map.order)
  map.orderDetails.each{it->
    order.addToOrderDetails createOrderDetailsGeneric(it)
  }
  order.save(failOnError:true)
}

def static createOrderDetailsGeneric(Map map) {
  new OrderDetail(map)
}

def static createRefundDetail(Map map){
  new RefundDetail(map)
}
