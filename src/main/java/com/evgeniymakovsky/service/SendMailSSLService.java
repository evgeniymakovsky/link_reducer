package com.evgeniymakovsky.service;

public interface SendMailSSLService {

    public void sendEmail();

    public void setRecipientMail(String recipientMail);

    public void setSubjectMail(String subjectMail);

    public void setTextMail(String textMail);
}
