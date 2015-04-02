package com.winbits.domain.editors
 
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.*
import com.winbits.domain.logistics.Bom
import com.winbits.domain.logistics.Odc
import com.winbits.domain.logistics.Warehouse
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderPayment
 
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 5/7/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
class CustomTypePropertyEditorRegistrar implements PropertyEditorRegistrar {
  public void registerCustomEditors(PropertyEditorRegistry registry) {
    registry.registerCustomEditor(ProviderFiscalData, new DomainClassLookupPropertyEditor(domainClass: ProviderFiscalData, property: "id"));
    registry.registerCustomEditor(ItemGroup, new DomainClassLookupPropertyEditor(domainClass: ItemGroup, property: "id"));
    registry.registerCustomEditor(ItemGroupProfile, new DomainClassLookupPropertyEditor(domainClass: ItemGroupProfile, property: "id"));
    registry.registerCustomEditor(Item, new DomainClassLookupPropertyEditor(domainClass: Item, property: "id"));
    registry.registerCustomEditor(Sku, new DomainClassLookupPropertyEditor(domainClass: Sku, property: "id"));
    registry.registerCustomEditor(Bom, new DomainClassLookupPropertyEditor(domainClass: Bom, property: "id"));
    registry.registerCustomEditor(Odc, new DomainClassLookupPropertyEditor(domainClass: Odc, property: "id"));
    registry.registerCustomEditor(SkuProfile, new DomainClassLookupPropertyEditor(domainClass: SkuProfile, property: "id"));
    registry.registerCustomEditor(Bank, new DomainClassLookupPropertyEditor(domainClass: Bank, property: "id"));
    registry.registerCustomEditor(Vertical, new DomainClassLookupPropertyEditor(domainClass: Vertical, property: "id"));
    registry.registerCustomEditor(Warehouse, new DomainClassLookupPropertyEditor(domainClass: Warehouse, property: "id"));
    registry.registerCustomEditor(Brand, new DomainClassLookupPropertyEditor(domainClass: Brand, property: "id"));
    registry.registerCustomEditor(BankAccount, new DomainClassLookupPropertyEditor(domainClass: BankAccount, property: "id"));
    registry.registerCustomEditor(Provider, new DomainClassLookupPropertyEditor(domainClass: Provider, property: "id"));
    registry.registerCustomEditor(ProviderContact, new DomainClassLookupPropertyEditor(domainClass: ProviderContact, property: "id"));
    registry.registerCustomEditor(PaymentMethod, new PaymentMethodPropertyEditor());
    registry.registerCustomEditor(Order, new DomainClassLookupPropertyEditor(domainClass: Order, property: "id"));
    registry.registerCustomEditor(OrderPayment, new DomainClassLookupPropertyEditor(domainClass: OrderPayment, property: "id"));
    registry.registerCustomEditor(ShippingAddress, new DomainClassLookupPropertyEditor(domainClass: ShippingAddress, property: "id"));
    registry.registerCustomEditor(ZipCodeInfo, new DomainClassLookupPropertyEditor(domainClass: ZipCodeInfo, property: "id"));
    registry.registerCustomEditor(Seller, new DomainClassLookupPropertyEditor(domainClass: Seller, property: "id"));

    registry.registerCustomEditor(VerticalMarketingType, new DomainClassLookupPropertyEditor(domainClass: VerticalMarketingType, property: "name"));
    registry.registerCustomEditor(SalesDepartmentType, new DomainClassLookupPropertyEditor(domainClass: SalesDepartmentType, property: "name"));
    registry.registerCustomEditor(CategoryType, new DomainClassLookupPropertyEditor(domainClass: CategoryType, property: "name"));
    registry.registerCustomEditor(SubCategoryType, new DomainClassLookupPropertyEditor(domainClass: SubCategoryType, property: "name"));
    registry.registerCustomEditor(ItemType, new DomainClassLookupPropertyEditor(domainClass: ItemType, property: "name"));
  }
}
