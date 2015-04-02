package com.winbits.api.clients.sms

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import com.winbits.api.clients.sms.SmsClient

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class SmsClientTest {

  @Autowired
  private SmsClient smsClient

  @Test
  void shouldResponseSms(){
    def response = smsClient.sendSms('5531995362','tEst1', '7738f97e6812874bbd232a49482cdf5f')
    assert response.responseMessage == 'Su mensaje se ha enviado SMC exitosamente'
    assert response.responseCode == 200
  }

}
