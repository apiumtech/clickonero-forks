package com.winbits.admin.services

import com.winbits.domain.logistics.*
import org.hibernate.criterion.CriteriaSpecification
import org.hibernate.transform.AliasToEntityMapResultTransformer
import org.springframework.transaction.annotation.Transactional

class OutcomeRequestDomainService {

  def messagingService

  def findAllSkuOutcomeWhitoutOutcomeRequest() {
    def innerJoin = CriteriaSpecification.INNER_JOIN
    def skuOutcomes = SkuOutcome.withCriteria {
      createAlias 'orderDetail', 'od', innerJoin
      createAlias 'warehouse', 'w', innerJoin
      createAlias 'orderDetail.order', 'o', innerJoin
      createAlias 'orderDetail.order.shippingOrder', 'so', innerJoin
      createAlias 'orderDetail.order.user', 'u', innerJoin
      projections {
        property('id', 'id')
        property('w.id', 'warehouseId')
        property('u.id', 'userId')
        property('so.commonAddress', 'commonAddress')
        property('so.firstName', 'firstName')
        property('so.lastName', 'lastName')
      }
      eq("status", SkuOutcomeStatusEnum.IN_WAREHOUSE.domain)
      isNull("outcomeRequest")
      resultTransformer AliasToEntityMapResultTransformer.INSTANCE
    }
    skuOutcomes.groupBy {
      [it.userId, it.commonAddress, it.firstName, it.lastName, it.warehouseId]
    }
  }

  def findAllSkuOutcome() {
    def skuOutcomes = SkuOutcome.findAll()
  }

  def findAllSkuOutcomeInSkuIds(List<Long> skuIdList) {
    if (skuIdList) {
      SkuOutcome.withCriteria {
        'in'("id", skuIdList)
        isNull('outcomeRequest')
      }
    } else {
      []
    }
  }

  OutcomeRequest createOutcomeRequest(List<SkuOutcome> skuOutcomes) {
    def skuOutcome = skuOutcomes.first()
    def outcomeRequest = new OutcomeRequest(status: OutcomeRequestStatusEnum.IN_WAREHOUSE.domain,
        wmsStatus: WmsStatusEnum.PENDING.domain, shippingOrder: skuOutcome.orderDetail.order.shippingOrder)
    skuOutcomes.each {
      outcomeRequest.addToSkuOutcomes(it)
    }
    outcomeRequest.save()
  }

  @Transactional(readOnly = true)
  def generateListSkuOutcomeIds() {
    def mapSkuOutcome = findAllSkuOutcomeWhitoutOutcomeRequest()
    log.info("Creando pedido para las salidas: ${mapSkuOutcome?:'sin salidas'} ")
    mapSkuOutcome.each {
      def skuOutcomeIdList = it.value*.id
      sendListSkuOutcomeToQueue(skuOutcomeIdList)
    }
  }

  def sendListSkuOutcomeToQueue(List<Long> skuOutcomeIdList){
    log.debug "skuOutcomeIdList -> $skuOutcomeIdList"
    messagingService.publishMessage("generateOutcomeRequestBySkuOutcomeIds", [skuOutcomeList: skuOutcomeIdList])
  }

  @Transactional
  def generateOutcomeRequest(List<Long> skuOutComeIdList) {
    def skuOutcomes = findAllSkuOutcomeInSkuIds(skuOutComeIdList)
    if (skuOutcomes) {
      createOutcomeRequest(skuOutcomes)
    } else {
      log.warn("SkuOutComes belongs to an existing OutcomeRequests! ${skuOutComeIdList}")
    }
  }
}
