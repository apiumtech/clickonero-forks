package com.winbits.domain.affiliation

import com.winbits.domain.DomainDefaultsService
import org.apache.commons.lang.RandomStringUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import redis.clients.jedis.Jedis
import redis.clients.jedis.Pipeline

import javax.servlet.http.HttpServletRequest

class AuthenticationService {
  private static final int DEFAULT_API_TOKEN_TIME_TO_EXPIRE = 86400
  private static final String SALES_AGENT_ID_ATTR_NAME = "${AuthenticationService.name}.SALES_AGENT_ID_ATTR_NAME"

  static transactional = false

  def springSecurityService
  def userService
  def redisService
  def grailsApplication

  def deleteApiToken(String apiToken) {
    redisService.del apiToken
  }

  def validateApiToken(String apiToken) {
    def hashRedis = redisService.hgetAll apiToken
    hashRedis ? true : false
  }

  @Transactional(readOnly = true)
  def loginApiToken(String apiToken) {
    User user = findUserByApiToken(apiToken)
    user ? [authenticateUser(user), user] : null
  }

  User findUserByApiToken(String apiToken){
    def hashRedis = getTokenToRedis(apiToken)
    userService.byHashRedis(hashRedis)
  }

  private def authenticateUser(User user) {
    springSecurityService.reauthenticate(user.email)
    SecurityContextHolder.context
  }

  void saveCurrentSalesAgentId(Long id, HttpServletRequest request) {
    request.setAttribute(SALES_AGENT_ID_ATTR_NAME, id)
  }

  Long getCurrentSalesAgentId(HttpServletRequest request) {
    request.getAttribute(SALES_AGENT_ID_ATTR_NAME) as Long
  }

  boolean isAuthenticated(User user) {
    if (user) {
      def context = authenticateUser(user)
      context.authentication.isAuthenticated()
    } else {
      false
    }
  }

  @Transactional
  User authenticateByToken(String apiToken, boolean firstLogin = false) {
    def hashRedis = getTokenToRedis(apiToken)
    def user = userService.byHashRedis(hashRedis)
    if (user) {
      user.apiToken = apiToken
      user.save()
    }
    if (isAuthenticated(user) && firstLogin) {
      user.updateLastLogin()
      user.save()
    }
    user
  }

  @Transactional(readOnly = true)
  String generateUserApiTokenByEmail(String email, int timeToExpire = DEFAULT_API_TOKEN_TIME_TO_EXPIRE) {
    def user = User.findByEmail(email)
    if (!user) {
      throw new IllegalStateException("A user with email $email does not exists!")
    }
    generateUserApiToken(user.id, timeToExpire)
  }

  String generateUserApiToken(Long id, int timeToExpired = DEFAULT_API_TOKEN_TIME_TO_EXPIRE) {
    String token = RandomStringUtils.randomAlphanumeric 64
    saveApiToken(id, token, timeToExpired)
    token
  }

  private void saveApiToken(Long id, String token, int timeToExpired) {
    redisService.withPipeline { Pipeline pipeline ->
      pipeline.hmset token, [id: id.toString()]
      pipeline.expire token, timeToExpired
    }
  }

  String generateSwitchUserApiToken(Long id, Long agentId) {
    String token = RandomStringUtils.randomAlphanumeric 64
    int timeToExpired = 1800
    redisService.withPipeline { Pipeline pipeline ->
      pipeline.hmset token, [sid: id.toString(), id: agentId.toString()]
      pipeline.expire token, timeToExpired
    }
    token
  }

  def getTokenToRedis(String token) {
    def hashRedis = [:]
    int timeToExpired = DEFAULT_API_TOKEN_TIME_TO_EXPIRE
    hashRedis = redisService.hgetAll token
    if(hashRedis) {
      redisService.expire token, timeToExpired
    }
    
    hashRedis
  }

  boolean isActiveCRC(String crc){
    def result = redisService.del crc
    result > 0 ? true: false
  }

  def createCRCintoRedis(){
      String crc_random = RandomStringUtils.randomAlphanumeric 10
      int timeToExpired = 900
      
      redisService.set crc_random, ''
      redisService.expire crc_random, timeToExpired

      return crc_random
  }

  def createCRCintoRedisTest(){
        String crc_random = '1234567890'
        int timeToExpired = 900
        
        redisService.set crc_random, ''
        redisService.expire crc_random, timeToExpired

        return crc_random
  }

  @Transactional(readOnly = true)
  void generateTestToken() {
    def testUser = User.findByEmail(DomainDefaultsService.TEST_USER_EMAIL)
    saveApiToken(testUser.id, testUser.apiToken, 3600)
  }
}
