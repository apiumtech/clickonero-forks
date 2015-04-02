package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.MessageRequiredSocialException
import grails.plugins.springsecurity.Secured
import com.winbits.domain.affiliation.User

@Secured(['IS_AUTHENTICATED_FULLY'])
class TwitterPublishController {

  static allowedMethods = [updateStatus: 'POST', publishRegisterLink: 'POST']

  def twitterService
  def inviteRegisterService

  def updateStatus() {
    String message = params.message
    if(message) {
      try{
        twitterService.publishStatus(message)
        restpond "Success publish!"
      }catch(e){
        restpond "Duplicate Status!"
      }
    } else {
      throw new MessageRequiredSocialException()
    }
  }

  def publishRegisterLink() {
    User user = getAuthenticatedUser()
    def verticalId = params.verticalId as Long
    String shareUrl = inviteRegisterService.createLink(user, verticalId)
    def msg = message(code: 'social.referredcode.link', args: [user.profile().name, shareUrl])
    twitterService.publishStatus(msg)
    restpond ""
  }
}
