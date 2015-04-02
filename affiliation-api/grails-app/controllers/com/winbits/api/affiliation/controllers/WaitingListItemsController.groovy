package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.WaitingListAlreadyIsInListException
import com.winbits.api.affiliation.exception.WaitingListInStockException
import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.api.affiliation.exception.WaitingListSkuProfileNotFoundException
import com.winbits.domain.catalog.SkuProfile
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class WaitingListItemsController {
  def waitingListService

  /**
   * Listar los items en el waiting list
   */
  def show(WaitingListFindCommand cmd) {
    def user =  getAuthenticatedUser();
    if(user) {
      restpond waitingListService.findAllByUser(user, cmd)
    } else {
      throw new InvalidUserException()
    }
  }

  /**
   * Agrega un item al waiting list
   */
  def save() {
    def user = getAuthenticatedUser()
    if(user) {
      def skuProfileId = params.long('skuProfileId')
      def skuProfile = SkuProfile.get(skuProfileId)
      if (skuProfile) {
        def isInStock = skuProfile.isNotSoldOut()
        if(!isInStock) {
          def waitingList = waitingListService.findByUserAndSkuProfile(user, skuProfile)
          if(!waitingList) {
            waitingListService.addToWaitingList(user, skuProfile)
            restpond " "
          } else {
            throw new WaitingListAlreadyIsInListException()
          }
        } else {
          throw new WaitingListInStockException()
        }
      } else {
        throw new WaitingListSkuProfileNotFoundException()
      }
    } else {
       throw new InvalidUserException()
    }





  }



  /**
   * Vaciar el waiting list
   */
  def delete() {
    def user = getAuthenticatedUser()
    if(user) {
      waitingListService.deleteWaitingListByUser(user)
    } else {
      throw new InvalidUserException()
    }

    restpond """

"""
  }

}

class WaitingListFindCommand {
  Long status
  Long site
  Integer offset
  Integer max

  static constraints = {
    status nullable: true
    site nullable: true
    offset nullable: true
    max nullable: true
  }
}
