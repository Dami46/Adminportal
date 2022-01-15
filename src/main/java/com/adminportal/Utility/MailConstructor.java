package com.adminportal.Utility;

import com.adminportal.Domain.DropItem;
import com.adminportal.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

    public MimeMessagePreparator constructAcceptBalanceRequestEmail(User user) {
        Context context = new Context();
        context.setVariable("user", user);

        String text = templateEngine.process("requestAcceptEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
                email.setTo(user.getEmail());
                email.setSubject("Online Store - Balance");
                email.setText(text, true);
                email.setFrom(new InternetAddress(Objects.requireNonNull(env.getProperty("support.email"))));
            }
        };
        return messagePreparator;
    }

    public MimeMessagePreparator constructRejectBalanceRequestEmail(User user) {
        Context context = new Context();
        context.setVariable("user", user);

        String text = templateEngine.process("requestAcceptEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
                email.setTo(user.getEmail());
                email.setSubject("Online Store - Balance");
                email.setText(text, true);
                email.setFrom(new InternetAddress(Objects.requireNonNull(env.getProperty("support.email"))));
            }
        };
        return messagePreparator;
    }

    public MimeMessagePreparator constructDropWinnerEmail(User user, DropItem dropItem) {
        Context context = new Context();
        context.setVariable("dropItem", dropItem);
        context.setVariable("user", user);
        context.setVariable("book", dropItem.getBook());

        String text = templateEngine.process("dropWinnerEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
                email.setTo(user.getEmail());
                email.setSubject("You won the drop - " + dropItem.getDropTitle());
                email.setText(text, true);
                email.setFrom(new InternetAddress(Objects.requireNonNull(env.getProperty("support.email"))));
            }
        };
        return messagePreparator;
    }

    public MimeMessagePreparator constructDropLoserEmail(User user, DropItem dropItem) {
        Context context = new Context();
        context.setVariable("dropItem", dropItem);
        context.setVariable("user", user);
        context.setVariable("book", dropItem.getBook());

        String text = templateEngine.process("dropLoserEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true);
                email.setTo(user.getEmail());
                email.setSubject("You lose the drop - " + dropItem.getDropTitle());
                email.setText(text, true);
                email.setFrom(new InternetAddress(Objects.requireNonNull(env.getProperty("support.email"))));
            }
        };
        return messagePreparator;
    }

}
