package com.winbits.api.dummyclients.mis

import com.winbits.api.clients.ApiClientsValidator
import com.winbits.api.clients.mis.MisBase
import com.winbits.api.clients.mis.MisClient
import com.winbits.api.clients.mis.MisManagerSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Client to consume Winbits' Management Information System API.
 */
@Component('misClient')
class DummyMisClient<T extends MisBase> implements MisClient<T> {

  private static Logger log = LoggerFactory.getLogger(DummyMisClient)

  @Autowired
  ApiClientsValidator apiClientsValidator

  MisManagerSender misManagerSender

  @Override
  boolean logMessage(T obj) {
    boolean res = false
    def errors = apiClientsValidator.validate(obj)
    if (errors.hasErrors()) {
      log.error errors.dump()
    } else {
      log.info('The message was logged in the MIS')
    }
    res
  }
}
