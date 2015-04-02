package com.winbits.api.catalog.services

import com.winbits.api.catalog.controllers.PartialResponseCommand

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 6/11/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CatalogReadable {
  static final List ORDER_FILTER = ["max", "order", "offset", "sort"]
  static final List ORDER_FILTER_RETURN = ["totalCount", "limit", "offset"]

  List<Map> getAllDomainByFields(PartialResponseCommand command, Class clazz)

  List validFieldsInDomain(List fields, Class clazz, List defaultFields)

  List<Map> collectProjectionEntries(List projections, List fields)
}