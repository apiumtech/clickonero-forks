package com.winbits.api.clients.balance

import com.winbits.api.dummyclients.bits.DummyBalanceClient
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class BalanceDummyClientTests {
  private static Logger log = LoggerFactory.getLogger(BalanceDummyClientTests)

  @Autowired
  private DummyBalanceClient dummyBalanceClient
   
  @Test
  void testGetBalance(){
    def response = dummyBalanceClient.getBitsBalance(1L)
    log.info('response {}',response)

    assert response
    assert response == 50.0
  }
}
