package com.winbits.api.clients.coupon

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
class CouponClientTests {

  private static Logger log = LoggerFactory.getLogger(CouponClientTests)

  @Autowired
  private CouponClient couponClient

  @Test
  void findCouponsTest() {
    def result = couponClient.findCouponsByOrderDetailId(357)
    log.info("FindCoupons result: ${result}")
    assert result

  }

  @Test(expected=wslite.rest.RESTClientException)
  void findCoupons404ErrorTest() {
    couponClient.findCouponsByOrderDetailId(-2132213454543)
  }

  @Test
  void getCouponUrlTest() {
    def result = couponClient.getCouponById(4, 'jpg', 197, 21)
    log.info("Get coupon url result: ${result}")
    assert result
  }

  @Test
  void getCouponUrl404ErrorTest() {
    def result = couponClient.getCouponById(-13213213213, 'ewqewqeqwe', 197, 21)
    log.info("Coupon not found: ${result}")
    assert result
    assert result.response.statusCode == 404
  }


  @Test
  void getCouponUrl405ErrorTest() {
    def result = couponClient.getCouponById(3, 'jpg', 197, 21)
    log.info("Coupon not available: ${result}")
    assert result
    assert result.response.statusCode == 405
  }
}
