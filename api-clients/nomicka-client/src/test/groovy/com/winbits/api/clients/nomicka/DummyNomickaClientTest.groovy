package com.winbits.api.clients.nomicka

import com.winbits.api.dummyclients.DummyNomickaClient
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created with IntelliJ IDEA.
 * User: pinky
 * Date: 8/19/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ['/app-ctx-dummy.xml'])

class DummyNomickaClientTest {

    @Autowired
    private DummyNomickaClient dummyNomickaClient

    @Test
     void dummyNomickaTest(){
        List<Long> ids= [51970, 51971, 51972, 51977]
        def response = dummyNomickaClient.stockNomicka(ids)
        assert response.response == [[id:123, stock:1, status:"ACTIVE"]]
    }
}
