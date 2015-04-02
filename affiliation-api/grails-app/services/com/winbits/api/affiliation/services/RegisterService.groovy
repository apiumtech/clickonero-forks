package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.FacebookCommand
import com.winbits.api.affiliation.controllers.RegisterCommand
import com.winbits.api.affiliation.exception.*
import com.winbits.api.clients.mailing.data.BaseEmailData
import com.winbits.api.clients.mailing.data.ConfirmAccountData
import com.winbits.api.clients.mailing.data.ConfirmWelcomeData
import com.winbits.api.clients.mis.affiliation.MisLoginSocialMedia
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.exceptions.api.client.EntityNotExistsException
import org.apache.commons.lang.RandomStringUtils
import org.hibernate.StaleObjectStateException
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.transaction.annotation.Transactional

class RegisterService {

    def grailsApplication
    def verticalService
    def mailingClient
    def bitsService
    def misClient
    def multipleLoginService
    def messagingService

    User register(RegisterCommand command) {
        verifyExistingUser(command.email)
        def user = initUser(command)
        user.password = command.password
        user.save()
        def confirmUrl = user.confirmUrl()
        log.info "CONFIRM URL -> ${confirmUrl}"
        trySendEmail(new ConfirmAccountData(email: user.email, IdUserweb: user.id,
                UrlConfirm: confirmUrl, firm: user.email.encodeAsBase64(),vertical: user.verticalName()))
        user
    }

    def trySendEmail(BaseEmailData data) {
        try {
            mailingClient.sendEmail(data)
        } catch (e) {
            log.warn("The ${data.key} email was not send!")
        }
    }

    def verifyReferredCode(String referredCode) {
        User user = User.findByReferrerCodeAndEnabled(referredCode, true)
        if (!user) {
            throw new ReferredCodeInvalidException()
        }
    }

    private void verifyExistingUser(String email) {
        def user = User.findByEmail(email)
        if (user) {
            if (user.enabled) {
                throw new UserAlreadyExistsException(email)
            } else {
                throw new UserAlreadyExistsNotEnableException([resendConfirmUrl:'/users/resend/'+email])
            }
        }
    }

    private void validateUserStatus4Activation(User user){
        if (!user) {
            throw new EntityNotExistsException(salt, "User")
        }

        if (user.enabled) {
            throw new UserAlreadyActivatedException(user)
        }
    }

    User activate(String salt) {
        def user = User.findBySalt(salt, [fetch: [vertical: 'join']])
        validateUserStatus4Activation (user)

        def profile = new Profile(locale: grailsApplication.config.winbits.default.locale,
                completeProfileRemainders: 0, completeRegister:false
        )
        activateUser(user, profile)
    }


    User activateUserWithoutSalt (User user){
        validateUserStatus4Activation (user)
        def profile = new Profile(locale: grailsApplication.config.winbits.default.locale)
        activateUser(user, profile,false)
    }

    private User activateUser(User user, Profile profile, boolean sendConfirmMail = true) {
        setupActiveProfile(profile, user)
        user.enabled = true
        user.generateApiToken()
        if (sendConfirmMail) {
            trySendEmail(new ConfirmWelcomeData(email: user.email, idUserweb: user.id, vertical: user.verticalName()))
        }
        user.save()
    }

    private Profile setupActiveProfile(Profile profile, User user) {
        try {
            profile.bitsId = bitsService.createAccount()
        }catch(e) {
            log.error "BitsApiNotAvailableException", e
            throw new BitsApiNotAvailableException(user)
        }
        user.addProfile(profile)
    }

    private User facebookRegister(FacebookCommand command) {
        verifyExistingUser(command.email)
        User user = initUser(command)
        user.password = RandomStringUtils.random(10, true, true)
        user.facebookToken = command.providerUserId
        user.save()
        def profile = command.toProfile()
        activateUser(user, profile, false)
        trySendEmail(new ConfirmWelcomeData(email: user.email, idUserweb: user.id, name: profile.name,
                lastname: profile.lastName, genre: profile.gender, birthdate: profile.birthdate?.toString('yyyy-MM-dd'),
                postcode: profile.zipCode, locality: profile.location, phone: profile.phone, vertical: user.verticalName()))
        user
    }

    private User initUser(command) {
        Vertical vertical = verticalService.byId(command.verticalId)
        def user = new User(email: command.email, vertical: vertical, referredBy: command.referredBy)
        user.referrerCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase()
//    user.generateApiToken()
        user
    }

    void resendConfirmationMail(String email) {
        def user = User.findByEmail(email)
        if (!user) {
            throw new EntityNotExistsException(email, "User")
        }
        if (user.enabled) {
            throw new UserAlreadyConfirmedException(email)
        }
        def confirmUrl = user.confirmUrl()
        trySendEmail(new ConfirmAccountData(email: user.email, IdUserweb: user.id,
                UrlConfirm: confirmUrl, firm: user.email.encodeAsBase64(),vertical: user.verticalName()))
    }

    def facebookLogin(FacebookCommand command) {
        def user = User.findByEmail(command.email)
        def existing = false
        if (user) {
            user.generateApiToken()
            user.facebookToken = command.providerUserId
            existing = true
            user.save()
            def loginEvent = new MisLoginSocialMedia(userName: user.email, userId: user.id, vertical: user.verticalName(), socialMediaName: 'Facebook')
            misClient.logMessage(loginEvent)
        } else {
            user = facebookRegister(command)
        }
        [user, existing]
    }

    User saveFacebookProfile(FacebookProfile fbProfile, Long verticalId, String referredBy) {
        Vertical vertical = verticalService.byId(verticalId)
        def user = new User(email: fbProfile.email, vertical: vertical)
        user.referrerCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase()
        user.password = RandomStringUtils.random(10, true, true)
        user.facebookToken = fbProfile.id
        user.referredBy = referredBy
        user.save()
        def profile = facebookProfiletoProfile(fbProfile)
        activateUser(user, profile)
        def hasBeenUserMigratedfromBebitos =multipleLoginService.migrateHistoryFromBebitosifIsNecesary(user,user.email, user.password)
        trySendEmail(new ConfirmWelcomeData(email: user.email, idUserweb: user.id, name: profile.name,
                lastname: profile.lastName, genre: profile.gender, birthdate: profile.birthdate?.toString('yyyy-MM-dd'),
                postcode: profile.zipCode, locality: profile.location, phone: profile.phone,vertical: user.verticalName()))
        if(hasBeenUserMigratedfromBebitos==true){
            messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
        }
        user
    }

    def updateUserToken(User user, FacebookProfile  fbProfile) {
        user.facebookToken = fbProfile?.id
        if (!user.enabled){
            user.password = RandomStringUtils.randomAlphanumeric(10).toUpperCase()
            activateUser(user, facebookProfiletoProfile(fbProfile))
        }
        def loginEvent = new MisLoginSocialMedia(userName: user.email, date: new Date(),
                userId: user.id, vertical: user.verticalName(), socialMediaName: 'Facebook')
        misClient.logMessage(loginEvent)
        trySaveUser(user)
    }

    private User trySaveUser(User user, int maxAttempts = 5) {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                user.save()
                break
            } catch (StaleObjectStateException e) {
                user.refresh()
            }
        }
    }

    Profile facebookProfiletoProfile(FacebookProfile fbProfile) {
        def locale = grailsApplication.config.winbits.default.locale
        new Profile(name: fbProfile.firstName, lastName: fbProfile.lastName,
                birthdate: fbProfile.birthday ?
                    DateTimeFormat.forPattern("MM/dd/yyyy").parseLocalDate(fbProfile.birthday) : null,
                gender: fbProfile.gender, locale: locale)
    }

    @Transactional(readOnly = true)
    def determineStatus(User user) {
        user.dateCreated.plusSeconds(20).isAfterNow() ? 201 : 200
    }

    User getUserByEmail(String email){
        User.findByEmail(email)
    }

    User getUserFacebookLogin(String email, FacebookProfile facebookProfile, referredBy, verticalId){
        def user = getUserByEmail(email)
        if(user)
            updateUserToken(user, facebookProfile)
        else
            user = saveFacebookProfile(facebookProfile, verticalId, referredBy)
        user
    }
}
