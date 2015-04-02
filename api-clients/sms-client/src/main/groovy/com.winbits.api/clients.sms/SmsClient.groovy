package com.winbits.api.clients.sms

interface SmsClient {

  def sendSms(String mobile, String code, String apiKey)

}