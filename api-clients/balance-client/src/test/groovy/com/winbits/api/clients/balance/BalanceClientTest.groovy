package com.winbits.api.clients.balance

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
@Ignore
class BalanceClientTest {
  private static Logger log = LoggerFactory.getLogger(BalanceClientTest)

  @Autowired
  private BalanceClient balanceClient
  
  @Test
  void getBitsBalance() {
    assert balanceClient.getBitsBalance(1L) > 0
  }
  
  @Test
  void getBitsBalanceError() {
    assert balanceClient.getBitsBalance(9999999999999L) == 0
  }

}
