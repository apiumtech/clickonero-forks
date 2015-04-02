package com.winbits.api.clients.mailing.data
import javax.validation.constraints.NotNull

class ConfirmWelcomeData extends BaseEmailData {

  final String key = 'confirm.welcome'

  @NotNull
  String idUserweb


  String urlBanner
  String name
  String lastname
  String genre
  def    birthdate = new Date().format('yyyy-MM-dd')
  String postcode
  String locality
  String phone
  String banner
  String vertical
}
