package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.BrandNotFoundException
import com.winbits.domain.affiliation.WishListItem
import grails.plugins.springsecurity.Secured

class WishListItemsController {

  def wishListService

  @Secured('IS_AUTHENTICATED_FULLY')
  def save() {
    def user = getAuthenticatedUser()
    def brandId = params.brandId
    if (brandId)
      wishListService.addToWishList(user, brandId)
    else
      throw new BrandNotFoundException()
    restpond wishListService.findAllWishListByUser(user)
  }

  @Secured('IS_AUTHENTICATED_FULLY')
  def show() {
    def user = getAuthenticatedUser()
    restpond wishListService.findAllWishListByUser(user).collect { WishListItem item ->
      [
          id: item.brand.id,
          dateAdded: item.dateCreated,
          name: item.brand.name,
          logo: item.brand.logo
      ]
    }
  }
}
