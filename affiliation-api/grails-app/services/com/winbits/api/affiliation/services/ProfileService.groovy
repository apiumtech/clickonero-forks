package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.ProfileCommand
import com.winbits.api.clients.mis.MisClient
import com.winbits.api.clients.mis.affiliation.ChangeDataUserEvent
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.WaitingListItem
import com.winbits.domain.affiliation.WishListItem
import com.winbits.domain.orders.Order
import com.winbits.domain.orders.OrderStatusEnum
import com.winbits.domain.sms.UserMobile
import com.winbits.domain.sms.UserMobileStatusEnum
import com.winbits.exceptions.api.client.EntityNotExistsException
import org.springframework.transaction.annotation.Transactional

class ProfileService {

  def bitsService
  MisClient misClient
  def subscriptionService
  def grailsApplication
  def socialConnectService
  def configurationService
  def ordersService

  List editProfile(User user, ProfileCommand command) {
    def profile = user.profile()
    command.updateProfile(profile)
    profile.save()
    def transferResponse = [:]
    if (!profile.completed && profile.isProfileComplete()) {
      log.info "First Complete Register promo add bits!"
      transferResponse = bitsService.transferBitsForCompleteRegister(user)
      profile.completed = true
      profile.save(flush: true)
    }
    def loginEvent = new ChangeDataUserEvent(userName: user.email, userId: user.id)
    if (user.salesAgentId)
      loginEvent.salesAgentId = user.salesAgentId
    log.info "changeDataUserEvent -> ${loginEvent.properties}"
    misClient.logMessage(loginEvent)
    [profile, transferResponse]
  }

  Long bitsAccount(long userId) {
    def user = User.get(userId)
    if (!user) {
      throw new EntityNotExistsException(userId, "User")
    }
    user.profile().bitsId
  }

  @Transactional(readOnly = true)
  def toProfileData(User user, BigDecimal bitsBalance = null) {
    if (user) {
      BigDecimal cashbackForComplete = 0
      // TODO: Move this logic inside Profile domain on plugin
      def profileMap = user.profileMap()
      def profile = user.profile()
      if (profileMap) {
        profileMap.with {
          zipCodeInfo = profile.zipCodeInfo
          zipCode = profile.zipCode
          location = profile.location
          phone = profile.phone
          completeRegister = profile.completed
//          newsletterPeriodicity = profile.newsletterPeriodicity
//          newsletterFormat = profile.newsletterFormat
          clickoneroId = profile.clickoneroId
          //Para usuarios migrados de bebitos activamos su bandera
            try{
                isFrombebitos= user.hasBeenUserMigratedfromBebitos
            }catch (Exception e){ //Para el caso de expresslogin
                def verticalPartnerList = com.winbits.domain.affiliation.VerticalPartner.findAllByUser(user)
                if(verticalPartnerList){
                    isFrombebitos=true
                }
                else{
                    isFrombebitos=false
                }
            }


        }
//        profileMap.wishListCount = WishListItem.countByUser(user)
//        profileMap.waitingListCount = WaitingListItem.countByUser(user)
//        profileMap.pendingOrdersCount = ordersService.countOutcomePending(user)
        cashbackForComplete = cashbackForCompleteRegister()
      }

      [id: user.id, email: user.email, apiToken: user.apiToken,
        bitsBalance: bitsBalance ?: bitsService.forUser(user),
        profile: profileMap,
        mobileActivationStatus: profile.phone ? hasUserMobile(user, profile.phone): null,
        socialAccounts: socialConnectService.accountsByUser(user),
//        subscriptions: subscriptionService.getSubscriptions(user),
//        mainShippingAddres: ShippingAddress.findByUserAndMain(user, true) ?: ShippingAddress.findByUser(user),
//        loginRedirectUrl: grailsApplication.config.api.winbits.logout.redirectUrl,
        cashbackForComplete: cashbackForComplete]

    } else {
      [:]
    }
  }

  private static UserMobileStatusEnum hasUserMobile(User user, String phone){
    UserMobile userMobile  = UserMobile.findByUserAndMobilePhone(user, phone,[sort:'lastUpdated',order:'desc'])
    if(userMobile)
      userMobile.userMobileStatus.enum
    else
      null
  }

  private BigDecimal cashbackForCompleteRegister (){
    configurationService.getConfigValue('promo.completeRegisterCashbackAmount',BigDecimal)
  }

  Boolean mustShowCompleteProfileRemainder(User user) {
    def profile = user.profile()
    if (user.completeProfileReminders < 4 && !profile.completed) {
      user.completeProfileReminders = user.completeProfileReminders + 1
      user.save()
      return true
    }
    return false
  }
}
