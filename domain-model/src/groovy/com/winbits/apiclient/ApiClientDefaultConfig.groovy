package com.winbits.apiclient

api.clients.bits.baseUrl = 'http://localhost:8001'
api.clients.bits.contextSuffix = '-api'

environments {
  development {
    api.clients.bits.baseUrl = 'https://apidev.winbits.com'
    api.clients.bits.contextSuffix = '-api'
  }
  qa {
    api.clients.bits.baseUrl = 'https://apici.winbits.com/v1'
    api.clients.bits.contextSuffix = ''
  }
  staging {
    api.clients.bits.baseUrl = 'https://apistaging.winbits.com/v1'
    api.clients.bits.contextSuffix = ''
  }
  production {
    api.clients.bits.baseUrl = 'https://api.winbits.com/v1'
    api.clients.bits.contextSuffix = ''
  }
}
