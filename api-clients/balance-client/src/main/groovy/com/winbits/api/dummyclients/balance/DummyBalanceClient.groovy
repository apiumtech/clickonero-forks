package com.winbits.api.dummyclients.bits

import com.winbits.api.clients.balance.BalanceClient
import com.winbits.api.clients.bits.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component('balanceClient')
class DummyBalanceClient implements BalanceClient {
  private static Logger log = LoggerFactory.getLogger(DummyBalanceClient)
  
  @Override
  BigDecimal getBitsBalance(Long bitsAccountId) {
    log.info('Getting bits balance of account {}', bitsAccountId)
    Assert.notNull(bitsAccountId, 'Please, specify the bits account id!')
    50.00
  }
}
