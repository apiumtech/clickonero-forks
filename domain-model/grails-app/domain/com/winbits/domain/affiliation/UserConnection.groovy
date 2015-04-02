package com.winbits.domain.affiliation


class UserConnection implements Serializable {

  String userId
  String providerId
  String providerUserId
  Integer rank=1
  String displayName
  String profileUrl
  String imageUrl
  String accessToken
  String secret
  String refreshToken
  Long expireTime



  static mapping = {
    id(composite: ['userId', 'providerId', 'providerUserId'])
    table 'UserConnection'
    version(false)
    userId column: 'userId'
    providerId column: 'providerId'
    providerUserId column: 'providerUserId'
    displayName column: 'displayName'
    profileUrl column: 'profileUrl'
    imageUrl column: 'imageUrl'
    accessToken column: 'accessToken'
    refreshToken column: 'refreshToken'
    expireTime column: 'expireTime'
  }

  static constraints = {
    displayName nullable: true
    refreshToken nullable: true
    secret nullable: true
    expireTime nullable: true
  }

}
