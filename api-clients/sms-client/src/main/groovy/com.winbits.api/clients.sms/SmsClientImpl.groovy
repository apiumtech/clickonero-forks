package com.winbits.api.clients.sms

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder
import wslite.rest.ContentType


@Component("smsClient")
class SmsClientImpl implements SmsClient{

  private static final Logger log = LoggerFactory.getLogger(SmsClientImpl)

  @Autowired
  RESTClient smsRestClient

  @Override
  def sendSms(String mobile, String code, String apiKey){
    def response
    def message = "Tu codigo de verificacion WINBITS: ${code}"
    Map request = [msisdn: mobile, message: message, tag: '#Winbits']
    try{
      response = smsRestClient.post(path:"/rest/sms/sendSingle", headers:['apikey':apiKey]){
        type ContentType.JSON
        json request
      }
    }
    catch (e){
      log.error ("Error: ", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }
    response.json
  }

}
