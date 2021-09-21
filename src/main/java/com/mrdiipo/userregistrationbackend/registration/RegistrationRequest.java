package com.mrdiipo.userregistrationbackend.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private String email;
    private String password;

}
