import com.winbits.domain.Configuration
import com.winbits.domain.DomainDefaultsService
import com.winbits.domain.affiliation.*
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.*
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderDetail
import com.winbits.domain.orders.OrderDetailStatusEnum
import com.winbits.domain.orders.OrderPayment
import grails.converters.JSON
import grails.util.Environment
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.joda.time.DateTime

class BootStrap {

  def grailsApplication
  def authenticationService
  def bitsClient
  def bitsClientAuthorization

  def init = { servletContext ->

    if (!Environment.current.name.toLowerCase() in ['test']) {
      bitsClient.bitsRestClient.authorization = bitsClientAuthorization
    }
    
    def itemStatus = ItemStatusEnum.ACTIVE.domain
    def vertical = Vertical.load(1L)
    SpringSecurityUtils.clientRegisterFilter('apiAuthenticationFilter', SecurityFilterPosition.FIRST)

    createDefaulConfig()

    def application = grailsApplication
    application.domainClasses.each { dc ->
      dc.metaClass.softDelete << {->
        delegate.deleted = true
        delegate.save()
      }

      dc.metaClass.addWarning << { field, message ->
        if (!delegate.hasProperty("warnings")) {
          delegate.metaClass.warnings = []
        }

        delegate.warnings << [field: field, message: message]
      }
    }

    if (Environment.current.name.toLowerCase() in ['test', 'ci']) {

      authenticationService.generateTestToken()
      setupTestUserByEmail(DomainDefaultsService.CS_REVIEW_USER_EMAIL)
      setupTestUserByEmail(DomainDefaultsService.CS_REJECT_USER_EMAIL)
      authenticationService.createCRCintoRedisTest()

      CategoryType.build(name: 'KIDS_ACCESORIES1')
      ItemType.build(name: 'BOOTS1')
      SalesDepartmentType.build(name: 'MALE1')
      SubCategoryType.build(name: 'SHOES1')
      VerticalMarketingType.build(name: 'SPORTS1')
      IncomeType.build(name: 'VIRTUAL1')

      def user = User.findOrCreateWhere(
          email: 'juan_jsr@yahoo.com',
          vertical: vertical,
          apiToken: "ABC",
          facebookToken: "XYZ",
          referrerCode: "ABCD",
          enabled: true
      )
      if (!user.id) {
        user.password = '12345'
        user.save()
        new Profile(bitsId: -3, locale: new Locale("es", "MX"), user: user).save()
      }
      if (!ZipCodeInfo.count()) {
        ZipCodeInfo.build(
            city: "Ciudad de México",
            cityCode: "09",
            county: "Miguel Hidalgo",
            countyCode: "13",
            locationCode: "000000",
            locationType: "Colonia",
            state: "Distrito Federal",
            stateCode: "09",
            zipCode: "55130",
            version: 1).save(flush: true)
      }
      setupTestUserByEmail user.email

      if (!SocialProvider.count) {
        new SocialProvider(name: 'Facebook', logo: 'facebook.png', providerId: 'facebook').save()
        new SocialProvider(name: 'Twitter', logo: 'twitter.png', providerId: 'twitter').save()
      }

      if (!BillingType.count()) {
        new BillingType(name: "FACTURABLE", description: "prueba de tipo").save()
      }

      if (!Bank.count()) {
        new Bank(name: "HSBC").save(flush: true)
      }

      if (!ProviderContactType.count()) {
        new ProviderContactType(name: "MAIN", description: "el principal").save()
        new ProviderContactType(name: "BILLING", description: "el que necesita facturas").save()
      }

      if (!Vertical.count()) {
        new Vertical(name: "Primera vertical", baseUrl: "wwww.kdkdkd.com").save()

      }
      if (!Brand.count()) {
        new Brand(name: "PATITO BRAND", description: "marca patito", vertical: vertical).save()

      }

      if (!ItemStatus.count()) {
        new ItemStatus(name: "ACTIVE", description: "item group activo").save()
      }

      if (!Brand.count()) {
        new Brand(name: "PATITO BRAND", description: "marca patito", vertical: vertical).save()
        new Brand(name: "ACME BRAND", description: "marca patito", vertical: vertical, deleted: true).save()

      }


      if (!Provider.count()) {
        new Provider(name: "proveedor patito", vertical: vertical).save()
        new Provider(name: "provvedor1", vertical: vertical).save()

      }

      if (!ItemGroup.count()) {
        2.times {
          def seller = Seller.build(vertical: vertical)
          def itemGroup = new ItemGroup(activeStart: new DateTime(), activeEnd: null, dateCreated: new DateTime(),
              lastUpdated: new DateTime(), status: itemStatus, requiresShipping: false, vertical: vertical,
              provider: Provider.load(1), brand: Brand.load(1), incomeType: IncomeType.get(1L), categoryType: CategoryType.load(1),
              subCategoryType: SubCategoryType.load(1), verticalMarketingType: VerticalMarketingType.load(1), model: 'model',
              itemGroupType: ItemGroupType.findOrSaveByName("PRODUCT"), 
              salesDepartmentType: SalesDepartmentType.load(1), itemType: ItemType.load(1), seller: seller).save()

          ItemGroupProfile.build(name: "name" + it, shortDescription: "shortDescription${it}", longDescription: "longDescription${it}", conditions: "conditions",
              details: "details${it}", maxPerOrder: 10, maxPerUser: 10, minPerOrder: 5, vertical: vertical, itemGroup: itemGroup,
              status: itemStatus)


        }
      }
      if (!AttributeType.count()) {
        new AttributeType(name: "name", description: "description").save()
      }
      if (Item.count() < 20) {
        new Item(priority: 1, attributeName: "atributoName", attributeLabel: "atributoLabel", attributeValue: "AtributoLAbel",
            status: itemStatus, itemGroup: ItemGroup.load(1), attributeType: AttributeTypeEnum.TEXT.domain).save()

        new Item(priority: 1, attributeName: "atributoName2", attributeLabel: "atributoLabel", attributeValue: "AtributoLAbel",
            status: itemStatus, itemGroup: ItemGroup.get(2), attributeType: AttributeTypeEnum.TEXT.domain).save()


      }
      def itemGroupProfile = ItemGroupProfile.get(1L)
      if (!Sku.count()) {
        10.times {
          Sku sku1 = new Sku(sku: "sku" + it, ean: "ean" + it, weight: 0, width: 0, cost: 0, item: Item.load(1), status: itemStatus,
              height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
              .save()


          3.times {
            def attribute1 = Attribute.findOrSaveWhere(name: "attribute${it}", label: "label${it}", value: "value${it}", type: AttributeTypeEnum.TEXT.domain, sku: sku1).save()
            sku1.addToAttributes(attribute1)
            sku1.save()
          }
          def odc = Odc.build(provider: Provider.load(1), type: OdcType.load(1), status: OdcStatus.load(1), paymentStatus: OdcPaymentStatus.load(1), wmsStatus: WmsStatus.load(1), incomeType: IncomeType.load(1))
          def odcDetail = OdcDetail.build(odc: odc, sku: sku1, status: OdcDetailStatus.load(1))
          def history = InOutSkuHistory.build(sku: sku1, type: InOutSkuHistoryType.load(1))
          new SkuProfile(fullPrice: 10, price: 10, sku: sku1, vertical: vertical, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
          new SkuIncome(cost: 100, stock: 10, available: true, sku: sku1, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()

          def sku2 = new Sku(sku: "sku" + it, ean: "ean" + it, weight: 0, width: 0, cost: 0, item: Item.get(2), status: itemStatus,
              height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
              .save()
          2.times {
            Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku2, type: AttributeTypeEnum.TEXT.domain).save()
          }
          new SkuProfile(fullPrice: 10, price: 10, sku: sku2, vertical: vertical, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
          new SkuIncome(cost: 200, stock: 20, available: false, sku: sku2, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
        }

        def sku21 = new Sku(sku: "sku21", ean: "ean21", weight: 0, width: 0, cost: 0, item: Item.get(2), status: itemStatus,
            height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
            .save()
        2.times {
          Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku21, type: AttributeTypeEnum.TEXT.domain).save()
        }
        def odc = Odc.build(provider: Provider.load(1), type: OdcType.load(1), status: OdcStatus.load(1), paymentStatus: OdcPaymentStatus.load(1), wmsStatus: WmsStatus.load(1), incomeType: IncomeType.load(1))
        def odcDetail = OdcDetail.build(odc: odc, sku: sku21, status: OdcDetailStatus.load(1))
        def history = InOutSkuHistory.build(sku: sku21, type: InOutSkuHistoryType.load(1))
        new SkuProfile(fullPrice: 10, price: 10, sku: sku21, vertical: vertical, quantityReserved: 1, quantityOnHand: 1, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
        new SkuIncome(cost: 200, stock: 20, available: false, sku: sku21, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()

        def sku22 = new Sku(sku: "sku22", ean: "ean22", weight: 0, width: 0, cost: 0, item: Item.get(2), status: itemStatus,
            height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
            .save()
        2.times {
          Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku22, type: AttributeTypeEnum.TEXT.domain).save()
        }
        def skuProfile22 = new SkuProfile(fullPrice: 10, price: 10, sku: sku22, vertical: vertical, quantityReserved: 1, quantityOnHand: 1, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
        new SkuIncome(cost: 200, stock: 20, available: false, sku: sku22, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
        new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile22).save(flush: true)

        def sku23 = new Sku(sku: "sku22", ean: "ean22", weight: 0, width: 0, cost: 0, item: Item.get(2), status: itemStatus,
            height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
            .save()
        2.times {
          Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku23, type: AttributeTypeEnum.TEXT.domain).save()
        }

        def skuProfile23 = new SkuProfile(fullPrice: 10, price: 10, sku: sku23, vertical: vertical, quantityReserved: 1, quantityOnHand: 1, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
        new SkuIncome(cost: 200, stock: 20, available: false, sku: sku23, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
        new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile23).save(flush: true)

        def sku24 = new Sku(sku: "sku22", ean: "ean22", weight: 0, width: 0, cost: 0, item: Item.get(2), status: itemStatus,
            height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
            .save()
        2.times {
          Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku24, type: AttributeTypeEnum.TEXT.domain).save()
        }

        def skuProfile24 = new SkuProfile(fullPrice: 10, price: 10, sku: sku24, vertical: vertical, quantityReserved: 1, quantityOnHand: 1, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
        new SkuIncome(cost: 200, stock: 20, available: false, sku: sku24, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
        new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile24, status: WaitingListItemStatusEnum.AVAILABLE.domain).save(flush: true)

        def itemGroupTo25 = new ItemGroup(name: "nameTo24", maxPerUser: 10, maxPerOrder: 10, minPerOrder: 5, activeStart: new DateTime().minusDays(1), dateCreated: new DateTime(),
            lastUpdated: new DateTime(), status: itemStatus, requireShipping: false, vertical: vertical, incomeType: IncomeTypeEnum.VIRTUAL.domain,
            provider: Provider.load(1), brand: Brand.load(1), categoryType: CategoryType.load(1), model: 'model',
            subCategoryType: SubCategoryType.load(1), verticalMarketingType: VerticalMarketingType.load(1),
            itemGroupType: ItemGroupType.findOrSaveByName("PRODUCT"), 

            salesDepartmentType: SalesDepartmentType.load(1), itemType: ItemType.load(1), seller: Seller.build(vertical: vertical)).save()

        def itemTo25 = new Item(priority: 1, attributeName: "atributoName2", attributeLabel: "atributoLabel", attributeValue: "AtributoLAbel",
            status: itemStatus, itemGroup: itemGroupTo25, attributeType: AttributeTypeEnum.TEXT.domain).save()

        def sku25 = new Sku(sku: "sku22", ean: "ean22", weight: 0, width: 0, cost: 0, item: itemTo25, status: itemStatus,
            height: 0, quantityOnHand: 0, quantityReserved: 0, length: 0, deleted: false, priority: 0, expectedSold: 100)
            .save()
        2.times {
          Attribute.findOrSaveWhere(name: 'attribute1', label: 'label1', value: 'value1', sku: sku25, type: AttributeTypeEnum.TEXT.domain).save()
        }

        def skuProfile25 = new SkuProfile(fullPrice: 10, price: 10, sku: sku25, vertical: vertical, quantityReserved: 1, quantityOnHand: 5, status: itemStatus, itemGroupProfile: itemGroupProfile).save()
        new SkuIncome(cost: 200, stock: 20, available: false, sku: sku25, incomeType: IncomeType.load(1), odcDetail: odcDetail, history: history).save()
        new WaitingListItem(user: User.findByApiToken("W1nb1tsT3st"), skuProfile: skuProfile25, status: WaitingListItemStatusEnum.AVAILABLE.domain).save(flush: true)


      }

    }

    JSON.registerObjectMarshaller(ZipCodeInfo) { ZipCodeInfo zipCodeInfo ->
      [
          id: zipCodeInfo.id,
          locationName: zipCodeInfo.location,
          locationCode: zipCodeInfo.locationCode,
          locationType: zipCodeInfo.locationType,
          county: zipCodeInfo.county,
          city: zipCodeInfo.city,
          state: zipCodeInfo.state,
          zipCode: zipCodeInfo.zipCode
      ]
    }

    JSON.registerObjectMarshaller(Attribute) { Attribute attribute ->
      [
          name: attribute.name,
          label: attribute.label,
          value: attribute.value,
          type: attribute.type.enum
      ]
    }

    JSON.registerObjectMarshaller(Image) { Image image ->
      [
          url: image.url,
          type: image.imageType.name
      ]
    }

    JSON.registerObjectMarshaller(WishListItem) { WishListItem item ->
      [
          id: item.brand.id,
          dateAdded: item.dateCreated,
          name: item.brand.name,
          logo: item.brand.logo
      ]
    }

    JSON.registerObjectMarshaller(ShippingAddress) { ShippingAddress addr ->
      addr.properties.subMap(['id', 'firstName', 'lastName', 'betweenStreets', 'indications', 'main', 'zipCodeInfo',
          'zipCode', 'location', 'county', 'state', 'lastName2']) +
          addr.commonAddress.properties.subMap(['street', 'internalNumber', 'externalNumber', 'phone'])
    }

    JSON.registerObjectMarshaller(OrderDetail) { OrderDetail orderDetail ->
      [
          orderDetailId: orderDetail.id,
          name: orderDetail.skuProfile.itemGroupProfile.name,
          brand: orderDetail.skuProfile.itemGroupProfile.itemGroup.brand.name,
          amount: orderDetail.amount,
          quantity: orderDetail.quantity,
          deliveryStatus: orderDetail.hasProperty('statusOutcome')? orderDetail.statusOutcome : [],
          tracingNumbers: orderDetail.hasProperty('trackingGuide')? orderDetail.trackingGuide : [],
          vertical: orderDetail.skuProfile.vertical,
          attributeLabel: orderDetail.skuProfile.sku.item.attributeLabel,
          attributeName: orderDetail.skuProfile.sku.item.attributeName,
          attributeValue: orderDetail.skuProfile.sku.item.attributeValue,
          attributeType: orderDetail.skuProfile.sku.item.attributeType.enum,
          attributes: orderDetail.skuProfile.sku.attributes,
          thumbnail: orderDetail.skuProfile.sku.item.thumbnail(),
          estimatedDeliveryDate: orderDetail.estimatedDeliveryDate,
          itemGroupType: orderDetail.skuProfile.itemGroupProfile.itemGroup.itemGroupType.name,
          withCoupon: orderDetail.withCoupon,
          shortDescription: orderDetail.skuProfile.itemGroupProfile.shortDescription,
          requiresShipping: orderDetail.skuProfile.itemGroupProfile.itemGroup.requiresShipping,
          refundedDetail: orderDetail.refundedDetail
      ]
    }

    JSON.registerObjectMarshaller(Order) { Order order ->
      [
          orderNumber: order.orderNumber,
          dateOfPurchase: order.dateCreated,
          total: order.total,
          status: order.status.enum,
          estimatedDeliveryDate: order.estimatedDeliveryDate,
          details: order.details,
          ticketPayments: order.ticketPayments
      ]
    }

    JSON.registerObjectMarshaller(OrderPayment) { OrderPayment payment ->
      [
          identifier: payment.paymentMethod.identifier,
          paymentCapture: payment.paymentCapture
      ]
    }

    JSON.registerObjectMarshaller(SocialProvider) { SocialProvider socialProvider ->
      [
          name: socialProvider.name,
          providerId: socialProvider.providerId,
          logo: socialProvider.logo,
          available: socialProvider.active
      ]
    }

    JSON.registerObjectMarshaller(VerticalPartner) { VerticalPartner partner ->
      [
          verticalId: partner.vertical.id,
          partnerName: partner.vertical.name,
          logo: partner.vertical.logo
      ]
    }


  }

  private createDefaulConfig() {
    def completeRegisterCashbackAmount = grailsApplication.config.winbits.config.promo.completeRegisterCashbackAmount
    DomainDefaultsService.findOrSaveDomainWhere(Configuration,
        [code: "winbits.config.promo.completeRegisterCashbackAmount", value: completeRegisterCashbackAmount], ['code'])
  }

  def destroy = {

  }

  private setupTestUserByEmail(String email) {
    def user = User.findByEmail(email)
    user.apiToken = authenticationService.generateUserApiToken(user.id, 1800)
    user.save()
  }
}
