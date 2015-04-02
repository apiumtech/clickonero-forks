package com.winbits.api.clients.nomicka

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created with IntelliJ IDEA.
 * User: pinky
 * Date: 8/19/14
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx.xml'])
class NomickaClientTest {

    @Autowired
    private NomickaClient nomickaClient

    @Test
    void shouldGetSkus(){
      def response = nomickaClient.stockNomicka([51970,
              51971,
              51972,
              51977])
      assert response.skuList == null
    }

}
