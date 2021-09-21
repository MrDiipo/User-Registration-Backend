package com.mrdiipo.userregistrationbackend.appuser;

import com.mrdiipo.userregistrationbackend.registration.token.ConfirmationToken;
import com.mrdiipo.userregistrationbackend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static String USER_NOT_FOUND = "User with email not found";

    @Autowired
    private final AppRepository appRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param email the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser appUser){
       boolean userExists =  appRepository.findByEmail(appUser.getEmail()).isPresent();

       if (userExists){
           throw new IllegalArgumentException("email already taken.");
       }

       String  encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
       appUser.setPassword(encodedPassword);

       appRepository.save(appUser);

       String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: SEND EMAIL
       return token;

    }

    public void enableAppUser(String email) {
    }
}
