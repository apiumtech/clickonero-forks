package com.winbits.api.clients.mailing.utils;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class EmailSenderTests {

  @Test
  public void sendEmailWorks() throws Exception {
    EmailSender.sendEmail("arcesino@gmail.com", "Winbits Test", "Just a simple test");
  }
}
