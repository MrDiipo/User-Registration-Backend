package com.mrdiipo.userregistrationbackend.email;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String email) {
        try {
                MimeM
        } catch (MessagingException e){
            LOGGER.error()
        }
    }
}
