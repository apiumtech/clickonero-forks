package com.winbits.domain.affiliation

class UserService {

  User byToken(String apiToken) {
    User.findByApiToken(apiToken)
  }

  User byEmail(String email) {
    User.findByEmail(email)
  }

  User byHashRedis(HashMap hashRedis) {
    def user = null
    if (hashRedis) {
      Long userId = Long.valueOf(hashRedis.sid ?: hashRedis.id)
     user =  User.get(userId)
      if(hashRedis.sid && user)
        user.salesAgentId = Integer.valueOf(hashRedis.id)
    }
    user
  }

}
