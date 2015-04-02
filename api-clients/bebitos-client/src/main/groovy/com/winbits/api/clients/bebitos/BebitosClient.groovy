package com.winbits.api.clients.bebitos

interface BebitosClient {

  Map findUserByCredentials(String email, String password)
  
  Map userDetail(String email)

}
