package com.winbits.api.dummyclients

import com.winbits.api.clients.ApiClientsValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.validation.Validator

@Configuration
class DummyClientsConfig {

  @Autowired
  private Validator validator

  @Bean
  ApiClientsValidator apiClientsValidator() {
    new ApiClientsValidator(validator: validator)
  }
}
