package com.winbits.api.clients.mis.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 3/03/14
 * Time: 06:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class MisConfig {

  @Bean
  public Gson misGson() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
  }
}
