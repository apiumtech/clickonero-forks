package com.winbits.api.clients.mis

import org.hibernate.validator.constraints.NotEmpty

import javax.validation.Constraint
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email;

/**
 * Created with IntelliJ IDEA.
 * User: rafa
 * Date: 4/24/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class MisBase {

  MisBase() {
    date = new Date()
  }


  @NotEmpty
  String module

  @NotNull
  Date date

  @NotEmpty
  String event

  String vertical

  @NotNull
  Long userId

  @NotEmpty
  @Email
  String userName
}
