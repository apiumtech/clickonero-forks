package com.winbits.api.clients.affiliation

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
@Ignore
class AffiliationClientTests {
  @Autowired
  private AffiliationClient affiliationClient

  @Test
  @Ignore
  void shouldGetLogin () {
    def response = affiliationClient.login ('test@winbits.com','s3cr3t0').response
    assert response
    assert response.meta.status == 200
    assert response.response.id == 1
    assert response.response.email == 'test@winbits.com'
    assert response.response.bitsBalance
    assert response.response.apiToken
    assert response.response.profile
  }
  
  @Test
  @Ignore
  void shouldFailLogin () {
    def response = affiliationClient.login ('fail@login.com','s3cr3t0').response
    assert response
    assert response.meta.status == 401
    assert response.meta.message
  }

 /* nomicka test*/

    @Test
    @Ignore
    void shouldGetLoginGivenANomickaUser () {
        def response = affiliationClient.login('nomicka@clickonero.com','KiMo1l0p4a77').response
        assert response
        assert response.meta.status != 200
        assert response.response.email != 'nomicka@clickonero.com'
        assert response.response.bitsBalance >= 0
        assert response.response.apiToken
        assert response.response.profile
    }

    @Test
    void shouldBringACouponInfo (){
        String apiToken = "hsvo9204e0e6THkkC42gAsCU8FXZ0u1cMbznpFMw5iqs6YoGy1zJZRGZ2BK3NiDa"
        Long cuponid = 7163L
        def response = affiliationClient.couponList(cuponid,apiToken).response
        assert response
        assert response.meta.status == 200
    }
    @Ignore
    @Test
    void shouldBringACoupon(){
        String apiToken = "5YkibE0yJ4dFRqe3QCzdZFd5zZj4WtVigoLYie56TvLZNhtJsAKE2FHdMk8ClGOF"
        def response = affiliationClient.couponId(32396,106564,"pdf",apiToken)
        assert response
        assert response.meta.status == 200


    }



}
