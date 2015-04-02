package com.winbits.domain.config

import com.winbits.domain.Configuration
import grails.util.GrailsUtil

class ConfigurationService {

  def grailsApplication

  String getConfig(String code) {
    Configuration.findByCode(code)?.value ?: ''
  }

  def <T> T getConfigValue(String code, Class<T> type = String) {
    def configCode = getConfigCode(code)
    findConfigValue(configCode, type) ?: grailsApplication.config.winbits.config.flatten()[code].asType(type)
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
