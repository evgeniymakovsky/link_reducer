package com.evgeniymakovsky.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by mak on 9.6.17.
 */
public class BCryptEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "55555";
        String encodedPassword = encoder.encode(password);
        System.out.println(encodedPassword);
        System.out.println(encoder.matches(password, "$2a$10$hEW34zJZv5/rYMuPXUbRbun3VzrvPgRQ0DibZkIfcbnG16cKcUXVW"));
    }
}
