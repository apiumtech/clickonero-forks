package com.winbits.api.clients.mis.affiliation

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/24/13
 * Time: 7:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(MisSubscriptions.EVENT_NAME)
@Scope('prototype')
class MisSubscriptions extends  MisAffiliationBase{


  static final String EVENT_NAME ="mis-subscriptions"
    MisSubscriptions()
    {
         this.event = EVENT_NAME
    }

    enum TypePeriodicity
    {
        daily,
        weekly,
        biweekly,
        monthly,
    }

    enum TypeAction
    {
        created,
        update,
    }

    @NotNull
    TypePeriodicity periodicity



    Map subscriptions



    @NotNull
    TypeAction action
}
