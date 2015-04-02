package com.winbits.api.affiliation.controllers.resources

import com.winbits.api.affiliation.exception.VerticalNotActiveException
import com.winbits.domain.affiliation.Vertical
import com.winbits.exceptions.api.client.EntityValidationException

class VerticalsResourceController {
  
  def show(String hostname) {
    if (!hostname) {
      throw new EntityValidationException('You must provide a "hostname" parameter!')
    }
    def verticals = Vertical.findAllByActive(true, [sort: 'order'])
    def vertical = verticals.find {
      new URL(it.baseUrl).host == hostname
    }
    if (!vertical) {
      throw new VerticalNotActiveException(hostname)
    }
    restpond data: verticals, meta: [currentVerticalId: vertical.id]
  }

  def showHome(String hostname){
    if (!hostname) {
      throw new EntityValidationException('You must provide a "hostname" parameter!')
    }
    def verticals = Vertical.findAll([sort: 'order'])
    def vertical = verticals.find {
      new URL(it.baseUrl).host == hostname
    }
    if (!vertical) {
      throw new VerticalNotActiveException(hostname)
    }
    log.info "Check home url in database"
    restpond data: verticals, meta: [currentVerticalId: vertical.id]
  }

}
