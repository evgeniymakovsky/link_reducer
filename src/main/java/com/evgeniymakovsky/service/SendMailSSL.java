package com.evgeniymakovsky.service;

public interface SendMailSSL {

    public void sendEmail();

    public void setRecipientMail(String recipientMail);

    public void setSubjectMail(String subjectMail);

    public void setTextMail(String textMail);
}
