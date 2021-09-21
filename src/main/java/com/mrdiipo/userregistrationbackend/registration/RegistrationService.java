package com.mrdiipo.userregistrationbackend.registration;

import com.mrdiipo.userregistrationbackend.appuser.AppUser;
import com.mrdiipo.userregistrationbackend.appuser.AppUserRole;
import com.mrdiipo.userregistrationbackend.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String request(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail){
            throw new IllegalArgumentException("email not valid")
        }

        return appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
    }
}
