package com.bni.finalproject01webservice.interfaces;

public interface EmailSenderInterface {

    void sendEmail(String to, String subject, String body);
}
