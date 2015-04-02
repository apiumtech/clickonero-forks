package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.PartnerHistoryNotFound
import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.VerticalPartner
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.web.json.JSONObject

class PartnerService {

  def mongoDbService

  def findOrdersHistory(User user, Vertical vertical) {
    def verticalPartner = VerticalPartner.findByUserAndVertical(user, vertical)
    if( verticalPartner ) {
      def document = mongoDbService.findDocumentById(vertical.getName(), verticalPartner.mongoHistoryId)
      if( document ) {
        new JsonSlurper().parseText(document)
      } else {
        throw new PartnerHistoryNotFound()
      }
    } else {
      throw new PartnerHistoryNotFound()
    }
  }

  def updateAsMigrationComplete(objectId, VerticalPartner partner) {
    if (objectId) {
      partner.mongoHistoryId = objectId
      partner.status = MigrationStatusEnum.COMPLETE.domain
      partner.save()
    }
  }

  def saveInfoInMongoDb(Map userDetail, Vertical vertical) {
    String userDetailJson = new JSONObject(userDetail).toString()
    mongoDbService.insertInCollection(vertical.name, userDetailJson)
  }

  def findPartners(User user) {
    VerticalPartner.findByUserAndStatus(user, MigrationStatusEnum.COMPLETE.domain)
  }
}
