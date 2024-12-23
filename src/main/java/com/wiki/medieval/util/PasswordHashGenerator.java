package com.wiki.medieval.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "admin123";
//        String encodedPassword = encoder.encode(rawPassword);
//        System.out.println(encodedPassword);
        System.out.println(encoder.matches("admin123", "$2a$10$j4QtrAa0ZGizaXTXHGKm0.SZ0xEIVBPpF4xG9D/.cpLUJfMWAleJe"));

    }
}

