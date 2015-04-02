package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.api.affiliation.exception.WaitingListItemNotFoundException
import com.winbits.api.affiliation.exception.WaitingListSkuProfileNotFoundException
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.SkuProfile
import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class WaitingListItemController {

  def waitingListService
  /**
   * Eliminar el item del waiting list con el id especificado
   * @param id Id el Sku Profile
   */
  def delete(Long id) {
    def user = getAuthenticatedUser() as User
    if(user) {
      def skuProfile = SkuProfile.get(id)
      if(skuProfile) {
        def waitingListItem = waitingListService.findByUserAndSkuProfile(user, skuProfile)
        if (waitingListItem) {
          waitingListService.deleteWaitingListItem(waitingListItem, params.long('orderDetailsId'))
          restpond  waitingListService.findAllByUser(user)
        } else {
          throw new WaitingListItemNotFoundException()
        }
      } else {
        throw new WaitingListSkuProfileNotFoundException()
      }
    } else {
      throw new InvalidUserException()
    }
  }

  def save(Long id) {
    def skuProfile = SkuProfile.get(id)
    if (skuProfile) {
      waitingListService.updateStatusBySkuProfile(skuProfile)
    } else {
      throw new WaitingListSkuProfileNotFoundException()
    }

    restpond """

"""

  }
}
