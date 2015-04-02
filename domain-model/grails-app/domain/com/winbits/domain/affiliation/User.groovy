package com.winbits.domain.affiliation

import com.winbits.domain.sms.UserMobile
import org.joda.time.DateTime
import com.winbits.domain.orders.CardSubscription

import java.security.SecureRandom

class User {
  String email
  String password
  String salt
  boolean enabled
  boolean accountExpired
  boolean accountLocked
  boolean passwordExpired

  String apiToken
  String facebookToken
  String secretToken
  String oauthToken
  Vertical vertical

  String referrerCode
  String referredBy

  DateTime dateCreated
  DateTime lastUpdated

  Long salesAgentId
  DateTime lastLogin
  Boolean fraudulent = false
  Integer completeProfileReminders = 0

  transient springSecurityService
  transient authenticationService

  private Profile userProfile

  static transients = ['salesAgentId']

  static hasMany = [userMobile: UserMobile, shippingAddresses: ShippingAddress, waitingListItems: WaitingListItem, wishListItems: WishListItem, cardSubscriptions: CardSubscription]

  static constraints = {
    email blank: false, unique: true, email: true
    password blank: false, maxSize: 75
    salt maxSize: 64
    referredBy nullable: true
    facebookToken nullable: true, maxSize: 128
    apiToken blank: false, maxSize: 128, nullable: true
    email blank: false, maxSize: 75
    referrerCode blank: false, maxSize: 20
    secretToken nullable: true
    oauthToken nullable: true
    lastLogin nullable: true
    completeProfileReminders min: 0, max: 4, defaultValue: 0
  }

  static mapping = {
    password column: '`password`'
    fraudulent defaultValue: '0'
    completeProfileReminders sqlType: 'TINYINT'
  }

  def grailsApplication

  Set<Role> getAuthorities() {
    UserRole.findAllByUser(this).collect { it.role } as Set
  }

  def beforeInsert() {
    encodePassword()
  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  protected void encodePassword() {
    password = springSecurityService.encodePassword(password, getSalt())
  }

  String getSalt() {
    if (this.salt == null) {
      generateSalt()
    }
    this.salt
  }

  String generateSalt() {
    def rnd = new byte[48];
    new SecureRandom().nextBytes(rnd)
    this.salt = rnd.encodeBase64()
    this.salt = this.salt.replaceAll(/\+/, '_')
    this.salt = this.salt.replaceAll(/\//, '_')
  }

  String confirmUrl() {
    def theBaseUrl = grailsApplication.config.api.winbits.baseUrl
    "$theBaseUrl/confirm/${getSalt()}"
  }

  String baseUrl() {
    vertical.baseUrl
  }

  Map profileMap() {
    profile()?.toMap() ?: [:]
  }

  Set subscriptions() {
    profile()?.subscriptions
  }

  String resendConfirmUrl() {
    def theBaseUrl = grailsApplication.config.api.winbits.baseUrl
    "$theBaseUrl/resend/${email.encodeAsURL()}"
  }

  def generateApiToken() {
    apiToken = authenticationService.generateUserApiToken(id)
  }

  String verticalName() {
    vertical.name
  }

  Map toEventBase() {
    [userName: email, date: new Date(), userId: id, vertical: verticalName()]
  }

  void updateLastLogin() {
    lastLogin = DateTime.now()
  }

  Profile profile() {
    if (!userProfile) {
      userProfile = Profile.findByUser(this)
    }
    userProfile
  }

  Profile addProfile(Profile profile) {
    profile.user = this
    userProfile = profile.save()
    userProfile
  }
}
