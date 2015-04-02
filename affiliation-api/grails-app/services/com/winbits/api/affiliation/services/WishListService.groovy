package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.BrandNotFoundException
import com.winbits.api.affiliation.exception.InvalidUserException
import com.winbits.api.affiliation.exception.WishListItemAlreadyIsInListException
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.WishListItem
import com.winbits.domain.catalog.Brand

class WishListService {


  def addToWishList(User user, long brandId) {
    if(user){
      def brand = Brand.findById(brandId)
      if (brand) {
        def wishListItem
        WishListItem.withoutHibernateFilter('enabledFilter') {
          wishListItem = WishListItem.findByUserAndBrand(user, brand)
        }
        if(wishListItem)
          if(wishListItem.deleted)
            wishListItem.deleted = false
          else
            throw new WishListItemAlreadyIsInListException()
        else
          wishListItem = createWishListItem(user, brand)
        wishListItem.save()
      }else
        throw new BrandNotFoundException()
    }else
      throw new InvalidUserException()

  }

  def createWishListItem(User user, Brand brand){
    def wishListItem = new WishListItem(user: user, brand: brand)
    user.addToWishListItems(wishListItem)
    user.save()
    wishListItem
  }


  def findAllWishListByUser(User user) {
    WishListItem.createCriteria().list {
      eq 'user', user
      eq 'deleted', false
      join 'brand'
      order 'dateCreated'
    }
  }

  def deleteWishListItem(User user, Brand brand) {
    WishListItem.executeUpdate(
        "update WishListItem as w set w.deleted = true where w.user = :user and w.brand = :brand",
        [user: user, brand: brand] )
  }
}
