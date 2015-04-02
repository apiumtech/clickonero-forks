package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.CreateShippingAddressCommand
import com.winbits.api.affiliation.controllers.UpdateShippingAddressCommand
import com.winbits.api.affiliation.exception.ShippingAddressDoesNotBelongsToUserException
import com.winbits.api.clients.mis.affiliation.ChangeShippingAddressEvent
import com.winbits.domain.affiliation.CommonAddress
import com.winbits.domain.affiliation.ShippingAddress
import com.winbits.domain.affiliation.User
import com.winbits.domain.catalog.ZipCodeInfo
import com.winbits.exceptions.api.client.EntityNotExistsException

class ShippingAddressService {

  def misClient

  def getShippingAddresses(ArrayList fields, User user) {
    ShippingAddress.findAllByUser(user, [sort: 'main', order: 'desc'])
  }

  def createShippingAddres(CreateShippingAddressCommand command, User user) {
    if (user?.shippingAddresses) {
      if (command.main) {
        user.shippingAddresses.findAll { it.main }.each {
          it.main = false
          it.save()
        }
      }
    } else {
      command.main = true
    }

    def shippingAddress = new ShippingAddress()
    shippingAddress.properties = command.properties
    shippingAddress.commonAddress = command.toCommonAddress()
    shippingAddress.user = user
    shippingAddress.save()
    shippingAddress
  }

  def updateShippingAddress(UpdateShippingAddressCommand command, User user, Map params) {
    def shippingAddress = verifyShippingAddressForUpdateOperation(command.id, user)

    if (command.main && !shippingAddress.main) {
      user.shippingAddresses.findAll { it.main && it.id != command.id }.each {
        it.main = false
        it.save()
      }
    }

    shippingAddress.properties = command.properties
    shippingAddress.commonAddress = command.toCommonAddress()
    def loginEvent = new ChangeShippingAddressEvent(userName: user.email, userId: user.id, shippingAddressId:shippingAddress.id)
    if(user.salesAgentId)
      loginEvent.salesAgentId = user.salesAgentId
    log.info "changeDataShippingAddressEvent -> ${loginEvent.properties}"
    misClient.logMessage(loginEvent)
    shippingAddress.save()
  }

  private ShippingAddress verifyShippingAddressForUpdateOperation(Long id, User user) {
    def shippingAddress = ShippingAddress.get(id)
    if (!shippingAddress) {
      throw new EntityNotExistsException(id, 'shippingAddress')
    }

    if (shippingAddress.user != user) {
      throw new ShippingAddressDoesNotBelongsToUserException(shippingAddress, user)
    }
    shippingAddress
  }

  def deleteShippingAddress(Long id, User user) {
    def shippingAddress = verifyShippingAddressForUpdateOperation(id, user)
    Boolean mainShipping = shippingAddress.main
    if (shippingAddress) {
      shippingAddress.delete(flush: true)

      if(mainShipping){
        changeMainShipping(user)
      }

    }
    shippingAddress
  }

  def changeMainShipping (User user){
    def shippingAddresses = ShippingAddress.findByUser(user,[sort: 'lastUpdated', order: 'desc'])
    if (shippingAddresses) {
      shippingAddresses.main = true
      shippingAddresses.save()
    }
  }
  
  def deleteNullFronJsonObject (def field) {
    field ? field : null 
  }

  ShippingAddress createShippingAddressMigration(Map sAResponse, User user, boolean mainShippingAddress) {
    new ShippingAddress(commonAddress: createCommonAddressMigration(sAResponse), firstName: sAResponse.firstName,
        lastName: sAResponse.lastName, lastName2: deleteNullFronJsonObject(sAResponse.lastName2),  indications:  deleteNullFronJsonObject(sAResponse.indications) ,  user: user,
        main: mainShippingAddress)
  }

  CommonAddress createCommonAddressMigration (Map sAResponse) {
    new CommonAddress(street: sAResponse.street,
        internalNumber: deleteNullFronJsonObject(sAResponse.internalNumber),
        externalNumber: deleteNullFronJsonObject(sAResponse.externalNumber),
        phone: sAResponse.phone,
        zipCode: sAResponse.zipCode,
        location: sAResponse.location,
        county: obtainCountyFromZipCode(sAResponse.zipCode)
    )
  }

  String obtainCountyFromZipCode(String zipCode) {
    ZipCodeInfo zipCodeInfo = ZipCodeInfo.findByZipCode(zipCode)
    zipCodeInfo ? zipCodeInfo.county : null
  }
}
