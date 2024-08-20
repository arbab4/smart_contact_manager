package com.scm.smartcontactmanager.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.smartcontactmanager.services.EmailService;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender eMailSender;


    @Override
    public void sendEmail(String to, String subject, String body) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(to);
       message.setSubject(subject);
       message.setText(body);
       message.setFrom("arbab33ahmad@gmail.com");
       
       eMailSender.send(message);
    }

    @Override
    public void sendPasswordResetEmail(String to, String subject, String body) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(to);
       message.setSubject(subject);
       message.setText(body);
       message.setFrom("arbab33ahmad@gmail.com");
       
       eMailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        
    }
    
}
