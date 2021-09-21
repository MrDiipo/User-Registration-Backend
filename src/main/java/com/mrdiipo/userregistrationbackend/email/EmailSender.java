package com.mrdiipo.userregistrationbackend.email;

public interface EmailSender {
    void send(String to, String email);
}
