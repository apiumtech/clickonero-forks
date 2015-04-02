package com.winbits.api.clients.mis

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.winbits.api.clients.ApiClientsValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Client to consume Winbits' Management Information System API. Delegates to dummy implementation by default
 */
@Component('misClient')
class MisClientImpl<T extends MisBase> implements MisClient<T> {

  private static Logger log = LoggerFactory.getLogger(MisClientImpl)

  @Autowired
  ApiClientsValidator apiClientsValidator

  @Autowired
  Gson misGson

  @Autowired
  MisManagerSender misManagerSender

  @Override
  boolean logMessage(T obj) {
    boolean res = false
    def errors = apiClientsValidator.validate(obj)
    if (errors.hasErrors()) {
      log.error(errors.fieldErrors.collect { "${it.field} -> ${it.defaultMessage}" })
      res = false
    } else {
      def jsonObject = misGson.toJsonTree(obj) as JsonObject
      jsonObject.remove('module')
      res = misManagerSender.sendRabbitLog(jsonObject.toString())
    }
    res
  }
}
