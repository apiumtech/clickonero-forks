package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.ActivationMobileException
import com.winbits.api.affiliation.exception.MobileAlreadyRegisterException
import com.winbits.api.affiliation.exception.UserHasMobileActiveException
import com.winbits.api.affiliation.exception.UserHasNotRegistersException
import com.winbits.api.clients.sms.SmsClient
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.config.ConfigurationService
import com.winbits.domain.sms.UserMobile
import com.winbits.domain.sms.UserMobileCarrier
import com.winbits.domain.sms.UserMobileCarrierEnum
import com.winbits.domain.sms.UserMobileStatusEnum
import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(SmsService)
@Build([Profile,User,UserMobile, UserMobileCarrier])
class SmsServiceSpec extends Specification {
  User user
  Profile profile
  private SmsClient smsClient

  def setup() {
    User.metaClass.encodePassword {->}
    user = User.build(id:1)
    profile = Profile.build(user: user)
    smsClient = Mock(SmsClient)
    service.smsClient = smsClient
  }

  def cleanup() {
  }

  void "When number is already register, throw exception"() {
    setup:
      def userMobile = UserMobile.build(mobilePhone:'5512960997',user: user,activationCode: 'x1x2x')
      userMobile.userMobileStatus = UserMobileStatusEnum.ACTIVE.domain
      userMobile.save(flush: true)

    when:
      service.sendCodeToActivation('5512960997', user, 1L)

    then:
      thrown(MobileAlreadyRegisterException)
  }

  void "When mobile does not register, save in user_mobile and update profile"(){
    given:
      UserMobileCarrier.build(id:1).save()
      ConfigurationService configurationService = Mock()
      configurationService.getConfigValue (_, _) >> 'jc087d283740574'
      service.configurationService = configurationService
      def mobile ='0987654321'

    when:
    service.sendCodeToActivation(mobile,user,1L)

    then:
    def userMobile = UserMobile.findByUser(user)
    assert userMobile.mobilePhone == mobile
    assert userMobile.activationCode.length() == 5
    assert userMobile.userMobileStatus.id == UserMobileStatusEnum.WAIT.id
    assert profile.phone == mobile
  }

  void "Second user register the same number, generate user_mobile register and code"(){
    given:
      ConfigurationService configurationService = Mock()
      configurationService.getConfigValue (_, _) >> 'jc087d283740574'
      service.configurationService = configurationService
      def mobile = '1234567890'
      User user2 = User.build(id:2)
      UserMobile.build(mobilePhone:mobile,user: user2,activationCode: 'y1y2y')

    when:
      service.sendCodeToActivation(mobile, user, 1L)

    then:
      def userMobile = UserMobile.findByUser(user)
      assert userMobile.mobilePhone == mobile
      assert userMobile.activationCode.length() == 5
      assert userMobile.userMobileStatus.id == UserMobileStatusEnum.WAIT.id

  }

  void "Try to activate a second mobile when user has one already activated"(){
    given:
      ConfigurationService configurationService = Mock()
      configurationService.getConfigValue (_, _) >> 'jc087d283740574'
      service.configurationService = configurationService
      def mobile = '5512960997'
      def userMobile = UserMobile.build(mobilePhone:'1234567890',user: user,activationCode: 'x1x2x')
      userMobile.userMobileStatus = UserMobileStatusEnum.ACTIVE.domain
      userMobile.save(flush: true)
      def userMobileToActivate = UserMobile.build(mobilePhone:'5512960997',user: user,activationCode: '12345')
      userMobileToActivate.userMobileStatus = UserMobileStatusEnum.WAIT.domain
      userMobileToActivate.save(flush: true)

    when:
      service.activateMobile(mobile,'12345',user)

    then:
      assert userMobile.userMobileStatus.id == UserMobileStatusEnum.CHANGE.id
  }

  void "Try to activate a mobile without other registers"(){
    setup:
      def mobile = '5512960997'
      def userMobile = UserMobile.build(mobilePhone:mobile,user: user,activationCode:'12345')

    when:
      service.activateMobile(mobile,'12345',user)

    then:
      assert userMobile.userMobileStatus.id == UserMobileStatusEnum.ACTIVE.id
  }

  void "Try activate a mobile without one register"(){
    setup:
      def mobile = '5512960997'

    when:
      service.activateMobile(mobile,'12345',user)

    then:
      thrown(UserHasNotRegistersException)
  }

  void "Try to activate a mobile whit bad code"(){
    setup:
      def mobile = '5512960997'
      UserMobile.build(mobilePhone:mobile,user: user,activationCode: '65478')

    when:
      service.activateMobile(mobile,'12345',user)

    then:
      thrown(ActivationMobileException)
  }

}
