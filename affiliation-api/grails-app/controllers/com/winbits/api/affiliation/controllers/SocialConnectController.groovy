package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.SocialProviderEnum
import grails.plugins.springsecurity.Secured

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SocialConnectController {
  //remember for default get a public profile permission
  static final List<String> PERMISSIONS_FACEBOOK_LINK = ['publish_actions'].asImmutable()
  static final serviceMapping = 'connect'
  def socialConnectService

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def status = {
    def providerId = params.providerId
    def result = [status: socialConnectService.isConnectionInStorage(providerId)]
    restpond result
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def connect(SocialCommand cmd) {
    if (cmd.hasErrors()) {
      renderErrorScript()
      return
    }
    cmd.response = response
    cmd.request = request
    def urlParams = [providerId:cmd.providerId, user:getAuthenticatedUser()?.email, verticalId: cmd.verticalId]
    restpond socialUrl: socialConnectService.getSocialConnectURL(cmd, urlParams, PERMISSIONS_FACEBOOK_LINK,serviceMapping)
  }

  def oauthCallback(SocialCommand cmd) {
    if (cmd.hasErrors()) {
      renderErrorScript()
      return
    }
    Map parameters = [:]
    cmd.response = response
    cmd.request = request
    def urlParams = [providerId:cmd.providerId, user:cmd.user, verticalId: cmd.verticalId]
    if(cmd.providerId.equals(SocialProviderEnum.FACEBOOK.provider)){
      parameters = socialConnectService.getOauthCallbackFacebookLink(cmd, urlParams, PERMISSIONS_FACEBOOK_LINK, serviceMapping)
    }else{
      parameters = socialConnectService.getOauthCallbackTwitterLink(cmd, urlParams, serviceMapping)
    }
    redirectToVertical parameters
  }

  def redirectToVertical (params){
    redirect url: socialConnectService.redirectToVertical(params)
  }

  @Secured(['IS_AUTHENTICATED_FULLY'])
  def removeConnection = {
    def providerId = params.providerId
    socialConnectService.removeConnection(providerId)
  }

  def renderErrorScript (){
    render """<html><body><script type="text/javascript">
                     window.close();
                </script></body></html>"""
  }

}

class SocialCommand {
  String providerId
  Long verticalId
  String user
  String error
  String denied
  HttpServletRequest request
  HttpServletResponse response
  String referredBy
  static constraints = {
    providerId nullable: true, inList: SocialProviderEnum.values()*.provider
    user nullable: true
    error nullable: true
    denied nullable: true
    request nullable: true
    response nullable: true
    referredBy nullable: true
  }

  SocialProviderEnum socialProvider() {
   SocialProviderEnum.values().find { it.provider == providerId }
  }
}