package com.winbits.api.clients.merchants.sac

public interface MerchantsSacClient {
  Map getMerchantsOffers(String merchantId, String merchantName, String offerName)
  Map getOfferDetails(String offerId)
  Map redeemCoupon(String offerId, String couponNumber)
  Map getCouponsListByOffer(String offerId)
  Map getCouponsListByVenueEvent(String offerId, String skuId)
}