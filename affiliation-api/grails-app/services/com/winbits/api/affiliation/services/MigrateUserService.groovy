package com.winbits.api.affiliation.services

import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.VerticalPartner

class MigrateUserService {

  def bebitosService
  static String CLICKONERO_VERTICAL_NAME = "clickOnero"
  def migrationService


  def migrateUser(Long userId) {
    def user = User.get(userId)
    log.info "entraaa a migrar usuario $userId  --- user:  $user"
    if(user) {
      def verticalPartnerList = VerticalPartner.findAllByUser(user)
      if(verticalPartnerList){
          verticalPartnerList?.each { verticalPartner ->
              if( verticalPartner.status.enum == MigrationStatusEnum.PENDING) {
                  executeMigration(verticalPartner)
              }
          }
      }else{
          log.info "No se encontró usuario en verticalPartner para migrar: $userId"
          verticalPartnerList = VerticalPartner.findByUser(user)
          if(verticalPartnerList){
              verticalPartnerList?.each { verticalPartner ->
                  if( verticalPartner.status.enum == MigrationStatusEnum.PENDING) {
                      executeMigration(verticalPartner)
                  }
              }
          }else{
              log.info "No se encontró por segunda vez usuario en verticalPartner para migrar: $userId"
          }
      }
    }
  }
//add clickonero migration
  def executeMigration(VerticalPartner partner) {
    def verticalName = partner.vertical.name
    switch (verticalName) {
      case bebitosService.VERTICAL_NAME: bebitosService.migrateUserData(partner)
        break
      case CLICKONERO_VERTICAL_NAME: migrationService.migrateUserInfo(partner)
        break
      default: break
    }
  }
}
