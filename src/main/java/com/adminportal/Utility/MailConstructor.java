package com.adminportal.Utility;

import com.adminportal.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructAcceptBalanceRequestEmail(Locale locale, User user) {
        String message = "\n Yours add balance request has been accepted :D \n Your current balance is : " + user.getBalance() ;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Online Store - Balance");
        email.setText(message);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }

    public SimpleMailMessage constructRejectBalanceRequestEmail(Locale locale, User user) {
        String message = "\n Yours add balance request has been rejected :/ \n If you want to know why, contact with our support ! \n Your current balance is : " + user.getBalance() ;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Online Store - Balance");
        email.setText(message);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }
}
