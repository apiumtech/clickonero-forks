package com.winbits.api.affiliation.controllers

import com.winbits.api.affiliation.exception.FacebookParamsException
import com.winbits.api.affiliation.exception.MessageRequiredSocialException
import grails.plugins.springsecurity.Secured
import com.winbits.domain.affiliation.User

@Secured(['IS_AUTHENTICATED_FULLY'])
class FacebookPublishController {

  static allowedMethods = [share: 'POST', like: 'POST', publishRegisterLink: 'POST']

  def facebookService
  def inviteRegisterService

  def share() {
    String message = params.message
    if(message) {
      facebookService.share(message)
      restpond " "
    } else {
      throw new MessageRequiredSocialException()
    }
  }

  def like() {
    String objectId = params.objectId
    facebookService.like(objectId)
    restpond " "
  }

  def publishRegisterLink() {
    User user = getAuthenticatedUser()
    def verticalId = params.verticalId as Long
    String shareUrl = inviteRegisterService.createLink(user, verticalId)
    def msg = message(code: 'social.referredcode.link', args: [user.profile().name, shareUrl])
    facebookService.share(msg)
    restpond ""
  }

  def facebookPost(FacebookPostCommand cmd) {
    if( !cmd.hasErrors() ) {
      def postId = facebookService.postImage(cmd.message, cmd.imageUrl, cmd.name)
      def resp = [postId: postId]
      restpond resp
    } else {
      throw new FacebookParamsException()
    }
  }
}

class FacebookPostCommand {
  String imageUrl
  String message
  String name

  static constraints = {
    message nullable: false
    imageUrl nullable: false
    name nullable: false
  }
}