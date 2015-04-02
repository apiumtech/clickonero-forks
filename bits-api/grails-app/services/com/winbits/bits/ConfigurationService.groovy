package com.winbits.bits

import grails.util.GrailsUtil

//TODO: Mandar a un plugin para que sea reutilizable en cualquier domain, por ahora winbits y bits tienen su propia implementacion.
class ConfigurationService {

  def grailsApplication

  String getConfig(String code) {
    Configuration.findByCode(code)?.value ?: ''
  }

  def <T> T getConfigValue(String code, Class<T> type = String) {
    def configCode =  getConfigCode(code)
    def defaultValue =  grailsApplication.config.winbits.config.flatten()[code].asType(type)
    findConfigValue(configCode, type) ?: defaultValue 
  }

  private <T> T findConfigValue(String code, Class<T> type = String) {
    def configuration = Configuration.findByCode(code)
    if (configuration) {
      tryCastConfigValue(configuration, type)
    }
  }

  def <T> T tryCastConfigValue(Configuration configuration, Class<T> type = String) {
    try {
      configuration.value.asType(type)
    } catch(e) {
      log.warn("Invalid config value for key ${configuration.code}!", GrailsUtil.sanitizeRootCause(e))
    }
  }

  private String getConfigCode(String code) {
    "winbits.config.${code}"
  }


}
