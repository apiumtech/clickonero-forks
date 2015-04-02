package com.winbits.api.clients.balance

interface BalanceClient {

  BigDecimal getBitsBalance(Long bitsAccountId)
}
