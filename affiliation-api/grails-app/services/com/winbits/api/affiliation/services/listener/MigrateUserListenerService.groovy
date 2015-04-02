package com.winbits.api.affiliation.services.listener

import com.winbits.domain.listeners.AbstractBaseQueueListener

class MigrateUserListenerService extends AbstractBaseQueueListener {

    static transactional = false
    static rabbitQueue = "migrateUserFromPartner"

    def migrateUserService

    @Override
    void processMessage(Map message) {
      def userId  = message?.userId
      if (userId) {
        migrateUserService.migrateUser(userId)
      }
    }

    @Override
    String rabbitQueue() {
      rabbitQueue
    }


}
