package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.interfaces.EmailSenderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements EmailSenderInterface {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("valasplus.bni@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);
    }
}
