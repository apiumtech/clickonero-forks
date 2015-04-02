package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.UpdateSubscriptionsCommand
import com.winbits.api.clients.mis.affiliation.MisSubscriptions
import com.winbits.domain.affiliation.NewsletterPeriodicity
import com.winbits.domain.affiliation.Subscription
import com.winbits.domain.affiliation.User

class SubscriptionService {
  def verticalService
  def misClient


  List getSubscriptions(User user) {
    mapSubscriptions(user.subscriptions())
  }

  private List mapSubscriptions(subscriptions) {
    def result = []
    verticalService.getAll().findAll { it.active == true && it.name != '_Test_' }.each {
      result << it.toSubscriptionMap(subscriptions)
    }
    result
  }

  List updateSubscriptions(User user, UpdateSubscriptionsCommand command) {
    def profile = user.profile()
    command.updateProfile(profile)
    profile.subscriptions.each {
      it.delete()
    }
    profile.subscriptions.clear()

    def newSubscriptions = command.subscriptions.findAll { it.active }
    def subscriptions = []
    newSubscriptions.each { subscriptionCommand ->
      def vertical = verticalService.byId(subscriptionCommand.id)
      def subscription = new Subscription(vertical: vertical, profile: profile).save()
      subscriptions << subscription
      profile.addToSubscriptions(subscription)
    }
    profile.save()
    def subscriptionResult = mapSubscriptions(subscriptions)

    def subEventMap = [:]
    subscriptionResult.each { subVertMap ->
      subEventMap.put(subVertMap.name, subVertMap.active)
    }
    def eventMap = user.toEventBase() + [periodicity: obtainPeriodicity(command), subscriptions: subEventMap, action: MisSubscriptions.TypeAction.update]
    misClient.logMessage(new MisSubscriptions(eventMap))
    subscriptionResult
  }

  private MisSubscriptions.TypePeriodicity obtainPeriodicity(UpdateSubscriptionsCommand command) {
    def periodicity = command.newsletterPeriodicity ?: NewsletterPeriodicity.defaultPeriodicity()
    periodicity == NewsletterPeriodicity.daily ? MisSubscriptions.TypePeriodicity.daily : MisSubscriptions.TypePeriodicity.weekly
  }
}
