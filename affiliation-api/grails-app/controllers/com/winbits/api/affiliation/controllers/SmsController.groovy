package com.winbits.api.affiliation.controllers

import com.winbits.domain.sms.UserMobile
import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured

class SmsController {

  def smsService

  @Secured(['IS_AUTHENTICATED_FULLY'])
  Map send(SmsCommand cmd) {
    UserMobile userMobile
    if(cmd.hasErrors() || !cmd.validate()){
      throw new EntityValidationException('Error!! Need mobile number')
    }
    else{
      def user = getAuthenticatedUser()
      userMobile = smsService.sendCodeToActivation(cmd.mobile, user, cmd.carrier)
    }
    restpond mobile: userMobile.mobilePhone
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  Map activate(ActivateCommand cmd){
    def activate
    if(cmd.hasErrors() || !cmd.validate())
      throw new EntityValidationException('Error!! Need mobile number and code to validate')
    else{
      def user = getAuthenticatedUser()
      activate = smsService.activateMobile(cmd.mobile, cmd.code,user)
    }
    restpond mobile: activate.mobilePhone, status: activate.userMobileStatus
  }
}

class SmsCommand{
  String mobile
  Long carrier
  static constraints = {
    mobile nullable: false, minSize: 10, maxSize: 10
    carrier nullable: true
  }
}

class ActivateCommand{
  String mobile
  String code
  static constraints = {
    mobile nullable: false, minSize: 10, maxSize: 10
    code nullable: false, minSize: 5, maxSize: 5
  }
}