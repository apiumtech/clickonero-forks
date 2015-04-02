package com.winbits.bits

import com.winbits.exceptions.api.client.EntityValidationException
import grails.plugins.springsecurity.Secured
import grails.validation.Validateable

@Secured(['ROLE_ADMIN'])
class BagController {

  static allowedMethods = [save: 'POST']

  def bagService

  def save(CreateBagCommand command) {
    def result = withCommand(command) {
      bagService.createBag(command.name, command.description)
    }

    restpond data: result, status: 201
  }

  private def withCommand(command, Closure c) {
    command.beforeValidate()
    if (command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }

    c.call command
  }

}

@Validateable
class CreateBagCommand {
  String name
  String description

  def beforeValidate() {
  }

  static constraints = {
    description nullable: true
  }
}
