package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.SubscriptionCommand
import com.winbits.api.affiliation.controllers.UpdateSubscriptionsCommand
import com.winbits.domain.affiliation.NewsletterPeriodicity
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.Subscription
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/22/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionServiceIntegrationTests extends GroovyTestCase {
  def subscriptionService

  def testUpdateSubscription() {
    def count = Subscription.count
    def verticalId = 1
    def profile = Profile.get(1)
    def subscriptions = [new SubscriptionCommand(id: verticalId, active: true), new SubscriptionCommand(id: 2, active: false)]
    def command = new UpdateSubscriptionsCommand(newsletterPeriodicity: NewsletterPeriodicity.daily)
    command.metaClass.subscriptions = subscriptions
    def subscription = new Subscription(vertical: Vertical.get(verticalId), profile: profile).save(failOnError: true)
    profile.addToSubscriptions(subscription)
    profile.save(failOnError: true)
    profile.save()

    def result = subscriptionService.updateSubscriptions(profile.user, command)
    assert (count + 1) == Subscription.count
    assert command.newsletterPeriodicity == profile.newsletterPeriodicity
    assert result
  }
}
