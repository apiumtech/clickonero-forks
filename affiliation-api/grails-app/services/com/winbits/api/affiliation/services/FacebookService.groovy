package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.FacebookCommand
import com.winbits.api.affiliation.exception.FacebookAuthorizationException
import com.winbits.domain.affiliation.SocialProviderEnum
import com.winbits.domain.affiliation.UserConnection
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.social.MissingAuthorizationException
import org.springframework.social.facebook.api.Facebook

class FacebookService {

  def Facebook facebook

  def share(String message) {
    try {
      String messageId = facebook.feedOperations().updateStatus(message)
      like(messageId)
    } catch (MissingAuthorizationException ex) {
      throw new FacebookAuthorizationException()
    }
  }

  def like(String id) {
    facebook.likeOperations().like(id)

  }

  def saveFacebookAccount(FacebookCommand command) {
    def userConnection = new UserConnection(userId: command.email,
        providerId: SocialProviderEnum.FACEBOOK.provider,
        providerUserId: command.providerUserId,
        accessToken: command.facebookToken,
        profileUrl: command.profileUrl,
        imageUrl:  command.imageUrl,
        expireTime: command.expireTime)
    userConnection.save()
  }

  def postImage(String message, String imageUrl, String name) {
    def content = IOUtils.toByteArray( imageUrl.toURL().openStream() )
    Resource resource = new ByteArrayResource(content){
      public String getFilename() throws IllegalStateException {
        return name
      }
    }

    facebook.mediaOperations().postPhoto(resource, message)
  }

}
