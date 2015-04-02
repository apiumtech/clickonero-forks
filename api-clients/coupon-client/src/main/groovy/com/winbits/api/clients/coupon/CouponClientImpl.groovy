package com.winbits.api.clients.coupon

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('couponClient')
class CouponClientImpl implements CouponClient {

  private static Logger log = LoggerFactory.getLogger(CouponClientImpl)

  @Autowired
  RESTClient couponRestClient

  @Override
  Map findCouponsByOrderDetailId(Long id) {
    log.info("Find coupons for order detail with id ${id}")
    def response = couponRestClient.get(path: "coupons/${id}")
    [coupons: response.json]
  }

  @Override
  Map getCouponById(Long id, String format, Long orderId, Long userId) {
    log.info("Get coupon file with id ${id}")
    def response
    try { 
      response = couponRestClient.get(
      path: "coupon/${id}?format=${format}&orderId=${orderId}&userId=${userId}")
    } catch(e) {
        log.error("Error when getting a coupon", e)
        response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response]
  }


}
