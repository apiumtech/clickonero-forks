package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.api.affiliation.exception.PartnerHistoryNotFound
import com.winbits.domain.affiliation.Vertical
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class PartnerController {

  def partnerService

  def history(Long verticalId) {
    def user = getAuthenticatedUser()
    if( user ) {
      def vertical = Vertical.get(verticalId)
      if( vertical ) {
        restpond partnerService.findOrdersHistory(user, vertical)
      } else {
        throw new PartnerHistoryNotFound()
      }
    } else {
      throw new InvalidUserException()
    }

  }

  def list() {
    def user = getAuthenticatedUser()
    if(user) {
      restpond partnerService.findPartners(user)
    } else {
      throw new InvalidUserException()
    }
  }
}
