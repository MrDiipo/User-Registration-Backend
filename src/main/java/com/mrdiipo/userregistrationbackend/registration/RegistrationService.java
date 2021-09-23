package com.mrdiipo.userregistrationbackend.registration;

import com.mrdiipo.userregistrationbackend.appuser.AppUser;
import com.mrdiipo.userregistrationbackend.appuser.AppUserRole;
import com.mrdiipo.userregistrationbackend.appuser.AppUserService;
import com.mrdiipo.userregistrationbackend.email.EmailSender;
import com.mrdiipo.userregistrationbackend.email.EmailService;
import com.mrdiipo.userregistrationbackend.registration.token.ConfirmationToken;
import com.mrdiipo.userregistrationbackend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmTokenService;
    private final EmailSender emailSender;

    public String request(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail){
            throw new IllegalArgumentException("email not valid");
        }

       String  token = appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(),
                buildEmail(request.getFirstName(), link));
        return token;
    }

    private String buildEmail(String firstName, String link) {
        // TODO: Implement Email builder html
        return null;
    }

    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmTokenService.getToken(token);
        if (!(confirmationToken.getToken().equals(token))){
            throw new IllegalStateException("token not found");
        }
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
