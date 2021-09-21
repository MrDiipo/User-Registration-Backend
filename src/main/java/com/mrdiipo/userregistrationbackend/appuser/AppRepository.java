package com.mrdiipo.userregistrationbackend.appuser;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppRepository {

    Optional<AppUser> findByEmail(String email);

}
