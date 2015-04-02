package com.winbits.api.clients.mailing.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {

//  private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

  private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
  private static final String SMTP_AUTH_USER = "clients.sms-test";
  private static final String SMTP_AUTH_PWD = "48JuvyzrC6KFKRZ";

  public static void sendEmail(String email, String subject, String content) throws Exception {
    Properties props = new Properties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", SMTP_HOST_NAME);
    props.put("mail.smtp.port", 587);
    props.put("mail.smtp.auth", "true");

    Authenticator auth = new SMTPAuthenticator();
    Session mailSession = Session.getDefaultInstance(props, auth);
    // uncomment for debugging infos to stdout
//    mailSession.setDebug(true);
    Transport transport = mailSession.getTransport();

    MimeMessage message = new MimeMessage(mailSession);

    Multipart multipart = new MimeMultipart("alternative");

    BodyPart part1 = new MimeBodyPart();
    part1.setText("This is multipart mail and u read part1……");

    BodyPart part2 = new MimeBodyPart();
    part2.setContent(content, "text/html");

    multipart.addBodyPart(part1);
    multipart.addBodyPart(part2);

    message.setContent(multipart);
    message.setFrom(new InternetAddress("webmaster@winbits.com", "Winbits"));
    message.setSubject(subject);
    message.addRecipient(Message.RecipientType.TO,
        new InternetAddress(email));

    transport.connect();
    transport.sendMessage(message,
        message.getRecipients(Message.RecipientType.TO));
    transport.close();
  }

  private static class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
      String username = SMTP_AUTH_USER;
      String password = SMTP_AUTH_PWD;
      return new PasswordAuthentication(username, password);
    }
  }
}
