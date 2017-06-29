package com.evgeniymakovsky.utils;

import org.apache.log4j.Logger;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailSSL {

    final static Logger logger = Logger.getLogger(SendMailSSL.class);

    private String appMail = "evgeniymakovsky@gmail.com";
    private String appMailPassword = "13584213qwe";
    private String recipientMail;
    private String subjectMail;
    private String textMail;

    public void sendEmail() {
        logger.info("Start sendEmail()");
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
                        return new PasswordAuthentication(appMail, appMailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appMail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientMail));
            message.setSubject(subjectMail);
            message.setText(textMail);

            Transport.send(message);

            logger.info("Email to " + recipientMail + " has been successfully sent!");

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
