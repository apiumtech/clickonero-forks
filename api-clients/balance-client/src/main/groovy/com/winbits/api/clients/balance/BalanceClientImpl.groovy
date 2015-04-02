package com.winbits.api.clients.balance

import com.winbits.api.clients.ApiClientsValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

import javax.annotation.PostConstruct

@Component('balanceClient')
class BalanceClientImpl implements BalanceClient {
  private static Logger log = LoggerFactory.getLogger(BalanceClientImpl)
  
  @Autowired
  RESTClient balanceRestClient

  @Override
  BigDecimal getBitsBalance(Long bitsAccountId) {
    log.info('Getting bits balance of account {}', bitsAccountId)
    Assert.notNull(bitsAccountId, 'Please, specify the bits account id!')
    def response
    try {
      response = balanceRestClient.get(path: "?accountId=${bitsAccountId}")
    } catch (e) {
      log.error("Error getting balance: ",e)
      return 0
    }

    response.text.toBigDecimal()
  }
}
