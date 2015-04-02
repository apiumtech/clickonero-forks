package com.winbits.domain.affiliation

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 7/5/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SocialProviderEnum {

  FACEBOOK('facebook'),
  TWITTER('twitter')

  def provider

  SocialProviderEnum(String provider) {
    this.provider = provider
  }

}
