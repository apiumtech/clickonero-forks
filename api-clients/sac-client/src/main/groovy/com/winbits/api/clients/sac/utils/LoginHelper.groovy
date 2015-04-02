package com.winbits.api.clients.sac.helper

import org.springframework.stereotype.Component

@Component ('loginHelper')
class LoginHelper {

  String obtainApiToken (Map response){
    Map loginResponse = response.response
    LoginChecker.check (loginResponse.state)
    loginResponse.obj.apiToken
  }
}
