package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.SkuProfileIsNotAvailable
import com.winbits.api.affiliation.exception.WaitingListSkuProfileNotFoundException
import com.winbits.domain.affiliation.WaitingListItemStatusEnum
import com.winbits.domain.catalog.SkuProfile
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class WaitingListNotifierController {

  def waitingListNotifierService

  def update() {
    def skuProfileId = params.long('skuProfileId')
    def skuProfile = SkuProfile.get(skuProfileId)
    if (skuProfile) {
      if(skuProfile.getAvaliable().getEnum() == WaitingListItemStatusEnum.AVAILABLE) {
        waitingListNotifierService.notifyUsersWhenSkuProfileIsAvailable(skuProfile)
        restpond ""
      } else {
        throw new SkuProfileIsNotAvailable()
      }
    } else {
      throw new WaitingListSkuProfileNotFoundException()
    }

  }
}
