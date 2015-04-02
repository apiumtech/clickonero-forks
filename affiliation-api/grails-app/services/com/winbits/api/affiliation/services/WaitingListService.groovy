package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.WaitingListFindCommand
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.WaitingListItem
import com.winbits.domain.affiliation.WaitingListItemStatus
import com.winbits.domain.catalog.Attribute
import com.winbits.domain.catalog.SkuProfile
import com.winbits.domain.orders.OrderDetail
import org.hibernate.transform.Transformers

class WaitingListService {

  def sessionFactory

  def addToWaitingList(User user, SkuProfile skuProfile) {
    def waitingList = WaitingListItem.findByUserAndSkuProfile(user, skuProfile)
    if(waitingList) {
      waitingList.deleted = false
    } else {
      waitingList = new WaitingListItem(user: user, skuProfile: skuProfile)
      user.addToWaitingListItems(waitingList)
      user.save()
    }
    waitingList.save()
  }

  def findAllByUser(User user, WaitingListFindCommand cmd = null) {
    String queryString = """
                select w.dateCreated as dateCreated,
                       itemGroupProfile.name as name,
                       skuProfile.id as id,
                       skuProfile.fullPrice as fullPrice,
                       skuProfile.price as price,
                       skuProfile.quantityOnHand as quantityOnHand,
                       skuProfile.quantityReserved as quantityReserved,
                       item.attributeName as attributeName,
                       item.attributeLabel as attributeLabel,
                       item.attributeValue as attributeValue,
                       attribute.name as attributeSkuName,
                       attribute.label as attributeSkuLabel,
                       attribute.value as attributeSkuValue,
                       attribute.type as attributeSkuType,
                       image as imageThumb,
                       brand.name as brandName,
                       brand.logo as brandLogo,
                       status.name as status,
                       vertical as vertical
                from WaitingListItem as w join w.status as status
                                          join w.skuProfile as skuProfile
                                          join w.skuProfile.itemGroupProfile as itemGroupProfile
                                          join skuProfile.sku as sku
                                          join skuProfile.vertical as vertical
                                          join sku.item as item
                                          join item.itemGroup as itemGroup
                                          join itemGroup.brand as brand
                                          left join item.images as image
                                          join image.imageType as imageType
                                          left join sku.attributes as attribute
                                          join attribute.type as type
                where w.user = ? and image.imageType.name = ? and w.deleted = false

    """

    def params = [user, "THUMB"]

    if(cmd?.status) {
      queryString += " and status.id = ?"
      params += [cmd.status]
    }

    if (cmd?.site) {
      queryString += " and vertical.id = ?"
      params += [cmd.site]
    }

    queryString += " order by dateCreated"
    def query = sessionFactory.getCurrentSession().createQuery( queryString ).setResultTransformer(
        Transformers.ALIAS_TO_ENTITY_MAP)

    def count = 0
    params.each {
      query.setParameter(count, params[count])
      count++
    }

    createWaitingListResult( query.list() )
  }

  def findByUserAndSkuProfile(User user, SkuProfile skuProfile) {
    WaitingListItem.findByUserAndSkuProfileAndDeleted(user, skuProfile, false)
  }

  def createWaitingListResult(List waitingListMap) {
    def result = []
    def waitingIdsSet = waitingListMap.id as Set
    waitingIdsSet.each {
      def waitingListItem = createWaitingListItemResult(it, waitingListMap)
      result.add(waitingListItem)
    }
    result
  }

  def searchAttributes(List waitingList) {
    def attributes = []
    waitingList.each {
      def attribute = new Attribute(
          name: it.attributeSkuName,
          label: it.attributeSkuLabel ,
          value: it.attributeSkuValue,
          type: it.attributeSkuType)
      attributes.add(attribute)
    }
    attributes
  }

  def createWaitingListItemResult(id, List waitingList) {
    def itemResult = [:]
    def waitingListByIdResult = waitingList.findAll { it.id == id }
    if( waitingListByIdResult ) {
      def principal = waitingListByIdResult[0]
      itemResult = [ skuProfile: [
                        id: principal.id,
                        name: principal.name,
                        vertical: principal.vertical,
                        fullPrice: principal.fullPrice,
                        price: principal.price,
                        stock: ( principal.quantityOnHand == -1?
                                   principal.quantityOnHand :
                                     ( principal.quantityOnHand-principal.quantityReserved ) ),
                        mainAttribute: [
                            name: principal.attributeName,
                            label: principal.attributeLabel,
                            value: principal.attributeValue],
                        attributes: searchAttributes(waitingListByIdResult),
                        image: principal.imageThumb,
                        marca: principal.brandName,
                        logo: principal.brandLogo],
                    dateCreated: principal.dateCreated,
                    status: principal.status ]
    }
    itemResult
  }

  def deleteWaitingListByUser(user) {
    WaitingListItem.executeUpdate("update WaitingListItem set deleted = true where user = ?", [user])
  }

  def deleteWaitingListItem(WaitingListItem waitingListItem, Long orderDetailId) {
    waitingListItem.orderDetail = orderDetailId ? OrderDetail.get(orderDetailId) : null
    waitingListItem.deleted = true
    waitingListItem.save()
  }

  def updateStatusBySkuProfile(SkuProfile skuProfile) {
    WaitingListItemStatus status = skuProfile.getAvaliable()
    WaitingListItem.executeUpdate("update WaitingListItem w set status = :status where skuProfile = :skuProfile",
        [status: status, skuProfile: skuProfile])
  }
}
