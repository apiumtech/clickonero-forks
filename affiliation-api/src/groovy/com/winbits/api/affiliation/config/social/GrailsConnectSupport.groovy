package com.winbits.api.affiliation.config.social

import com.winbits.domain.affiliation.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactory
import org.springframework.social.connect.support.OAuth1ConnectionFactory
import org.springframework.social.connect.support.OAuth2ConnectionFactory
import org.springframework.social.connect.web.ConnectSupport
import org.springframework.social.oauth1.AuthorizedRequestToken
import org.springframework.social.oauth1.OAuth1Operations
import org.springframework.social.oauth1.OAuth1Parameters
import org.springframework.social.oauth1.OAuth1Version
import org.springframework.social.oauth1.OAuthToken
import org.springframework.social.oauth2.AccessGrant
import org.springframework.social.oauth2.GrantType
import org.springframework.social.oauth2.OAuth2Operations
import org.springframework.social.oauth2.OAuth2Parameters
import org.springframework.util.Assert
import org.springframework.util.MultiValueMap
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.WebRequest

class GrailsConnectSupport extends ConnectSupport {
  private static final String OAUTH_TOKEN_ATTRIBUTE = "oauthToken";
  String callbackUrl
  Boolean useAuthenticateUrl

  public String buildOAuthUrl(ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
    Assert.notNull(connectionFactory, 'The connectionFactory is required')
    if (connectionFactory instanceof OAuth1ConnectionFactory) {
      return buildOAuth1Url((OAuth1ConnectionFactory<?>) connectionFactory, request, additionalParameters)
    } else if (connectionFactory instanceof OAuth2ConnectionFactory) {
      return buildOAuth2Url((OAuth2ConnectionFactory<?>) connectionFactory, request, additionalParameters)
    } else {
      throw new IllegalArgumentException("ConnectionFactory not supported")
    }
  }

  public Connection<?> completeConnectionOAuth(ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
    Assert.notNull(connectionFactory, 'The connectionFactory is required')
    if (connectionFactory instanceof OAuth1ConnectionFactory) {
      return completeConnectionOAuth1Url((OAuth1ConnectionFactory<?>) connectionFactory, request)
    } else if (connectionFactory instanceof OAuth2ConnectionFactory) {
      return completeConnectionOAuth2Url((OAuth2ConnectionFactory<?>) connectionFactory, request)
    } else {
      throw new IllegalArgumentException("ConnectionFactory not supported")
    }
  }

  public Connection<?> completeConnectionOAuth1Url(OAuth1ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
    String verifier = request.getParameter("oauth_verifier")
    String token = request.getParameter("oauth_token")
    AuthorizedRequestToken requestToken = new AuthorizedRequestToken(new OAuthToken( token, getUserByToken(token).secretToken ), verifier)
    OAuthToken accessToken = connectionFactory.getOAuthOperations().exchangeForAccessToken(requestToken, null)
    return connectionFactory.createConnection(accessToken)
  }

  public Connection<?> completeConnectionOAuth2Url(OAuth2ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
    String code = request.getParameter("code")
    def providerId = connectionFactory.getProviderId()
    def curl = callbackUrl(request, providerId)
    AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, curl, null)
    return connectionFactory.createConnection(accessGrant)
  }

  private String callbackUrl(NativeWebRequest request, String providerId) {
    //return new ApplicationTagLib().createLink(mapping: mapping, absolute: true, params: [providerId: providerId])
    callbackUrl
  }

  private String buildOAuth1Url(OAuth1ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
    OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations()
    OAuth1Parameters parameters = new OAuth1Parameters(additionalParameters)
    if (oauthOperations.getVersion() == OAuth1Version.CORE_10) {
      parameters.setCallbackUrl(callbackUrl(request, connectionFactory.providerId))
    }
    OAuthToken requestToken = fetchRequestToken(request, oauthOperations, connectionFactory.providerId)
    saveSecret( requestToken.getValue(), requestToken.getSecret() )
    return buildOAuth1Url(oauthOperations, requestToken.getValue(), parameters)
  }

  private saveSecret(String oauthToken, String secret) {
    User.executeUpdate("update User as u set u.oauthToken = :oauthToken, u.secretToken = :secret where u.email = :email",
        [oauthToken: oauthToken, secret: secret, email: getUserId()])
  }

  private getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
    authentication?.getName()
  }

  def getUserByToken(String oauthToken) {
    User.findByOauthToken(oauthToken)
  }

  private OAuthToken fetchRequestToken(NativeWebRequest request, OAuth1Operations oauthOperations, String providerId) {
    if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
      return oauthOperations.fetchRequestToken(callbackUrl(request, providerId), null);
    }
    return oauthOperations.fetchRequestToken(null, null);
  }

  private String buildOAuth2Url(OAuth2ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
    OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
    OAuth2Parameters parameters = getOAuth2Parameters(request, additionalParameters, connectionFactory.providerId);
    if (useAuthenticateUrl) {
      return oauthOperations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
    } else {
      return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
    }
  }

  private OAuth2Parameters getOAuth2Parameters(NativeWebRequest request, MultiValueMap<String, String> additionalParameters, String providerId) {
    OAuth2Parameters parameters = new OAuth2Parameters(additionalParameters);
    parameters.setRedirectUri(callbackUrl(request, providerId));
    String scope = request.getParameter("scope");
    if (scope != null) {
      parameters.setScope(scope);
    }
    return parameters;
  }

}
