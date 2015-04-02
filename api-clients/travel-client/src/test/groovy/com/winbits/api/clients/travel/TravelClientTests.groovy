package com.winbits.api.clients.travel

import org.joda.time.LocalDate
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Test cases for CouponClient.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class TravelClientTests {

  private static Logger log = LoggerFactory.getLogger(TravelClientTests)

  @Autowired
  private TravelClient travelClient

  @Test
  void findTravelTest() {
    def result = travelClient.findTravelByItemGroupId(10)
    log.info("FindCoupons result: ${result}")
    assert result
  }

  @Test
  void findTravel404ErrorTest() {
    def result = travelClient.findTravelByItemGroupId(-14234324)
    log.info("Travel not found: ${result}")
    assert result
    assert result.response.statusCode == 404
  }

}
