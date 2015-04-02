package com.winbits.api.clients.merchants.sac

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

@Component('merchantsSacClient')
class MerchantsSacClientImpl implements MerchantsSacClient {

  private static Logger log = LoggerFactory.getLogger(MerchantsSacClientImpl)

  @Autowired
  RESTClient merchantsSacRestClient

  @Override
  Map getMerchantsOffers(String merchantId, String merchantName, String offerName) {
    log.info("Get merchants offers for merchant id:${merchantId}, merchant name: ${merchantName}, offer name: ${offerName}")
    def response

    try {
      response = merchantsSacRestClient.get(path: 'merchant/offers', query: [providerId: merchantId, providerName: merchantName, offerName: offerName])
    } catch (e) {
      log.error("Error trying to get merchants offers", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  @Override
  Map getOfferDetails(String offerId) {
    log.info("Get offer details for merchant offer ${offerId}")
    def response

    try {
      response = merchantsSacRestClient.get(path: '/merchant/offerDetails', query: [offerId: offerId])
    } catch (e) {
      log.error("Error trying to get offer details", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  @Override
  Map redeemCoupon(String offerId, String couponNumber) {
    log.info("Redeem coupon ${couponNumber} from offer ${offerId}")
    def response

    try {
      response = merchantsSacRestClient.get(path: '/merchant/coupons/redeem', query: [offerId: offerId, couponNumber: couponNumber])
    } catch (e) {
      log.error("Error trying to redeem coupon", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  @Override
  Map getCouponsListByOffer(String offerId) {
    log.info("Get coupons list from merchant offer ${offerId}")
    def response

    try {
      response = merchantsSacRestClient.get(path: 'merchant/coupons/offer/download', query: [id: offerId])
    } catch (e) {
      log.error("Error trying to get the coupons list", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  @Override
  Map getCouponsListByVenueEvent(String offerId, String skuId) {
    log.info("Get coupons list of sku ${skuId} from merchant offer ${offerId}")
    def response

    try {
      response = merchantsSacRestClient.get(path: 'merchant/coupons/sku/download', query: [id: offerId, skuId: skuId])
    } catch (e) {
      log.error("Error trying to get the coupons list", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

}
