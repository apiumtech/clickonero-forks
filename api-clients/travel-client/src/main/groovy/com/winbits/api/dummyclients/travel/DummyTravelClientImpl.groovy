package com.winbits.api.dummyclients.travel

import com.winbits.api.clients.travel.TravelClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component('travelClient')
class DummyTravelClientImpl implements TravelClient {
  private static Logger log = LoggerFactory.getLogger(DummyTravelClientImpl)


  @Override
  Map findTravelByItemGroupId(Long id) {
    log.info("Find travel with item group id ${id}")
    def response = [requireCoupon:true]

    [response: response]
  }
}
