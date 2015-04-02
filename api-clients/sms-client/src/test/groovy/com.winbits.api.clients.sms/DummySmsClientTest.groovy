package com.winbits.api.clients.sms

import com.winbits.api.clients.sms.SmsClient
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])
class DummySmsClientTest {

  @Autowired
  private SmsClient smsClient

  @Test
  void dummySmsClientTest(){
    def response = smsClient.sendSms('5512960997','12345','7738f97e6812874bbd232a49482cdf5f')
    assert response
  }

}
