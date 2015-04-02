package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.ActivationMobileException
import com.winbits.api.affiliation.exception.MobileAlreadyRegisterException
import com.winbits.api.affiliation.exception.SendSmsServiceUnableException
import com.winbits.api.affiliation.exception.UserHasNotRegistersException
import com.winbits.api.clients.sms.SmsClient
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.sms.UserMobile
import com.winbits.domain.sms.UserMobileCarrier
import com.winbits.domain.sms.UserMobileStatusEnum
import org.apache.commons.lang.RandomStringUtils
import org.joda.time.DateTime

class SmsService {

  SmsClient smsClient
  def configurationService

  UserMobile sendCodeToActivation(String mobile, User user, Long carrierId) {
    String activationCode
    UserMobile userMobile = UserMobile.findByMobilePhone(mobile,[sort:'lastUpdated',order:'desc'])

    if(userMobile && userMobile.userMobileStatus.id == UserMobileStatusEnum.ACTIVE.id){
      throw new MobileAlreadyRegisterException(mobile)
    }

    if(!userMobile || userMobile.user != user){
        (activationCode, userMobile) = saveNewUserMobileRegister(user, mobile, carrierId, activationCode, userMobile)
    } else{
       log.info "NUMBER ALREADY REGITER RE-SEND CODE ${userMobile}"
      activationCode = userMobile.activationCode
      userMobile.lastUpdated = DateTime.now().toDate()
      userMobile.save(flush:true)
    }

    try {
      smsClient.sendSms(mobile, activationCode, getSmsApiKey())
    }catch(e){
        throw new SendSmsServiceUnableException(mobile)
    }

    userMobile
  }

  private static List saveNewUserMobileRegister(User user, String mobile, long carrierId, String activationCode, UserMobile userMobile) {
    activationCode = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
    userMobile = new UserMobile()
    userMobile.user = user
    userMobile.mobilePhone = mobile
    userMobile.activationCode = activationCode
    userMobile.userMobileStatus = UserMobileStatusEnum.WAIT.domain
    userMobile.carrier = UserMobileCarrier.get(carrierId)
    userMobile.save()
    updateUserProfile(mobile, user)
    [activationCode, userMobile]
  }

  private String getSmsApiKey(){
    configurationService.getConfigValue('sms.apikey',String)
  }

  UserMobile activateMobile(String mobile, String code, User user){
    def activateMobile = UserMobile.findByMobilePhoneAndUserMobileStatus(mobile, UserMobileStatusEnum.ACTIVE.domain)
    if(activateMobile){
      throw new MobileAlreadyRegisterException(mobile)
    }else{
      changeUserMobileStatus(user, code, mobile)
    }
  }

  private static void changeActiveRegister(List<UserMobile>  list){
    UserMobile userMobile=list.find{it.userMobileStatus.id == UserMobileStatusEnum.ACTIVE.id}
    if(userMobile){
      userMobile.userMobileStatus = UserMobileStatusEnum.CHANGE.domain
      userMobile.save(flush: true)
    }
  }

  private static UserMobile changeUserMobileStatus(User user, String code, String mobile) {
    def list = UserMobile.findAllByUserAndUserMobileStatusNotEqual(user, UserMobileStatusEnum.CANCELLED.domain)
    if(!list)
      throw new UserHasNotRegistersException(mobile)
    changeActiveRegister(list)
    findToActivateMobile(list,mobile, code)
  }

  private static UserMobile findToActivateMobile(List<UserMobile> list, String mobile, String code){
    UserMobile userMobile2Activate = list.find{
      it.mobilePhone == mobile && it.activationCode == code
    }

    if(!userMobile2Activate)
      throw new ActivationMobileException(mobile)

    change2Active(userMobile2Activate)
    cancelledOtherRegisters(list)
    userMobile2Activate
  }

  private static void change2Active(UserMobile userMobile){
    userMobile.userMobileStatus = UserMobileStatusEnum.ACTIVE.domain
    userMobile.lastUpdated = DateTime.now().plusSeconds(10).toDate()
    userMobile.save(flush:true)
  }

  private static void cancelledOtherRegisters(List<UserMobile> list){
    list.each{
      if(it.userMobileStatus.id == UserMobileStatusEnum.WAIT.id){
        it.userMobileStatus = UserMobileStatusEnum.CANCELLED.domain
        it.save()
      }
    }
  }

  private static void updateUserProfile(String mobile, User user){
    Profile profile = Profile.findByUser(user)
    profile.phone = mobile
    profile.save()
  }
}
