package com.winbits.api.clients.service

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
class ServiceClientTests {

  private static Logger log = LoggerFactory.getLogger(ServiceClientTests)

  @Autowired
  private ServiceClient serviceClient

  @Test
  void findServiceTest() {
    def result = serviceClient.findServiceByItemGroupId(10)
    log.info("FindCoupons result: ${result}")
    assert result
  }

  @Test
  void findService404ErrorTest() {
    def result = serviceClient.findServiceByItemGroupId(-14234324)
    log.info("Service not found: ${result}")
    assert result
    assert result.response.statusCode == 404
  }

}
