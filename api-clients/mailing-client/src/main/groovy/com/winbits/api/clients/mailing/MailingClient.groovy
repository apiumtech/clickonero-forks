package com.winbits.api.clients.mailing

import com.winbits.api.clients.mailing.data.BaseEmailData

interface MailingClient {
  /**
   * The data map must have an entry called <b>confirmationUrl</b> for the message to gets sent.
   *
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  void sendRegisterConfirmationEmail(String email, Map data)

  /**
   *
   * @param data Email message model.
   */
  void sendConfirmationOrderMail(Map data)

  /**
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  void sendWelcomeEmail(String email, Map data)


  /**
   * The data map must have an entry called <b>resetUrl</b> for the message to gets sent.
   *
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  void sendPasswordResetEmail(String email, Map data)

  /**
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  void sendOxxoPaymentTicketEmail(String email, Map data)


  /**
   * @param email Email address to send message to.
   * @param data Email message model.
   */
  void sendHsbcPaymentTicketEmail(String email, Map data)

  void sendEmail(BaseEmailData data)


}
