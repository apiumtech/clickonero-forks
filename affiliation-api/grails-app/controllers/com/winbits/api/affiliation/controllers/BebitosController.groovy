package com.winbits.api.affiliation.controllers

import com.winbits.domain.sms.UserMobile
import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured

class BebitosController {

  def bebitosService


    @Secured(['IS_AUTHENTICATED_FULLY'])
    Map orders(BebitosCommand cmd) {
        if(cmd.hasErrors() || !cmd.validate()){
            throw new EntityValidationException('Error!! Need collection name and email')
        }
        else{
            def user = getAuthenticatedUser()
            restpond bebitosService.findHistoryByEmail(cmd.collectionName,cmd.email,cmd.clickoneroId,cmd.path)
        }

    }

}
class BebitosCommand {

    String email
    String collectionName
    String clickoneroId
    String path



    static constraints = {
        email nullable: false
        collectionName nullable: false
        clickoneroId nullable:true
        path nullable:true
    }
}


