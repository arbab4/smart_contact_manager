package com.scm.smartcontactmanager.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithAttachment();

    void sendEmailWithHtml();    

    void sendPasswordResetEmail(String to, String subject, String body);
}
