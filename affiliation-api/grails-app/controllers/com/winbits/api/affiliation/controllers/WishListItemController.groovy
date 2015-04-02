package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.api.affiliation.exception.BrandNotFoundException
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.Brand
import grails.plugins.springsecurity.Secured

class WishListItemController {

  def wishListService

  @Secured('IS_AUTHENTICATED_FULLY')
  def delete(Long brandId) {
    def user = getAuthenticatedUser() as User
    if(user) {
      def brand = Brand.findByIdAndDeleted(brandId, false)
      if (brand) {
        wishListService.deleteWishListItem(user, brand)
        restpond wishListService.findAllWishListByUser(user)
      } else {
        throw new BrandNotFoundException()
      }
    } else {
      throw new InvalidUserException()
    }
  }
}
