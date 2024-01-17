package com.lgb.cafe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> allMailOfAdmin){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bao2002pytn.aws@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setCc(allMailOfAdmin.toArray(new String[0]));
        javaMailSender.send(message);
    }

    public void forgotPasswordMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("bao2002pytn.aws@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        javaMailSender.send(message);
    }
}
