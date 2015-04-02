package com.winbits.api.affiliation.config.social

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor

/**
 * Created with IntelliJ IDEA.
 * User: manuel
 * Date: 6/25/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
class SecurityConfig {

  @Bean
  TextEncryptor textEncryptor() {
    Encryptors.noOpText();
  }

}
