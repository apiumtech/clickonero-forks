package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.UserBadCredentialsException
import com.winbits.api.affiliation.exception.UserNotConfirmedException
import com.winbits.api.clients.bebitos.BebitosClient
import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.VerticalPartner


class MultipleLoginService {

    BebitosClient bebitosClient

    static transactional = false

    def loginService
    def bebitosService
    def registerService
    def messagingService
    def migrationService


    def login(String email, String password) {
        def user = User.findByEmail(email)
        def hasBeenUserMigratedfromBebitos =false;
        def userResponse
        if(isDontNeedMigrate(user)) {  //Está en winbits  pero no se sabe si está en bebitos
            log.info(user.email+" Está en winbits  pero no se sabe si está en bebitos")
            user.metaClass.isMigrateUser = false
            validateUserEnabled(user)
            try
            {    //Si el usuario está bloqueado no se hace la migraciòn
                if(!user.accountLocked){
                    hasBeenUserMigratedfromBebitos =migrateHistoryFromBebitosifIsNecesary(user,email, password)
                    if(hasBeenUserMigratedfromBebitos==true){
                        messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
                    }
                }else if(user.lastLogin==null){
                    hasBeenUserMigratedfromBebitos =migrateHistoryFromBebitosifIsNecesary(user,email, password)
                    if(hasBeenUserMigratedfromBebitos==true){
                        messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
                        user.setAccountLocked(false)
                        user.save()
                    }
                }

            }catch(Exception E){E.printStackTrace()}

            try{
                userResponse=loginService.login(email, password)
                userResponse?.metaClass?.isMigrateUser = false
            }catch(UserNotConfirmedException e){

                if(hasBeenUserMigratedfromBebitos)  {
                    activateUser(user)
                    userResponse=loginService.login(email, password)
                    userResponse.metaClass.isMigrateUser = false
                }else{
                    throw e
                }
            }

            if(userResponse){
                userResponse.metaClass.hasBeenUserMigratedfromBebitos=hasBeenUserMigratedfromBebitos;
            }
            userResponse
        } else if (user) {//El usuario está en winbits pero no está completo por lo que se requiere migrar los datos faltantes de clickonero
            log.info(user.email+": El usuario está en winbits pero no está completo por lo que se requiere migrar los datos faltantes de clickonero")
            migrateClickoneroUser(user)
            user.metaClass.isMigrateUser = true
            try{
                hasBeenUserMigratedfromBebitos =migrateHistoryFromBebitosifIsNecesary(user,email, password)
            }catch(Exception E){}
            try{
                userResponse=loginService.login(email, password)
            }catch(UserNotConfirmedException e){
                //El usuario no se ha confirmado en winbits pero si lo trajimos de bebitos, permitimos que pase con su cuenta
                if(hasBeenUserMigratedfromBebitos)  {
                    activateUser(user)
                    userResponse=loginService.login(email, password)
                }else{
                    throw e
                }
            }
            if(userResponse){
                userResponse.metaClass.hasBeenUserMigratedfromBebitos=hasBeenUserMigratedfromBebitos;
            }
            userResponse
        } else { //El usuario está en bebitos o no está registrado
            user=migrateBebitosUser(email, password)
            hasBeenUserMigratedfromBebitos=Boolean.TRUE
            try{
                user.metaClass.isMigrateUser = false
                //Migrar su historial mediante rabbit
                messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
                userResponse=loginService.login(email, password)
                if(userResponse){
                    userResponse.metaClass.hasBeenUserMigratedfromBebitos=hasBeenUserMigratedfromBebitos;
                }
                userResponse
            }catch(UserBadCredentialsException ube){
                throw new UserBadCredentialsException(user)
            }catch(NullPointerException npe){
                throw new UserBadCredentialsException(user)
            }
        }
    }

    boolean migrateHistoryFromBebitosifIsNecesary(user,String email, String password){
        def isbebitosmigrated=false
        def couldUserBeAtBebitos=true
        //Verificamos si se ha migrado previamente de bebitos
        def verticalPartnerList = VerticalPartner.findAllByUser(user)
        if(verticalPartnerList) {
            verticalPartnerList?.each { verticalPartner ->
                if( verticalPartner.vertical.name.equals("Bebitos") && verticalPartner.status!=MigrationStatusEnum.TRIED) {
                    isbebitosmigrated=true
                }
                else if( verticalPartner.vertical.name.equals("Bebitos") && verticalPartner.status==MigrationStatusEnum.TRIED) {
                    couldUserBeAtBebitos=false
                }
            }
        }


        if(isbebitosmigrated==false && couldUserBeAtBebitos==true){  //No se tiene registro de migración desde bebitos. Lo buscamos en bebitos
            def userbebitosresponse

            try{
                userbebitosresponse=   bebitosClient.userDetail(email)//Lo buscamos por email porque podrían no coincidir los passwords
                def statusCode=userbebitosresponse.response?.statusCode


                if(statusCode && statusCode == 200) {
                    log.info("Creamos VERTICALPARTNER")
                    def VP=new VerticalPartner(user: user, vertical: bebitosService.getVertical(), status: MigrationStatusEnum.PENDING.domain).save()
                    isbebitosmigrated=true
                    log.info("VERTICALPARTNER: "+ VP.save())
                    log.info("Publicamos el mensaje")
                    sleep(500)
                    messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
                }
                else if(statusCode && statusCode == 422) {
                    try{
                        new VerticalPartner(user: user, vertical: bebitosService.getVertical(), status: MigrationStatusEnum.TRIED.domain).save()
                    }catch(Exception E){}

                }
            }catch(Exception E){}
        }
        isbebitosmigrated
    }

    def validateUserEnabled(user) {
        User.withTransaction {
            if( user.profile() && !user.enabled ){
                user.enabled = true
                user.save()
            }
        }
    }

    def User migrateBebitosUserByEmail(email) {
        def user = bebitosService.migrateUserByEmail(email)
        activateUser(user)
        user
    }

    private User migrateBebitosUser(email, password) {
        def user = bebitosService.migrateUser(email, password)
        activateUser(user)
        user
    }

    boolean isDontNeedMigrate(User user) {
        user && (user?.profile() || user?.salt || user?.enabled)
    }

    void migrateClickoneroUser(User user){
        registerService.activateUserWithoutSalt(user)
        migrationService.obtainProfile(user)
    }


    private activateUser(user) {
        user ? registerService.activate(user.salt) : null
    }

    private loginByPartnerUser(User user, String email, String password) {
        if (user) {
            messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
            loginService.login(email, password)
        } else {
            throw new UserBadCredentialsException(new User(email: email) )
        }
    }
}
