package com.winbits.api.dummyclients.sms
import org.springframework.stereotype.Component
import com.winbits.api.clients.sms.SmsClient

@Component("smsClient")
class DummySmsClientImpl implements SmsClient{

  def sendSms(String mobile, String code, String apiKey){
    def map =[mobile:mobile, code: code]
    return map
  }
}