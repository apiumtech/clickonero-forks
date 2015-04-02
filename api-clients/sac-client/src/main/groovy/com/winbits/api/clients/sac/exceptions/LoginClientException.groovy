package com.winbits.api.clients.sac.exceptions

class LoginClientException extends Exception {
  String message
  public LoginClientException (String message){
    super (message)
    this.message = message
  }
}
