package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.SkuProfile
import org.hibernate.criterion.CriteriaSpecification

class WaitingListNotifierService {

  def notifyUsersWhenSkuProfileIsAvailable(SkuProfile skuProfile) {
    def users = getUsersBySkuProfileInTheirWaitingList(skuProfile)
    //TODO: Se invoca el notificador mediante rabbit
  }

  def getUsersBySkuProfileInTheirWaitingList(SkuProfile skuProfile) {
    User.withCriteria {
      createAlias("waitingListItems", "wl", CriteriaSpecification.LEFT_JOIN)
      eq("wl.skuProfile", skuProfile)
    }
  }
}
