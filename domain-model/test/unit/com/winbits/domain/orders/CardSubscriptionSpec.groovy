package com.winbits.domain.orders

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.winbits.domain.affiliation.User

import grails.test.mixin.Mock

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CardSubscription)
@Mock(User)
class CardSubscriptionSpec extends Specification {
        def user

	def setup() {
            user = new User()
	}

	def cleanup() {
	}

	void "test asocia usuario a cardSubscription"(){
            when:
                CardSubscription card = new CardSubscription(subscriptionId: "subscription1", 
                    user:user).save()
               
               CardSubscription card2 = new CardSubscription(subscriptionId: "subscription2",
                    user:user).save()


            then:
                assert card
                assert card.user == user
                assert card2.user == user
	}
}
