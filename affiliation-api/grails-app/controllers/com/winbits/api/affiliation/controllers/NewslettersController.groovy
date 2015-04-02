package com.winbits.api.affiliation.controllers

import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.domain.affiliation.User

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 3/03/15
 * Time: 01:15 PM
 * To change this template use File | Settings | File Templates.
 */

import com.winbits.domain.sms.UserMobile
import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured


class NewslettersController {

    def registerService
    def respuesta =[response:'error']
    @Secured(['IS_AUTHENTICATED_FULLY'])
    Map addBebitosNewsletter(NewsletterCommand cmd) {
        if(cmd.hasErrors() || !cmd.validate()){
            throw new EntityValidationException('Error!! Need email number')
        }
        else{
            def user = User.findByEmail(cmd.email)
            //La vertical
            log.info("Bienvenida Alterna Bebitos")
            registerService.trySendEmail(new ConfirmWelcomeData(email: user.email, idUserweb: user.id, vertical: "BebitosAlterno"))
            restpond 'Ok'
        }
        restpond respuesta
    }
}
class NewsletterCommand{
    String email
    static constraints = {
        email nullable: false
    }
}