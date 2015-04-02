package com.winbits.api.clients.sac.helper

import com.winbits.api.clients.sac.exceptions.LoginClientException

class LoginChecker {
  static check (arg){
    if (arg != 200){
      throw new LoginClientException ('Error al hacer login itentalo mas tarde') 
    }
  }
}
