package com.winbits.api.affiliation.controllers

import com.winbits.domain.affiliation.SocialProviderEnum

class FacebookLoginController {
  //remember for default get a public profile permission
  static List<String> PERMISSIONS_FACEBOOK_LOGIN = ["email", 'user_birthday', 'publish_actions'].asImmutable()
  static String providerId = SocialProviderEnum.FACEBOOK.provider
  static String serviceMapping = 'facebookLogin'
  def socialConnectService
  def grailsApplication

  def connect(SocialCommand cmd) {
    if (cmd.hasErrors()) {
      renderErrorScript()
      return
    }
    cmd.providerId = providerId
    Map urlParams = [verticalId:  cmd.verticalId]
    if(cmd.referredBy){urlParams.put('referredBy', cmd.referredBy)}
    cmd.response = response
    cmd.request = request
    redirect(url: socialConnectService.getSocialConnectURL(cmd, urlParams, PERMISSIONS_FACEBOOK_LOGIN, serviceMapping))
  }

  def oauthCallback(SocialCommand cmd) {
    if (cmd.hasErrors()) {
      renderErrorScript()
      return
    }
    cmd.providerId = providerId
    Map urlParams = [verticalId:  cmd.verticalId]
    if(cmd.referredBy){urlParams.put('referredBy', cmd.referredBy)}
    cmd.response = response
    cmd.request = request
    redirectToVertical socialConnectService.getOauthCallbackFacebookLogin(cmd, urlParams, PERMISSIONS_FACEBOOK_LOGIN, serviceMapping)
  }

  def redirectToVertical (params){
    redirect url: socialConnectService.redirectToVertical(params)
  }

  def renderErrorScript (){
    render """<html><body><script type="text/javascript">
                     window.close();
                </script></body></html>"""
  }

}
