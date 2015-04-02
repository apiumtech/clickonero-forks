package com.winbits.api

import com.winbits.domain.affiliation.User
import com.winbits.domain.tracking.Tracking
import com.winbits.domain.tracking.TrackingStepEnum
import org.springframework.transaction.annotation.Transactional

class TrackingDomainService {

  static transactional = false

  boolean validateUTMs(Map utms) {
    utms && utms.campaign && utms.medium
  }

  @Transactional
  Tracking createTracking(User user, TrackingStepEnum step, Map utms) {
    def tracking = Tracking.fromUTMs(utms)
    tracking.user = user
    tracking.step = step.domain
    tracking.save()
  }
}
