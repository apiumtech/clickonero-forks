package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.tracking.TrackingStepEnum
import grails.converters.JSON
import org.springframework.transaction.annotation.Transactional

class TrackingService {

  static transactional = false

  def redisService
  def trackingDomainService

  def updateUserUTMs(User user, Map utms) {
    if (trackingDomainService.validateUTMs(utms)) {
      def utmsValue = (utms as JSON).toString()
      redisService.hset(user.apiToken, 'utms', utmsValue)
    }
  }

  @Transactional
  def trackRegister(User user, Map utms) {
    if (trackingDomainService.validateUTMs(utms)) {
      log.info('Tracking register...')
      trackingDomainService.createTracking(user, TrackingStepEnum.REGISTER, utms)
    }
  }

  @Transactional
  def trackLogin(User user, Map utms) {
    if (trackingDomainService.validateUTMs(utms)) {
      log.info('Tracking login...')
      trackingDomainService.createTracking(user, TrackingStepEnum.LOGIN, utms)
    }
  }

  @Transactional
  def trackFacebookRegister(User user, Map utms) {
    trackRegister(user, utms)
    trackLogin(user, utms)
  }
}
