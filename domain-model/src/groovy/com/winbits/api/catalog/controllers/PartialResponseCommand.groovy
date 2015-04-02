package com.winbits.api.catalog.controllers

import grails.validation.Validateable

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class PartialResponseCommand {
  List fields
  Map filters
  List<Long> ids
  Integer max = 20
  Integer offset = 0
  List sort = ["id"]
  String order = "asc"

  static constraints = {
    fields nullable: true
    ids nullable: true
    max max: 50
  }

}
