package com.winbits.api.affiliation.controllers.social

import com.winbits.api.affiliation.controllers.SocialCommand
import com.winbits.api.affiliation.services.RegisterService
import com.winbits.api.affiliation.services.SocialConnectService
import com.winbits.grest.test.spock.ApiSpecification
import grails.util.Holders
import spock.lang.Shared

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 8/07/14
 * Time: 05:48 PM
 * To change this template use File | Settings | File Templates.
 */
class SocialConnectApiSpec extends ApiSpecification{

  private static String API_TOKEN = 'W1nb1tsT3st'

  final String apiToken = API_TOKEN

  final String resource = '/connect/facebook'

  @Shared
  def fixtureLoader

  def setupSpec(){
    def ctx = Holders.applicationContext
    stubGetSocialConnectURL()
    fixtureLoader = ctx.getBean('fixtureLoader')
  }

  private void stubGetSocialConnectURL(String url) {
    SocialConnectService.metaClass.getSocialConnectURL = { SocialCommand cmd, Map urlParams, List permissions, String mapping ->
      url
    }
  }

  private void stubGetOauthCallbackFacebookLink(Map parameters) {
    SocialConnectService.metaClass.getOauthCallbackFacebookLink= { SocialCommand cmd, Map urlParams, List permissions, String mapping ->
      parameters
    }
  }

  private void stubGetOauthCallbackFacebookLogin(Map parameters) {
    SocialConnectService.metaClass.getOauthCallbackFacebookLogin= { SocialCommand cmd, Map urlParams, List permissions, String mapping ->
      parameters
    }
  }

  private void stubGetOauthCallbackTwitterLink(Map parameters) {
    SocialConnectService.metaClass.getOauthCallbackTwitterLink= { SocialCommand cmd, Map urlParams, String mapping ->
      parameters
    }
  }

  def cleanup(){

  }

  def 'user connect to facebook'(){
    when:
    post(){}
    then:
    assertStatus 200
    assert jsonResponse != null
    assert jsonResponse.socialUrl != null

  }

  def 'user connect request facebook'(){
    given:'mock the service of facebook'
    def url = 'https://www.facebook.com/settings?tab=applications'
    stubGetSocialConnectURL(url)
    when:
    post() {}
    then:
    assertStatus 200
    jsonResponse != null
    jsonResponse.socialUrl != null
    jsonResponse.socialUrl.equals(url)
  }

  def 'user connect to twitter'(){
    when:
    post(path:'/connect/twitter'){}
    then:
    assertStatus 200
    assert jsonResponse != null
    assert jsonResponse.socialUrl != null
  }

  def 'user connect request twitter'(){
    given:'mock the service of twitter'
    def url = 'https://www.twitter.com/settings?tab=applications'
    stubGetSocialConnectURL(url)
    when:
    post(path:'/connect/twitter') {}
    then:
    assertStatus 200
    jsonResponse != null
    jsonResponse.socialUrl != null
    jsonResponse.socialUrl.equals(url)
  }

  def 'user connect request facebook login'(){
    given:'mock the service of twitter'
    def url = 'https://www.facebook.com/settings?tab=applications'
    stubGetSocialConnectURL(url)
    when:
    post(path:'/facebook-login/connect',followRedirects:false) {}
    then:
    assertRedirect(url)
  }

  def 'oauth facebook link authorization denied'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'denied-authentication-fb-link',errorCode:'DAFL',accountId:'providerId']
    stubGetOauthCallbackFacebookLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/facebook')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=denied-authentication-fb-link&errorCode=DAFL&accountId=providerId')
  }

  def 'oauth facebook link permissions denied'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'denied-permissions-fb-link',errorCode:'DPFL',accountId:'providerId']
    stubGetOauthCallbackFacebookLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/facebook')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=denied-permissions-fb-link&errorCode=DPFL&accountId=providerId')
  }

  def 'oauth facebook link email link to other account'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'fb-has-link-account',errorCode:'FHLA',accountId:'providerId']
    stubGetOauthCallbackFacebookLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/facebook')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=fb-has-link-account&errorCode=FHLA&accountId=providerId')
  }

  def 'oauth facebook link success'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'success-authentication-fb-link',successCode:'Facebook']
    stubGetOauthCallbackFacebookLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/facebook')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=success-authentication-fb-link&successCode=Facebook')
  }

  def 'oauth twitter link authorization denied'(){
    given:'mock the service of twitter'
    def parameters = [verticalId:1,code:'denied-authentication-tw-link',errorCode:'DATL',accountId:'providerId']
    stubGetOauthCallbackTwitterLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/twitter')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=denied-authentication-tw-link&errorCode=DATL&accountId=providerId')
  }

  def 'oauth twitter link email link to other account'(){
    given:'mock the service of twitter'
    def parameters = [verticalId:1,code:'tw-has-link-account',errorCode:'THLA',accountId:'providerId']
    stubGetOauthCallbackTwitterLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/twitter')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=tw-has-link-account&errorCode=THLA&accountId=providerId')
  }

  def 'oauth twitter link success'(){
    given:'mock the service of twitter'
    def parameters = [verticalId:1,code:'success-authentication-tw-link',successCode:'Twitter']
    stubGetOauthCallbackTwitterLink(parameters)
    when:
    get(followRedirects:false,path:'/connect/twitter')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=success-authentication-tw-link&successCode=Twitter')
  }

  def 'oauth facebook login/register authorization denied'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'denied-authentication-fb-register',errorCode:'DAFR']
    stubGetOauthCallbackFacebookLogin(parameters)
    when:
    get(followRedirects:false,path:'/facebook-login/oauthCallback')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=denied-authentication-fb-register&errorCode=DAFR')
  }

  def 'oauth facebook login/register permissions denied'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'denied-permissions-fb-register',errorCode:'DPFR']
    stubGetOauthCallbackFacebookLogin(parameters)
    when:
    get(followRedirects:false,path:'/facebook-login/oauthCallback')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=denied-permissions-fb-register&errorCode=DPFR')
  }

  def 'oauth facebook email login/register to facebook account without principal email'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'email-inactive-fb-register',errorCode:'EIFR']
    stubGetOauthCallbackFacebookLogin(parameters)
    when:
    get(followRedirects:false,path:'/facebook-login/oauthCallback')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=email-inactive-fb-register&errorCode=EIFR')
  }

  def 'oauth facebook login/register success'(){
    given:'mock the service of facebook'
    def parameters = [verticalId:1,code:'success-authentication-fb-register',facebookId:'1231232178122']
    stubGetOauthCallbackFacebookLogin(parameters)
    when:
    get(followRedirects:false,path:'/facebook-login/oauthCallback')
    then:
    assertRedirect('http://www.winbits-test.com/wbClient.html?verticalId=1&code=success-authentication-fb-register&facebookId=1231232178122')
  }


}
