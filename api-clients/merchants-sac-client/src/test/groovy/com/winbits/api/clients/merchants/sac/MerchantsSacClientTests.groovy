package com.winbits.api.clients.merchants.sac

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@Ignore
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = ['/app-ctx.xml'])
class MerchantsSacClientTests {

  @Autowired
  private MerchantsSacClient merchantsSacClient

  @Test
  void shouldNotReturnOffersMerchantId() {
    def response = merchantsSacClient.getMerchantsOffers("1001", null, null)
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers(null, "Test", null)
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersOfferName() {
    def response = merchantsSacClient.getMerchantsOffers(null, null, "Test")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersInvalidMerchantId() {
    def response = merchantsSacClient.getMerchantsOffers("Test", null, null)
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantIdWithWrongMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers("7", "Test", null)
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantIdWithWrongOfferName() {
    def response = merchantsSacClient.getMerchantsOffers("7", null, "Test")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantNameWithWrongOfferName() {
    def response = merchantsSacClient.getMerchantsOffers(null, "El Palacio de Hierro SA de CV", "Test")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersOfferNameWithWrongMerchantId() {
    def response = merchantsSacClient.getMerchantsOffers("1001", null, "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersOfferNameWithWrongMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers(null, "Test", "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantIdAndMerchantNameWithWrongOfferName() {
    def response = merchantsSacClient.getMerchantsOffers("7", "El Palacio de Hierro SA de CV", "Test")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantIdAndOfferNameWithWrongMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers("7", "Test", "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldNotReturnOffersMerchantNameAndOfferNameWithWrongMerchantId() {
    def response = merchantsSacClient.getMerchantsOffers("1001", "El Palacio de Hierro SA de CV", "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 0
  }

  @Test
  void shouldReturnOffersMerchantId() {
    def response = merchantsSacClient.getMerchantsOffers("7", null, null)
    assert response

    def offersList = response.response
    assert offersList.size() == 158
  }

  @Test
  void shouldReturnOffersMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers(null, "El Palacio de Hierro SA de CV", null)
    assert response

    def offersList = response.response
    assert offersList.size() == 158
  }

  @Test
  void shouldReturnOffersOfferName() {
    def response = merchantsSacClient.getMerchantsOffers(null, null, "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 1
  }

  @Test
  void shouldReturnOffersMerchantIdAndMerchantName() {
    def response = merchantsSacClient.getMerchantsOffers("7", "El Palacio de Hierro SA de CV", null)
    assert response

    def offersList = response.response
    assert offersList.size() == 158
  }

  @Test
  void shouldReturnOffersMerchantIdAndOfferName() {
    def response = merchantsSacClient.getMerchantsOffers("7", null, "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 1
  }

  @Test
  void shouldReturnOffersMerchantNameAndOfferName() {
    def response = merchantsSacClient.getMerchantsOffers(null, "El Palacio de Hierro SA de CV", "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 1
  }

  @Test
  void shouldReturnOffersMerchantIdAndMerchantNameAndOfferName() {
    def response = merchantsSacClient.getMerchantsOffers("7", "El Palacio de Hierro SA de CV", "Black Ventilader")
    assert response

    def offersList = response.response
    assert offersList.size() == 1
  }

  @Test
  void shouldGetOfferDetails() {
    def response = merchantsSacClient.getOfferDetails("299")
    assert response

    def offerDetails = response.response
    assert offerDetails.id == 299
    assert offerDetails.nombre == "P1DI003"
    assert offerDetails.nombreProveedor == "ProModa"
    assert offerDetails.proyeccion == 345
    assert offerDetails.fraudulentos == 0
    assert offerDetails.comprados == 61
    assert offerDetails.idProveedor == 1
    assert offerDetails.redimidos == 3
    assert offerDetails.fechaFin == "2013-12-14T00:00:00Z"
    assert offerDetails.fechaInicio == "2013-10-20T22:00:00Z"
    assert offerDetails.mapaCupones == [
        "My Looq":
            [
                vertical:"My Looq",
                comprados:61,
                redimidos:3,
                fraude:0
            ]
    ]
    assert offerDetails.listaVenueEvent == [
        [
            stock:150,
            event:"Talla",
            skuId:791,
            vendidos:18,
            venue:"Negro",
            dealId:299,
            descEvent:"Small"
        ],
        [
            stock:150,
            event:"Talla",
            skuId:792,
            vendidos:31,
            venue:"Negro",
            dealId:299,
            descEvent:"Med"
        ],
        [
            stock:150,
            event:"Talla",
            skuId:793,
            vendidos:12,
            venue:"Negro",
            dealId:299,
            descEvent:"Large"
        ]
    ]
  }

  @Test
  void shouldNotGetOfferDetailsInvalidOfferId() {
    def response = merchantsSacClient.getOfferDetails("abcde")
    assert response

    def offerDetails = response.response
    assert offerDetails == null
  }

  @Test
  void shouldNotGetOfferDetailsOfferId() {
    def response = merchantsSacClient.getOfferDetails("99999999")
    assert response

    def offerDetails = response.response
    assert offerDetails == null
  }

  @Test
  void shouldNotGetOfferDetailsNegativeOfferId() {
    def response = merchantsSacClient.getOfferDetails("-1")
    assert response

    def offerDetails = response.response
    assert offerDetails == null
  }

  @Test
  void shouldNotRedeemRedeemedCoupon() {
    def response = merchantsSacClient.redeemCoupon("299", "2#xwxz9m-z5c")
    assert response

    def responseCode = response.response
    assert responseCode.response == 906
  }

  @Test
  void shouldNotRedeemInvalidCoupon() {
    def response = merchantsSacClient.redeemCoupon("299", "asdfgh")
    assert response

    def responseCode = response.response
    assert responseCode.response == 902
  }

  @Test
  void shouldNotRedeemCouponFromDifferentOffer() {
    def response = merchantsSacClient.redeemCoupon("199", "2#xwxz9m-z5c")
    assert response

    def responseCode = response.response
    assert responseCode.response == 903
  }
}
