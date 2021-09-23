package com.mrdiipo.userregistrationbackend.email;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("elegbedeOla@gmail.com");
        } catch (MessagingException e){
            LOGGER.info("failed to send email");
            throw new IllegalStateException("failed to send email");
        }
    }
}
