package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.config.social.GrailsConnectSupport
import com.winbits.api.affiliation.controllers.SocialCommand
import com.winbits.domain.affiliation.SocialProviderEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.UserConnection
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.catalog.SocialProvider
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.web.context.request.NativeWebRequest

class SocialConnectService {

  ConnectionRepository connectionRepository
  UsersConnectionRepository usersConnectionRepository
  ConnectionFactoryLocator connectionFactoryLocator
  def grailsApplication
  def grailsLinkGenerator
  def registerService

  def saveConnection(Connection<?> connection, String providerId, String userId) {
    def repository = usersConnectionRepository.createConnectionRepository(userId)
    def connections = repository.findConnections(providerId)
    if( connections ) {
      repository.updateConnection(connection)
    } else {
      repository.addConnection(connection)
    }
  }

  def isConnectionInStorage(String providerId) {
    List connections = connectionRepository.findConnections(providerId)
    connections && !connections.isEmpty()
  }

  def removeConnection(String providerId) {
    connectionRepository.removeConnections(providerId)
  }

  def getLoginUrl() {
    grailsApplication.config.social.loginUrl
  }

  def getConnectedUrl() {
    grailsApplication.config.social.connectedUrl
  }

  def getDeniedUrl() {
    grailsApplication.config.social.deniedUrl
  }

  def getDisconnectUrl() {
    grailsApplication.config.social.disconnectUrl
  }

  def accounts() {
    Map connections = connectionRepository.findAllConnections()
    def providerList = connections.findResults{ k, v -> v ? k: null }
    createProviderResult(providerList)
  }

  def accountsByUser(User user) {
    def providers = UserConnection.findAllByUserId(user.email)
    def providerList = providers?.collect { it.providerId }
    createProviderResult(providerList)
  }

  private createProviderResult(providerList) {
    def allProviders = SocialProvider.findAll()
    def result = []
    allProviders.each { p ->
      def tmp = providerList.find { it == p.providerId }
      p.active = tmp ? true : false
      result.add(p)
    }
    result
  }

  String redirectToVertical(Map params){
    def paramsToUrl = params.collect { key, val -> "${key}=${val}" }.join('&')
    def vertical = Vertical.get(params.verticalId)
    "${vertical.baseUrl}/wbClient.html?${paramsToUrl}"
  }

  def getConnectionFactory (providerId){
    connectionFactoryLocator.getConnectionFactory(providerId)
  }

  NativeWebRequest getNativeWebRequest(SocialCommand cmd){
    NativeWebRequest nativeWebRequest = new GrailsWebRequest(cmd.request, cmd.response, cmd.request.servletContext)
    nativeWebRequest
  }

  UserConnection getUserConnection (providerName, providerId){
    UserConnection.findByProviderIdAndProviderUserId(providerName, providerId)
  }

  def getConnection(webSupport, conectionFactory, nativeWebRequest){
    webSupport.completeConnectionOAuth(conectionFactory, nativeWebRequest)
  }

  def getSocialConnectURL(SocialCommand cmd, Map urlParams, List permissions, String mapping){
   def callbackUrl = getCallbackUrl(urlParams, mapping)
   def webSupport =  getWebSupport(callbackUrl)
   def connectionFactory = getConnectionFactory(cmd.providerId)
   NativeWebRequest nativeWebRequest = getNativeWebRequest(cmd)
   def url = webSupport.buildOAuthUrl(connectionFactory, nativeWebRequest)
   if(cmd.providerId.equals(SocialProviderEnum.FACEBOOK.provider)){
     url = "${url}&scope=${getPermmisionsJoin(permissions, ',')}"
   }
   url
  }

  Map getOauthCallbackFacebookLink(SocialCommand cmd, Map urlParams, List permissions, String mapping) {
    Map parameters = [verticalId: cmd.verticalId]
    if(!cmd.error){
      def callback = getCallbackUrl(urlParams, mapping)
      def webSupport = getWebSupport(callback)
      def connectionFactory = getConnectionFactory(cmd.providerId)
      NativeWebRequest nativeWebRequest = getNativeWebRequest(cmd)
      def connection = getConnection(webSupport, connectionFactory, nativeWebRequest)
      parameters = validateFacebookLink(connection, parameters, cmd.providerId, permissions)
      if (parameters.size() == 1){
        saveConnection(connection,cmd.providerId, cmd.user)
        parameters.putAll([code:'success-authentication-fb-link',successCode:cmd.providerId.capitalize()])
      }
    }else{
      parameters.putAll([code:'denied-authentication-fb-link',errorCode:'DAFL',accountId:cmd.providerId])
    }
    parameters
  }

  Map validateFacebookLink(Connection connection, Map parameters, String providerId, List permissionList){
    def permissions = connection.getApi().userOperations().getUserPermissions()
    def userConnection = getUserByApi(connection, providerId)
    if(!permissions.containsAll(permissionList))
      parameters.putAll([code:'denied-permissions-fb-link',errorCode:'DPFL',accountId:providerId])
    else if(userConnection)
      parameters.putAll([code:'fb-has-link-account',errorCode:'FHLA',accountId:providerId])
    parameters
  }


  Map getOauthCallbackFacebookLogin(SocialCommand cmd, Map urlParams, List permissions, String mapping) {
    Map parameters = [verticalId: cmd.verticalId]
    if(!cmd.error){
      def callback = getCallbackUrl(urlParams, mapping)
      def webSupport = getWebSupport(callback)
      def connectionFactory = getConnectionFactory(cmd.providerId)
      NativeWebRequest nativeWebRequest = getNativeWebRequest(cmd)
      Connection<Facebook> connection = getConnection(webSupport, connectionFactory, nativeWebRequest)
      FacebookProfile facebookProfile = connection.getApi().userOperations().getUserProfile()
      def email = getEmailToLogin (facebookProfile, cmd.providerId)
      parameters = validateFacebookLogin(connection, parameters, email, permissions)
      if (parameters.size() == 1){
        def userConnectionValid = UserConnection.findByUserIdAndProviderId(email, cmd.providerId)
        if(email.equals(facebookProfile.email) && userConnectionValid && !userConnectionValid.providerUserId.equals(facebookProfile.id)){
          removeOldConnection(cmd.providerId, email)
          parameters.putAll([code:'success-authentication-change-fb-link',facebookId:facebookProfile.id])
        } else{
          parameters.putAll([code:'success-authentication-fb-register',facebookId:facebookProfile.id])
        }
        def user = registerService.getUserFacebookLogin(email, facebookProfile, cmd.referredBy, cmd.verticalId)
        saveConnection(connection, cmd.providerId, user.email)
      }
    }else{
      parameters.putAll([code:'denied-authentication-fb-register',errorCode:'DAFR'])
    }
    parameters
  }

  void removeOldConnection(String providerId, String email){
    def userConnection = UserConnection.findByUserIdAndProviderId(email, providerId)
    if (userConnection){
      userConnection.delete(flush: true)
    }
  }

  Map validateFacebookLogin(Connection connection, Map parameters, String email, List permissionList){
    def permissions = connection.getApi().userOperations().getUserPermissions()
    if(!permissions.containsAll(permissionList))
      parameters.putAll([code:'denied-permissions-fb-register',errorCode:'DPFR'])
    else if (!email)
      parameters.putAll([code:'email-inactive-fb-register',errorCode:'EIFR'])
    parameters
  }

  String getEmailToLogin(FacebookProfile facebookProfile, providerId){
    def userConnection = getUserConnection(providerId, facebookProfile.id)
    def email = userConnection?.userId?:facebookProfile.email
    email
  }


  Map getOauthCallbackTwitterLink(SocialCommand cmd, Map urlParams, String mapping) {
    Map parameters = [verticalId: cmd.verticalId]
    if(!cmd.denied){
      def callback = getCallbackUrl(urlParams, mapping)
      def webSupport = getWebSupport(callback)
      def connectionFactory = getConnectionFactory(cmd.providerId)
      NativeWebRequest nativeWebRequest = getNativeWebRequest(cmd)
      def connection = getConnection(webSupport, connectionFactory, nativeWebRequest)
      parameters = validateTwitterLink(connection, parameters, cmd.providerId)
      if (parameters.size() == 1){
        saveConnection(connection,cmd.providerId, cmd.user)
        parameters.putAll([code:'success-authentication-tw-link',successCode:cmd.providerId.capitalize()])
      }
    }else{
      parameters.putAll([code:'denied-authentication-tw-link',errorCode:'DATL',accountId:cmd.providerId])
    }
    parameters
  }

  Map validateTwitterLink(connection, parameters, providerId){
    def userConnection = getUserByApi(connection, providerId)
    if(userConnection)
      parameters.putAll([code:'tw-has-link-account',errorCode:'THLA',accountId:providerId])
    parameters
  }

  def getUserByApi(connection, providerId){
    def userProfile = connection.getApi().userOperations().getUserProfile()
    getUserConnection(providerId, userProfile.id)
  }

  def getPermmisionsJoin(List permissions, String joiner){
    permissions.join(joiner)
  }

  String getCallbackUrl(Map urlParams, String mapping){
    grailsLinkGenerator.link(mapping: mapping, action: "oauthCallback",
        params: urlParams, absolute: true)
  }

  def getWebSupport(callbackUrl){
    def webSupport = new GrailsConnectSupport(callbackUrl: callbackUrl)
    webSupport
  }

}
