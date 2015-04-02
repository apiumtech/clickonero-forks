package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.exception.*
import com.winbits.api.clients.mailing.MailingClient
import com.winbits.api.clients.mailing.data.ResetPasswordEmailData
import com.winbits.api.clients.mis.affiliation.ChangePasswordEvent
import com.winbits.api.clients.mis.affiliation.MisLogin
import com.winbits.api.clients.mis.affiliation.MisRecoverPassword
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class LoginService {
  def springSecurityService
  AuthenticationManager authenticationManager
  def userService
  def grailsLinkGenerator
  def grailsApplication
  MailingClient mailingClient
  def authenticationService
  def misClient
  def multipleLoginService
  def migrationService
  def messagingService

  User login(String username, String password) {
    log.debug "Logged"
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password)
    User details = new User(email: username)
    token.setDetails(details)
    try {
      Authentication auth = authenticationManager.authenticate(token)
      log.debug("Login succeeded!")
      SecurityContextHolder.getContext().setAuthentication(auth)
    } catch (BadCredentialsException e) {
      throw new UserBadCredentialsException(details)
    } catch (DisabledException e) {
      dealWithDisabledException(details, password)
    }
    def user = User.findByEmail(username)
    //Fix para poder autenticar los usuarios migrados de clickonero los cuales no tienen salt.
    if (!user.salt) {
      user.password = password
      user.generateSalt()
    }
    user.generateApiToken()
    user.updateLastLogin()
    def loginEvent = new MisLogin(userName: username, date: new Date(), userId: user.id, vertical: user.verticalName())
    misClient.logMessage(loginEvent)
    user.save()
  }

  private dealWithDisabledException(User details, String password) {
    def user = User.findByEmail(details.email)
    def userPassword = springSecurityService.encodePassword(password, user.salt)
    if (userPassword == user.password) {
      throw new UserNotConfirmedException(details)
    } else {
      throw new UserBadCredentialsException(details)
    }
  }

  User loginFacebookToken(String facebookToken) {
    def user = User.findByFacebookToken(facebookToken)
    if (!authenticationService.isAuthenticated(user) || user.accountLocked) {
      throw new UserBadCredentialsException(user)
    }
    user.generateApiToken()
    user.updateLastLogin()
    user.save()
    try{
       def hasBeenUserMigratedfromBebitos =multipleLoginService.migrateHistoryFromBebitosifIsNecesary(user,user.email, user.password)
       if(hasBeenUserMigratedfromBebitos==true){
           messagingService.publishMessage("migrateUserFromPartner", [userId: user.id])
       }
    }catch(Exception E){log.info("Error al migrar bebitos: "+E.getMessage())}
    user
  }

  def recover(String email, Long verticalId) {
    User user = userService.byEmail(email)
    if (user) {
      def event = new MisRecoverPassword(status: toStatus(user), action: MisRecoverPassword.TypeAction.sendEmail,
          userName: user.email, date: new Date(), userId: user.id, vertical: user.verticalName())
      misClient.logMessage(event)
      if (!user.accountLocked) {
        if (user.enabled) {
          sendEmailForgotPassword(user, verticalId)
        } else {
          throw new UserInactiveException()
        }
      } else {
        throw new UserLockedException()
      }
    } else {
      throw new EmailNotFoundException()
    }
  }

  private MisRecoverPassword.StatusUser toStatus(User user) {
    user.accountLocked ? MisRecoverPassword.StatusUser.block : toStatusNormalOrDisabled(user)
  }

  private MisRecoverPassword.StatusUser toStatusNormalOrDisabled(User user) {
    return user.enabled ? MisRecoverPassword.StatusUser.normal : MisRecoverPassword.StatusUser.disabled
  }

  User updatePassword(String hash, String password) {
    def user = User.findBySalt(hash)
    if (user) {
      user.generateSalt()
      user.password = password
      def event = new MisRecoverPassword(status: toStatus(user), action: MisRecoverPassword.TypeAction.resetPassword,
          userName: user.email, date: new Date(), userId: user.id, vertical: user.verticalName())
      misClient.logMessage(event)
      user.save()
    } else {
      throw new ResetCodeExpiredException()
    }
  }

  def sendEmailForgotPassword(User user, Long verticalId) {
    def vertical = Vertical.get(verticalId)
    def crc = authenticationService.createCRCintoRedis()
    def salt = user.salt
    def saltCrc = salt + crc
    def link = "${vertical.baseUrl}#wb-reset-password-${saltCrc}"
    trySendPasswordResetEmail(new ResetPasswordEmailData(email: user.email, resetUrl: link))
  }

  def trySendPasswordResetEmail(ResetPasswordEmailData data) {
    try {
      mailingClient.sendEmail(data)
    } catch (e) {
      log.warn('The password reset email was not send!')
    }
  }

  void changePassword(User user, String password, String newPassword) {
    if (user.facebookToken) {
      user.password = newPassword
      user.save()
    } else {
      changeNoFacebookUserPasword(user, password, newPassword)
    }
    def loginEvent = new ChangePasswordEvent(userName: user.email, userId: user.id)
    if (user.salesAgentId)
      loginEvent.salesAgentId = user.salesAgentId
    log.info "changePasswordEvent -> ${loginEvent.properties}"
    misClient.logMessage(loginEvent)
  }

  private void changeNoFacebookUserPasword(User user, String password, String newPassword) {
    def userPassword = springSecurityService.encodePassword(password, user.salt)
    if (user.password == userPassword) {
      user.password = newPassword
      user.save()
    } else {
      throw new PasswordNoMatchException('Cannot set new password if provided previous password do not match')
    }
  }
}
