package com.evgeniymakovsky.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("SendMailSSLService")
public class SendMailSSLService {

    final static Logger LOGGER = Logger.getLogger(SendMailSSLService.class);

    private final static String APP_MAIL = "evgeniymakovsky@gmail.com";
    private final static String APP_MAIL_PASSWORD = "13584213qwe";
    private String recipientMail;
    private String subjectMail;
    private String textMail;

    public void sendEmail() {
        LOGGER.info("Start sendEmail()");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(APP_MAIL, APP_MAIL_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(APP_MAIL));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientMail));
            message.setSubject(subjectMail);
            message.setText(textMail);

            Transport.send(message);

            LOGGER.info("Email to " + recipientMail + " has been successfully sent!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRecipientMail(String recipientMail) {
        this.recipientMail = recipientMail;
    }

    public void setSubjectMail(String subjectMail) {
        this.subjectMail = subjectMail;
    }

    public void setTextMail(String textMail) {
        this.textMail = textMail;
    }
}