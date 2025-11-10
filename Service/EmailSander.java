package com.car.dekho.car.dekho.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailSander {
    @Autowired
    private JavaMailSender javaMailSender;
        public String otpSanderForAccountVerification(String email,String otp){
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Otp for account verification");
            message.setText(otp + " otp for account verification please use it before 10 minutes after that it will expire");

            javaMailSender.send(message);
            return "Message is sand to"+ email;
        }

        public String otpForPasswordReset(String email,String otp){
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Otp for password reset");
            message.setText(otp + " otp for password reset please ignore it if you don't request it");

            javaMailSender.send(message);
            return "Message is sand to"+ email;
        }
    }


