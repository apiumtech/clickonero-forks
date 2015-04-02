package com.winbits.api.clients.bits

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import com.winbits.api.dummyclients.bits.DummyBitsClient

/**
 * Test cases for BitsClient.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class BitsDummyClientTests {
  private static Logger log = LoggerFactory.getLogger(BitsDummyClientTests)

  @Autowired
  private DummyBitsClient dummyBitsClient

   @Test
  void testRefundBits(){
    def response = dummyBitsClient.refundBits(1L, 1L)
    log.info('response {}',response)

    assert response
    assert response['balance'] == 50.0
    assert response['expirationDate']
    assert response['transactionId'] == 1L

  }
}
