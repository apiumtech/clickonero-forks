package com.winbits.api.clients.mailing.data

import javax.validation.constraints.NotNull


class ConfirmAccountData extends BaseEmailData {

  final String key = 'confirm.account'

  String vertical

  @NotNull
  String IdUserweb

  @NotNull
  String firm

  @NotNull
  @org.hibernate.validator.constraints.URL
  String UrlConfirm


}
